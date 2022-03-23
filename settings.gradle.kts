plugins {
    id("de.fayard.refreshVersions") version "0.40.1"
    id("com.pablisco.gradle.automodule") version "0.15"
}

refreshVersions {
    enableBuildSrcLibs()
    rejectVersionIf {
        candidate.stabilityLevel != de.fayard.refreshVersions.core.StabilityLevel.Stable
    }
}
