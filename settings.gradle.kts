pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://s01.oss.sonatype.org/content/repositories/snapshots")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

plugins {
    id("com.gradle.enterprise") version "3.14.1"
}

includeBuild("gradle/reductions-build-logic")
include(
    ":app",
    ":core",
    ":data",
    ":debug-ui",
    ":domain",
    ":presentation",
    ":test-util",
)

// Include all application modules
file("apps")
    .walkTopDown()
    .maxDepth(1)
    .forEach(::includeNestedDir)

// Include all feature modules
file("features")
    .walkTopDown()
    .maxDepth(2)
    .forEach(::includeNestedDir)

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

fun includeNestedDir(dir: File) {
    val name = dir.name
    include(name)
    project(":$name").projectDir = dir
}
