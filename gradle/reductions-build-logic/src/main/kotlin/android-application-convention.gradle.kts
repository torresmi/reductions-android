import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    id("arrow-analysis-convention")
    kotlin("android")
    id("com.android.application")
    id("detekt-convention")
    id("jacoco-convention")
    id("kapt-convention")
    id("kotlin-convention")
    id("material3-convention")
    id("install-git-hooks")
}

android {
    namespace = "com.fuzzyfunctors.reductions"
    compileSdk = 34

    defaultConfig {
        minSdk = 23
        targetSdk = 34

        // Making either of these two values dynamic in the defaultConfig will
        // require a full app build and reinstallation because the AndroidManifest.xml
        // must be updated.
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            enableUnitTestCoverage = true
            enableAndroidTestCoverage = true
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro",
            )
        }
    }

    buildFeatures {
        compose = true
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

    lint {
        checkDependencies = true
        xmlReport = true
        htmlReport = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

afterEvaluate {
    tasks.named("preBuild").dependsOn("installGitHooks")
}
