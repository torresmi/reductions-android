
import org.gradle.kotlin.dsl.invoke

plugins {
    kotlin("jvm")
    id("kapt-convention")
    id("kotlin-convention")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
