plugins {
    kotlin("jvm")
    alias(libs.plugins.ktlint)
}

dependencies {
    // unit test
    testImplementation(libs.bundles.unit.test)
}
