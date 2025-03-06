// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.devtools.ksp") version "2.0.21-1.0.25" apply false
    id("com.google.dagger.hilt.android") version "2.51" apply false
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        val nav_version = "2.8.8"
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version")
        classpath(libs.kotlin.gradle.plugin)
    }
}