import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.android.junit5)
}

val properties =
    Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

android {
    namespace = libs.versions.applicationId.get()
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = libs.versions.applicationId.get()
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionName = libs.versions.appVersion.get()
        versionCode = libs.versions.versionCode.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunnerArguments["runnerBuilder"] =
            "de.mannodermaus.junit5.AndroidJUnit5Builder"

        buildConfigField(
            "String",
            "POKE_BASE_URL",
            properties.getProperty("POKE_BASE_URL"),
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
    packaging {
        resources {
            excludes += "META-INF/**"
            excludes += "win32-x86*/**"
        }
    }
    buildFeatures {
        buildConfig = true
        dataBinding = true
    }
    testOptions {
        animationsDisabled = true
        managedDevices {
            localDevices {
                create("pixel4api27") {
                    device = "Pixel 4"
                    apiLevel = 27
                    systemImageSource = "aosp"
                }
                create("pixel4api28") {
                    device = "Pixel 4"
                    apiLevel = 28
                    systemImageSource = "aosp"
                }
                create("pixel4api29") {
                    device = "Pixel 4"
                    apiLevel = 29
                    systemImageSource = "aosp"
                }
                create("pixel4api30") {
                    device = "Pixel 4"
                    apiLevel = 30
                    systemImageSource = "aosp"
                }
                create("pixel4api31") {
                    device = "Pixel 4"
                    apiLevel = 31
                    systemImageSource = "aosp"
                }
                create("pixel4api32") {
                    device = "Pixel 4"
                    apiLevel = 32
                    systemImageSource = "aosp"
                }
                create("pixel4api33") {
                    device = "Pixel 4"
                    apiLevel = 33
                    systemImageSource = "aosp"
                }
                create("pixel4api34") {
                    device = "Pixel 4"
                    apiLevel = 34
                    systemImageSource = "aosp"
                }
            }
            groups {
                create("phones") {
                    targetDevices.add(devices["pixel4api27"])
                    targetDevices.add(devices["pixel4api28"])
                    targetDevices.add(devices["pixel4api29"])
                    targetDevices.add(devices["pixel4api30"])
                    targetDevices.add(devices["pixel4api31"])
                    targetDevices.add(devices["pixel4api32"])
                    targetDevices.add(devices["pixel4api33"])
                    targetDevices.add(devices["pixel4api34"])
                }
            }
        }
    }
}

dependencies {
    // module
    implementation(project(":data"))
    implementation(project(":remote")) // TODO remove this
    implementation(project(":local"))
    testImplementation(project(":testing"))
    androidTestImplementation(project(":testing"))
    // androidx
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.lifecycle)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.flexbox)
    // third party
    implementation(libs.timber)
    implementation(libs.coil.core)
    implementation(libs.glide)
    kapt(libs.glide.compiler)
    implementation(libs.splash.screen)
    // android test
    androidTestImplementation(libs.bundles.android.test)
    androidTestRuntimeOnly(libs.junit5.android.test.runner)
}
