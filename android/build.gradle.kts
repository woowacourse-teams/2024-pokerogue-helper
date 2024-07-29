import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.kotlin.gradleplugin)
        classpath(libs.agp)
        classpath(libs.ktlint)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.android.junit5) apply false
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    tasks {
        withType<Test> {
            useJUnitPlatform()
        }

        withType<KotlinCompile>().configureEach {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_17.toString()
            }
        }
    }
}