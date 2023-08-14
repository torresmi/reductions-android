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
    ":domain",
    ":presentation",
    ":test-util",
)

// Include all feature modules
file("feature")
    .walkTopDown()
    .maxDepth(2)
    .forEach(::includeNestedDir)

// Include all common modules
file("common")
    .walkTopDown()
    .maxDepth(1)
    .forEach(::includeNestedDir)

// Include all platform modules
file("platform")
    .walkTopDown()
    .maxDepth(1)
    .forEach(::includeNestedDir)

// Optionally include sample apps. Uncomment as needed for faster feature development
include(
//    ":sample:store",
)

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
