// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.plugin.serialization) apply false
    alias(libs.plugins.com.google.dagger.hilt.android) apply false
}

buildscript {
    dependencies {
        classpath(libs.com.google.services)
        classpath(libs.com.firebase.crashlytics.plugin)
    }
}
