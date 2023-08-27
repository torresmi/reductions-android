plugins {
    id("kotlin-library-convention")
}

dependencies {
    api(project(":feature:stores:stores-domain"))

    implementation(platform(libs.kotlin.bom))

    implementation(libs.remotedata)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(project(":test-util"))
}
