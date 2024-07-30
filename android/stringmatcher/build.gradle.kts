plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    implementation(libs.kotlin)
    testImplementation(libs.bundles.unit.test)
    testImplementation(libs.kotlin.test)
}