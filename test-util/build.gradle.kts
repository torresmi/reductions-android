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

    implementation(libs.remotedata)
    api(libs.kotest.assertions)
    api(libs.kotest.property)
    api(libs.kotest.runner)
    api(libs.fixture)
    api(libs.kotlin.faker)
}
