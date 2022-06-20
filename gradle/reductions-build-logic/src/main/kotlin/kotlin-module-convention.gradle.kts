
import org.gradle.kotlin.dsl.invoke

plugins {
    kotlin("jvm")
    id("detekt-convention")
    id("kapt-convention")
    id("kotlin-convention")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
