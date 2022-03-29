plugins {
    kotlin("jvm")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":core"))

    implementation(platform(Libs.kotlin_bom))

    implementation(Libs.arrow_core)
    implementation(Libs.kotlinx_coroutines_core)

    testImplementation(Libs.kotlinx_coroutines_test)
    testImplementation(Libs.mockk)
    testImplementation(project(":test-util"))
}
