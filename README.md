# ValuePickerSlider
[![Release](https://jitpack.io/v/com.github.rajdeepvaghela/ValuePickerSlider.svg)](https://jitpack.io/#com.github.rajdeepvaghela/ValuePickerSlider)
[![Release](https://img.shields.io/github/v/release/rajdeepvaghela/ValuePickerSlider)](https://github.com/rajdeepvaghela/ValuePickerSlider/releases)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

It is a Horizontal slider value picker, which can be customized heavily to pick the desired number. Build fully in Jetpack Compose.

<div align="center">
    <img src="https://github.com/rajdeepvaghela/ValuePickerSlider/assets/17750025/790ba284-5a12-428d-80b3-23780ed098f5" />
</div>

## Installation
Add it in your root build.gradle or settings.gradle at the end of repositories:
```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
      mavenCentral()
      maven { url 'https://jitpack.io' }
    }
}
```
Add the dependency
```gradle
dependencies {
    implementation 'com.github.rajdeepvaghela:ValuePickerSlider:1.0.7'
}
```
## Usage
```kotlin
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
                    text = numberFormat.format(it / 10f),
                    color = MaterialTheme.colorScheme.onBackground,
                )
            }
        },
        currentBarColor = Color.Red,
        modifier = Modifier.fillMaxWidth()
    )
```

## License
```
Copyright 2024 Rajdeep Vaghela

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
