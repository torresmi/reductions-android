import com.android.build.api.dsl.ApplicationExtension

plugins {
    id("com.android.application")
    kotlin("android")
    id("base-kotlin-convention")
    id("material3-opt-in-convention")
    id("jacoco-convention")
    id("disable-kapt-test")
    id("detekt-convention")
//    id("io.arrow-kt.analysis.kotlin")
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
