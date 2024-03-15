// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript{
    repositories{
        google()
        mavenCentral()
    }
    dependencies{
        val navVersion = "2.7.7"
        classpath("androidx.navigation.safeargs.kotlin:androidx.navigation.safeargs.kotlin.gradle.plugin:${navVersion}")
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
}