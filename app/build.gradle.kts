
import org.jetbrains.kotlin.konan.properties.loadProperties
import java.lang.String.format

plugins {
    id("com.android.application")
    kotlin("android")
    id("net.thauvin.erik.gradle.semver")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.fuzzyfunctors.reductions"
        minSdk = 23
        targetSdk = 31

        // Making either of these two values dynamic in the defaultConfig will
        // require a full app build and reinstallation because the AndroidManifest.xml
        // must be updated.
        versionCode = 1
        versionName = "0.1"
        testInstrumentationRunner = "android.support.test.runner.AndroidJUnitRunner"
    }

    androidComponents.onVariants { variant ->
        if (variant.buildType == "release") {
            val properties = loadProperties("app/version.properties")
            variant.outputs.forEach {
                it.versionCode.set(properties.getProperty("version.buildmeta").toInt())
                it.versionName.set(properties.getProperty("version.semver").substringBefore("+"))
            }
        }
    }

    buildTypes {
        getByName("debug") {
            isTestCoverageEnabled = true
        }

        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                "proguard-rules.pro"
            )
        }
    }

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-alpha07"
    }

    lint {
        checkDependencies = true
        xmlReport = true
        htmlReport = true
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

tasks {
    incrementBuildMeta {
        doFirst {
            buildMeta = format("%d", buildMeta.toInt() + 1)
        }
    }
}

kotlin {
    sourceSets.all {
        languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
    }
}

afterEvaluate {
    val appModule = this
    val testDebugTask = tasks.named("testDebugUnitTest")
    val testReleaseTask = tasks.named("testReleaseUnitTest")

    rootProject.subprojects.forEach { module ->
        module.afterEvaluate {
            val testTaskKey = "test"

            module?.takeUnless { it == appModule }
                ?.tasks
                ?.find { it.name.equals(testTaskKey) }
                ?.let {
                    testDebugTask {
                        dependsOn(it)
                    }
                    testReleaseTask {
                        dependsOn(it)
                    }
                }
        }
    }
}

dependencies {
    implementation(project(":data"))
    implementation(project(":presentation"))
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(project(":debug-ui"))

    implementation(platform("org.jetbrains.kotlin:kotlin-bom:_"))

    implementation("androidx.activity:activity-compose:_")
    implementation("androidx.appcompat:appcompat:_")
    implementation("androidx.compose.animation:animation:_")
    implementation("io.arrow-kt:arrow-core:_")
    implementation("io.coil-kt:coil:_")
    implementation("androidx.core:core-ktx:_")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:_")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:_")
    implementation("io.insert-koin:koin-android:_")
    implementation("com.squareup.logcat:logcat:_")
    implementation("androidx.compose.material3:material3:_")
    implementation("com.squareup.retrofit2:retrofit:_")
    implementation("com.squareup.retrofit2:converter-moshi:_")
    implementation("com.github.torresmi:remotedata:_")
    implementation("androidx.compose.ui:ui-tooling:_")

    debugImplementation("com.facebook.flipper:flipper:_")
    debugImplementation("com.facebook.flipper:flipper-leakcanary2-plugin:_")
    debugImplementation("com.squareup.leakcanary:leakcanary-android:_")
    debugImplementation("com.facebook.soloader:soloader:_")

    releaseImplementation("com.facebook.flipper:flipper-noop:_")

    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:_")
    testImplementation("io.insert-koin:koin-test:_")
    testImplementation(project(":test-util"))

    androidTestImplementation("androidx.test:runner:_")
    androidTestImplementation("androidx.test:rules:_")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:_")
}
