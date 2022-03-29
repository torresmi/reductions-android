plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "com.fuzzyfunctors.reductions"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "0.1"
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
    implementation(Libs.material)
    implementation(Libs.retrofit)
    implementation(Libs.converter_moshi)
    implementation(Libs.remotedata)

    testImplementation(Libs.kotlinx_coroutines_test)
    testImplementation(Libs.koin_test)
    testImplementation(project(":test-util"))

    androidTestImplementation(Libs.espresso_core)
    androidTestImplementation(Libs.androidx_test_runner)
    androidTestImplementation(Libs.androidx_test_rules)
}
