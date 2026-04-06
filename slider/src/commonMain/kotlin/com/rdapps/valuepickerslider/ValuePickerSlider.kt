package com.rdapps.valuepickerslider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun ValuePickerSlider(
    modifier: Modifier = Modifier,
    state: SliderState = rememberSliderState(),
    numSegments: Int = 20,
    fadeEdgeMinAlpha: Float = .25f,
    barHeight: Dp = 40.dp,
    barWidth: Dp = 2.dp,
    barColor: Color = MaterialTheme.colorScheme.secondary,
    currentBarHeight: Dp = 60.dp,
    currentBarColor: Color = MaterialTheme.colorScheme.primary,
    currentValueLabel: @Composable (Int) -> Unit = { value -> Text(value.toString()) },
    indicatorLabel: @Composable BoxScope.(Int) -> Unit = { value -> Text(value.toString()) },
) {
    val density = LocalDensity.current

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth()
            .drag(state, numSegments),
        contentAlignment = Alignment.TopCenter,
    ) {
        val segmentWidth = maxWidth / numSegments
        val segmentWidthPx = constraints.maxWidth.toFloat() / numSegments.toFloat()
        val halfSegments = (numSegments + 1) / 2
        val start = (state.currentValue - halfSegments).toInt().coerceAtLeast(state.range.start)
        val end = (state.currentValue + halfSegments).toInt().coerceAtMost(state.range.endInclusive)
        val breaks = state.barBreaks

        var indicatorHeight by remember {
            mutableStateOf(30.dp)
        }

        val maxOffset = constraints.maxWidth / 2f
        for (i in start..end) {
            val offsetX = (i - state.currentValue) * segmentWidthPx
            // indicator at center is at 1f, indicators at edges are at 0.25f
            val alpha = 1f - (1f - fadeEdgeMinAlpha) * (offsetX / maxOffset).absoluteValue

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .graphicsLayer(
                            alpha = alpha,
                            translationX = offsetX
                        )
                        .height(indicatorHeight),
                ) {
                    indicatorLabel(i)
                }

                val size = DpSize(
                    width = barWidth,
                    height = barHeight x (
                            breaks.find {
                                i % it.occurrenceEvery == 0
                            }?.barHeightRatio ?: 1f
                            )
                )

                LineView(
                    size = size,
                    color = barColor,
                    modifier = Modifier
                        .width(segmentWidth)
                        .height(currentBarHeight)
                        .graphicsLayer(
                            alpha = alpha,
                            translationX = offsetX
                        )
                )
            }
        }

        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.onGloballyPositioned {
                    indicatorHeight = with(density) { it.size.height.toDp() }
                }
            ) {
                currentValueLabel(state.currentValue.roundToInt())
            }

            LineView(size = DpSize(barWidth x 1.5f, currentBarHeight), color = currentBarColor)
        }
    }
}

@Composable
private fun LineView(size: DpSize, color: Color, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Spacer(
            modifier = Modifier
                .size(size)
                .background(color = color, shape = CircleShape)
        )
    }
}