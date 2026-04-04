package com.rdapps.valuepickerslider

import androidx.compose.runtime.Stable

@Stable
interface SliderState {
    val currentValue: Float
    val range: ClosedRange<Int>
    val barBreaks: List<BarBreak>

    suspend fun snapTo(value: Float)
    suspend fun decayTo(value: Float)
    suspend fun stop()
}