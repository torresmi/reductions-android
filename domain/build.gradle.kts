plugins {
    alias(libs.plugins.kotlin.jvm)
}

tasks {
    test {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":core"))

    implementation(platform(libs.kotlin.bom))

    implementation(libs.arrow.core)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.remotedata)

    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)
    testImplementation(project(":test-util"))
}
