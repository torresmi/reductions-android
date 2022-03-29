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

    implementation(Libs.remotedata)
    api(Libs.kotest_assertions_core)
    api(Libs.kotest_property)
    api(Libs.kotest_runner_junit5)
    api(Libs.fixture)
    api(Libs.kotlin_faker)
}
