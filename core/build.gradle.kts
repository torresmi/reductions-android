plugins {
    kotlin("jvm")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:_"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
}
