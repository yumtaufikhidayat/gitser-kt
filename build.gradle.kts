plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.parcelize) apply false
    id("com.google.devtools.ksp") version "2.1.21-2.0.2" apply false
}