import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.android.junit5)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics.plugin)
}

val properties =
    Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

android {
    namespace = libs.versions.applicationId.get()
    compileSdk = libs.versions.compileSdk.get().toInt()
    signingConfigs {
        getByName("debug") {
            keyAlias = "androiddebugkey"
            keyPassword = "android"
            storeFile = File("${project.rootDir.absolutePath}/keystore/debug.keystore")
            storePassword = "android"
        }
        create("release") {
            keyAlias = properties.getProperty("KEY_ALIAS")
            keyPassword = properties.getProperty("KEY_PASSWORD")
            storeFile = file("${project.rootDir.absolutePath}/keystore/poke_key.keystore")
            storePassword = properties.getProperty("STORE_PASSWORD")
        }
    }
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
        debug {
            applicationIdSuffix = ".dev"
            signingConfig = signingConfigs.getByName("debug")
        }

        create("beta") {
            initWith(getByName("debug"))
            versionNameSuffix = "-beta"
            applicationIdSuffix = ".beta"
            signingConfig = signingConfigs.getByName("debug")
//            firebaseAppDistribution {
//                artifactType = "APK"
//                releaseNotesFile = "firebase/releaseNote.txt"
//                groupsFile = "firebase/testers.txt"
//            }
        }

        create("alpha") {
            initWith(getByName("debug"))
            versionNameSuffix = "-alpha"
            applicationIdSuffix = ".alpha"
            signingConfig = signingConfigs.getByName("debug")
//            firebaseAppDistribution {
//                artifactType = "APK"
//                releaseNotesFile = "firebase/releaseNote.txt"
//                groupsFile = "firebase/testers.txt"
//            }
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            signingConfig = signingConfigs.getByName("release")
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
        // ref: https://github.com/robolectric/robolectric/pull/4736
        unitTests.isIncludeAndroidResources = true
        managedDevices {
            val deviceApis = (27..34)
            localDevices {
                deviceApis.forEach { api ->
                    create("pixel4api$api") {
                        device = "Pixel 4"
                        apiLevel = api
                        systemImageSource = "aosp"
                    }
                }
            }
            groups {
                create("phones") {
                    deviceApis.forEach { api ->
                        targetDevices.add(devices["pixel4api$api"])
                    }
                }
            }
        }
    }
}

dependencies {
    // module
    implementation(project(":data"))
    implementation(project(":local"))
    implementation(project(":stringmatcher"))
    implementation(project(":analytics"))
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
    implementation(libs.timber)
    implementation(libs.coil.core)
    implementation(libs.coil.svg)
    implementation(libs.glide)
    kapt(libs.glide.compiler)
    implementation(libs.splash.screen)
    implementation(libs.balloon)
    // google & firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.crashlytics.buildtools)
    implementation(libs.bundles.firebase)
    implementation(libs.app.update.ktx)
    // Koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.android)
    androidTestImplementation(libs.koin.android.test)
    // unit test
    testRuntimeOnly(libs.junit.vintage.engine)
    // robolectric test
    testImplementation(libs.robolectric)
    testImplementation(libs.bundles.android.test)
    // android test
    androidTestImplementation(libs.bundles.android.test)
    debugImplementation(libs.bundles.android.test)
    androidTestRuntimeOnly(libs.junit5.android.test.runner)
    testImplementation(libs.android.test.fragment)
    debugImplementation(libs.android.test.fragment.manifest)
}
