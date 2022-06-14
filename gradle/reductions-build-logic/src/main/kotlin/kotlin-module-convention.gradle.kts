
import org.gradle.kotlin.dsl.invoke

plugins {
    kotlin("jvm")
    id("kotlin-convention")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
