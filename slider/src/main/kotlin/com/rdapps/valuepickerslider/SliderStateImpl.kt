package com.rdapps.valuepickerslider

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FloatSpringSpec
import androidx.compose.animation.core.Spring
import androidx.compose.runtime.saveable.Saver
import kotlin.math.roundToInt

class SliderStateImpl(
    currentValue: Float,
    override val range: ClosedRange<Int>,
    override val barBreaks: List<BarBreak>
) : SliderState {
    private val floatRange = range.start.toFloat()..range.endInclusive.toFloat()

    private val animatable = Animatable(currentValue)
    private val decayAnimationSpec = FloatSpringSpec(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow,
    )

    override val currentValue: Float
        get() = animatable.value

    override suspend fun stop() {
        animatable.stop()
    }

    override suspend fun snapTo(value: Float) {
        animatable.snapTo(value.coerceIn(floatRange))
    }

    override suspend fun decayTo(value: Float) {
        val target = value.roundToInt().coerceIn(range).toFloat()
        animatable.animateTo(target)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SliderStateImpl

        if (range != other.range) return false
        if (floatRange != other.floatRange) return false
        if (animatable != other.animatable) return false
        if (decayAnimationSpec != other.decayAnimationSpec) return false
        if (barBreaks != other.barBreaks) return false

        return true
    }

    override fun hashCode(): Int {
        var result = range.hashCode()
        result = 31 * result + floatRange.hashCode()
        result = 31 * result + animatable.hashCode()
        result = 31 * result + decayAnimationSpec.hashCode()
        result = 31 * result + barBreaks.hashCode()
        return result
    }

    companion object {
        val Saver = Saver<SliderStateImpl, List<Any>>(
            save = { listOf(it.currentValue, it.range.start, it.range.endInclusive, it.barBreaks) },
            restore = {
                SliderStateImpl(
                    currentValue = it[0] as Float,
                    range = (it[1] as Int)..(it[2] as Int),
                    barBreaks = it[3] as List<BarBreak>
                )
            }
        )
    }
}