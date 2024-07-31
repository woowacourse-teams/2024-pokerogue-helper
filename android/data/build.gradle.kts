plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
}

android {
    namespace = "poke.rogue.helper.data"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
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
}

dependencies {
    implementation(project(":local"))
    implementation(project(":remote"))
    implementation(project(":stringmatcher"))
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin)
    // third-party
    implementation(libs.timber)
    // test
    testImplementation(libs.bundles.unit.test)
    testImplementation(libs.kotlin.test)
}
