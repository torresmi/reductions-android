import de.fayard.refreshVersions.core.StabilityLevel

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
}

plugins {
    id("de.fayard.refreshVersions") version "0.40.2-SNAPSHOT"
    id("com.pablisco.gradle.auto.include") version "1.3"
}

refreshVersions {
    rejectVersionIf {
        candidate.stabilityLevel != StabilityLevel.Stable
    }
}
