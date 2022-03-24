plugins {
    id("de.fayard.refreshVersions") version "0.40.1"
    id("com.pablisco.gradle.auto.include") version "1.3"
}

refreshVersions {
    enableBuildSrcLibs()
    rejectVersionIf {
        candidate.stabilityLevel != de.fayard.refreshVersions.core.StabilityLevel.Stable
    }
}
