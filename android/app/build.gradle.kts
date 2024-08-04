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
            storeFile = file(System.getProperty("user.home") + "/.android/debug.keystore")
            storePassword = "android"
        }

//        create("release") {
//            keyAlias = properties.getProperty("keyAlias")
//            keyPassword = properties.getProperty("keyPassword")
//            storeFile = file("${project.rootDir.absolutePath}/keystore/key.jks")
//            storePassword = properties.getProperty("storePassword")
//        }
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
    // google & firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)
    // android test
    androidTestImplementation(libs.bundles.android.test)
    androidTestRuntimeOnly(libs.junit5.android.test.runner)
}
