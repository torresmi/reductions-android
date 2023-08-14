plugins {
    id("kotlin-library-convention")
}

dependencies {
    implementation(project(":core"))

    implementation(libs.remotedata)
    api(libs.bundles.kotest)
    api(libs.fixture)
    api(libs.kotlin.faker)
}
