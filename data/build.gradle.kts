plugins {
    id("kotlin-library-convention")
}

dependencies {
    implementation(projects.core)
    implementation(projects.domain)

    implementation(platform(libs.kotlin.bom))

    implementation(libs.arrow.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.moshi)
    implementation(libs.remotedata)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(projects.testUtil)
}
