plugins {
    kotlin("jvm")
    id("base-kotlin-convention")
    id("jacoco-convention")
    id("disable-kapt-test")
    id("detekt-convention")
    id("io.arrow-kt.analysis.kotlin")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
