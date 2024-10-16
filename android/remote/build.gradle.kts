import org.jetbrains.kotlin.konan.properties.Properties

plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

val properties =
    Properties().apply {
        load(rootProject.file("local.properties").inputStream())
    }

android {
    namespace = "poke.rogue.helper.remote"

    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
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
        create("alpha") {
            initWith(getByName("debug"))
        }
        create("beta") {
            initWith(getByName("debug"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.kotlin.serialization.json)
    // third-party
    implementation(libs.timber)
    // koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    testImplementation(libs.koin.test.junit5)
    // retrofit & okhttp
    implementation(libs.bundles.retrofit)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.logging.interceptor)
    // unit test
    testImplementation(libs.bundles.unit.test)
    testImplementation(libs.kotlin.test)
    testImplementation(libs.mockk.webserver)
}
