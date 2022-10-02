import com.android.build.api.dsl.ApplicationExtension

plugins {
    id("arrow-analysis-convention")
    kotlin("android")
    id("com.android.application")
    id("detekt-convention")
    id("jacoco-convention")
    id("kapt-convention")
    id("kotlin-convention")
    id("material3-convention")
}

configure<ApplicationExtension> {
    defaultConfig {
        // Making either of these two values dynamic in the defaultConfig will
        // require a full app build and reinstallation because the AndroidManifest.xml
        // must be updated.
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
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

    lint {
        checkDependencies = true
        xmlReport = true
        htmlReport = true
    }
}
