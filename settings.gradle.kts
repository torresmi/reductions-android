plugins {
    id("de.fayard.refreshVersions") version "0.40.1"
}

refreshVersions {
    enableBuildSrcLibs()
    rejectVersionIf {
        candidate.stabilityLevel != de.fayard.refreshVersions.core.StabilityLevel.Stable
    }
}

include(
    ":app",
    ":core",
    ":data",
    ":presentation",
    ":domain",
    ":test-util",
)
