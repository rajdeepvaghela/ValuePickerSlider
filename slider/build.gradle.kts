@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    id("maven-publish")
}

val libGroup = "com.rdapps.valuepickerslider"

kotlin {
    applyDefaultHierarchyTemplate()

    androidLibrary {
        namespace = libGroup
        compileSdk = 36
        minSdk = 21
    }

    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    js { browser() }
    wasmJs { browser() }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.animation)
            implementation(compose.material3)
            implementation(compose.ui)
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

// Let the KMP Gradle plugin auto-generate publications for every target
// (kotlinMultiplatform, jvm, androidRelease, iosX64, iosArm64, iosSimulatorArm64).
// Then stamp them all with the correct groupId / version.
afterEvaluate {
    publishing {
        publications {
            withType<MavenPublication> {
                groupId = libGroup
                version = "1.0.8"
            }
        }
    }
}