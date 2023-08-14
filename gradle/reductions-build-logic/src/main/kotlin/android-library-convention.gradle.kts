plugins {
    id("arrow-analysis-convention")
    kotlin("android")
    id("com.android.library")
    id("detekt-convention")
    id("jacoco-convention")
    id("kapt-convention")
    id("kotlin-convention")
}

android {
    val formattedProjectName = projectDir.name.replace("-", ".")
    namespace = "com.fuzzyfunctors.reductions.$formattedProjectName"
    compileSdk = 33
}
