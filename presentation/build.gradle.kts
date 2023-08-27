plugins {
    id("kotlin-library-convention")
}

dependencies {
    implementation(projects.core)

    implementation(platform(libs.kotlin.bom))

    implementation(libs.arrow.core)
    implementation(libs.kotlinx.coroutines.core)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(projects.testUtil)
}
