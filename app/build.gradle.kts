
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

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    lint {
        checkDependencies = true
        xmlReport = true
        htmlReport = true
    }
}

tasks {
    incrementBuildMeta {
        doFirst {
            buildMeta = format("%d", buildMeta.toInt() + 1)
        }
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

    implementation(platform(Libs.kotlin_bom))

    implementation(Libs.appcompat)
    implementation(Libs.arrow_core)
    implementation(Libs.constraintlayout)
    implementation(Libs.coil)
    implementation(Libs.kotlinx_coroutines_android)
    implementation(Libs.kotlinx_coroutines_core)
    implementation(Libs.koin_android)
    implementation(Libs.logcat)
    implementation(Libs.material)
    implementation(Libs.retrofit)
    implementation(Libs.converter_moshi)
    implementation(Libs.remotedata)

    debugImplementation(Libs.flipper)
    debugImplementation(Libs.flipper_leakcanary2_plugin)
    debugImplementation(Libs.leakcanary_android)
    debugImplementation(Libs.soloader)

    releaseImplementation(Libs.flipper_noop)

    testImplementation(Libs.kotlinx_coroutines_test)
    testImplementation(Libs.koin_test)
    testImplementation(project(":test-util"))

    androidTestImplementation(Libs.espresso_core)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.androidx_test_rules)
}
