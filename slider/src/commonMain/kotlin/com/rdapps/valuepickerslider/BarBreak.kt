package com.rdapps.valuepickerslider

import androidx.compose.runtime.Stable

@Stable
data class BarBreak(
    val occurrenceEvery: Int,
    val barHeightRatio: Float
)
