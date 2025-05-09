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
    // android
    implementation(libs.androidx.core.ktx)
    // koin
    implementation(platform(libs.koin.bom))
    implementation(libs.koin.core)
    testImplementation(libs.koin.test.junit5)
    // JUnit test api
    api(libs.bundles.unit.test)
    api(libs.kotlin.test)
    // androidx test api
    implementation(libs.androidx.test.core)
    implementation(libs.androidx.test.extention.ktx)
    implementation(libs.androidx.test.espresso)
    implementation(libs.androidx.test.espresso.contrib)
    // koin api
    api(libs.koin.test.junit5)
    api(libs.koin.android.test)
}
