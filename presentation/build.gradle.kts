plugins {
    id("kotlin-convention")
}

dependencies {
    implementation(project(":core"))

    implementation(platform(libs.kotlin.bom))

    implementation(libs.arrow.core)
    implementation(libs.kotlin.coroutines.core)

    testImplementation(libs.kotlin.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(project(":test-util"))
}
