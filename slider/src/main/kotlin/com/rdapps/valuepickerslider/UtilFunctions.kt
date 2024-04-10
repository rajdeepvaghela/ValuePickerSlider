package com.rdapps.valuepickerslider

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun rememberSliderState(
    range: ClosedRange<Int> = 0..120,
    currentValue: Float = range.start.toFloat(),
    barBreaks: List<BarBreak> = emptyList()
): SliderState {
    val state = rememberSaveable(saver = SliderStateImpl.Saver) {
        SliderStateImpl(currentValue, range, barBreaks.sortedByDescending { it.occurrenceEvery })
    }
    LaunchedEffect(key1 = Unit) {
        state.snapTo(state.currentValue.roundToInt().toFloat())
    }
    return state
}

fun Modifier.drag(
    state: SliderState,
    numSegments: Int,
) = this.pointerInput(Unit) {
    val segmentWidthPx = size.width / numSegments

    var job: Job? = null
    coroutineScope {
        detectHorizontalDragGestures { change, _ ->
            val offset = state.currentValue - change.positionChange().x / segmentWidthPx

            launch {
                state.snapTo(offset)
            }

            job?.cancel()
            job = launch {
                delay(100)
                state.decayTo(offset)
            }

            if (change.positionChange() != Offset.Zero) {
                change.consume()
            }
        }
    }
}

infix fun Dp.x(other: Float): Dp = Dp(value * other)