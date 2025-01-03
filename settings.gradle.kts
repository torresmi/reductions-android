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
    id("com.gradle.enterprise") version "3.18.2"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "reductions-android-root"

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
    .forEach(::includeDir)

// Include all platform modules
file("platform")
    .walkTopDown()
    .maxDepth(1)
    .forEach(::includeDir)

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

fun includeDir(dir: File) {
    val path = dir.path
        .substringAfterLast(rootDir.name)
        .replace("/", ":")

    include(path)
    project(path).projectDir = dir
}
