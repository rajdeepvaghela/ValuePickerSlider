package com.rdapps.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rdapps.example.ui.theme.ValuePickerSliderTheme
import com.rdapps.valuepickerslider.BarBreak
import com.rdapps.valuepickerslider.ValuePickerSlider
import com.rdapps.valuepickerslider.rememberSliderState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ValuePickerSliderTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SliderDemo(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun SliderDemo(modifier: Modifier = Modifier) {
    val sliderState = rememberSliderState(
        range = 880..1080,
        currentValue = 910f,
        barBreaks = listOf(
            BarBreak(10, 1f),
            BarBreak(5, .75f),
            BarBreak(1, .5f)
        )
    )

    ValuePickerSlider(
        state = sliderState,
        numSegments = 32,
        currentValueLabel = {
            Text(
                text = getFmName(it / 10f),
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.background(MaterialTheme.colorScheme.background)
            )
        },
        indicatorLabel = {
            if (it % 10 == 0) {
                Text(
                    text = "${it / 10f}",
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        currentBarColor = Color.Red,
        modifier = modifier
            .padding(top = 24.dp)
            .fillMaxWidth()
    )
}

private fun getFmName(value: Float): String {
    return when (value) {
        91.1f -> "Radio City"
        92.7f -> "Big FM"
        93.5f -> "Red FM"
        98.3f -> "Radio Mirchi"
        else -> "$value"
    }
}