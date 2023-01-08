
import org.gradle.kotlin.dsl.invoke

plugins {
    id("arrow-analysis-convention")
    kotlin("jvm")
    id("detekt-convention")
    id("jacoco-convention")
    id("kapt-convention")
    id("kotlin-convention")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
