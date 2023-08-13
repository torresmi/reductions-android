plugins {
    id("kotlin-library-convention")
}

dependencies {
    api(project(":stores-domain"))

    implementation(platform(libs.kotlin.bom))

    implementation(libs.remotedata)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(project(":test-util"))
}
