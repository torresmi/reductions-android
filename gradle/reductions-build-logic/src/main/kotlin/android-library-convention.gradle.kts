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
    compileSdk = 34

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_11
        sourceCompatibility = JavaVersion.VERSION_11
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}
