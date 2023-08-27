plugins {
    id("kotlin-library-convention")
}

dependencies {
    implementation(projects.core)

    implementation(libs.remotedata)
    api(libs.bundles.kotest)
    api(libs.fixture)
    api(libs.kotlin.faker)
}
