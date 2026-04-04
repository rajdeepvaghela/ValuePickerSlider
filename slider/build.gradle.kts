@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.composeCompiler)
    id("maven-publish")
}

val libGroup = "com.rdapps.valuepickerslider"

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
        publishLibraryVariants("release")
    }

    jvm()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    linuxX64()
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

        commonTest.dependencies {

        }
    }
}

android {
    namespace = libGroup
    compileSdk = 36

    defaultConfig {
        minSdk = 21
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    publishing {
        singleVariant("release")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
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