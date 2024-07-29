plugins {
    kotlin("jvm")
}

dependencies {
    implementation(libs.bundles.unit.test)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin)
    implementation(kotlin("test"))
}
