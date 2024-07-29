plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin)
    // unit test
    testImplementation(libs.bundles.unit.test)
}
