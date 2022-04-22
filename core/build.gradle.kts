plugins {
    kotlin("jvm")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(platform(libs.kotlin.bom))

    implementation(libs.kotlin.coroutines.core)
}
