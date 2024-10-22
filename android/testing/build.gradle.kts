plugins {
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.android.library)
}

android {
    namespace = "poke.rogue.helper.testing"
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
    packaging {
        resources {
            excludes += "META-INF/**"
            excludes += "win32-x86*/**"
        }
    }
}

dependencies {
    implementation(project(":analytics"))
    implementation(project(":data"))
    implementation(project(":stringmatcher"))
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin)
    implementation(libs.timber)
    implementation(libs.mockk)
    api(libs.bundles.unit.test)
    api(libs.kotlin.test)
}
