plugins {
    kotlin("jvm")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":core"))

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:_"))

    implementation("io.arrow-kt:arrow-core:_")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
    implementation("com.squareup.moshi:moshi:_")
    implementation("com.squareup.retrofit2:converter-moshi:_")
    implementation("com.github.torresmi:remotedata:_")
    implementation("com.squareup.retrofit2:retrofit:_")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
    testImplementation("io.mockk:mockk:_")
    testImplementation(project(":test-util"))
}
