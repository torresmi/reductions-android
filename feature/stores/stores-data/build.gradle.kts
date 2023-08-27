plugins {
    id("kotlin-library-convention")
}

dependencies {
    implementation(projects.feature.stores.storesDomain)

    implementation(platform(libs.kotlin.bom))

    implementation(libs.remotedata)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(projects.testUtil)
}
