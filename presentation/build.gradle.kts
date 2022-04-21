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

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:_"))

    implementation("io.arrow-kt:arrow-core:_")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
    testImplementation("io.mockk:mockk:_")
    testImplementation(project(":test-util"))
}
