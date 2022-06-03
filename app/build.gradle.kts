
import com.android.build.api.dsl.ManagedVirtualDevice
import org.jetbrains.kotlin.konan.properties.loadProperties
import java.lang.String.format

plugins {
    alias(libs.plugins.android.app)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.semver)
    alias(libs.plugins.sonarqube)
}

sonarqube {
    properties {
        property("sonar.androidLint.reportPaths", "build/reports/lint-results-debug.xml")
    }
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

        managedDevices {
            devices {
                val pixel2Api30 by registering(ManagedVirtualDevice::class) {
                    device = "Pixel 2"
                    apiLevel = 30
                    systemImageSource = "aosp-atd"
                }

                val pixelCApi30 by registering(ManagedVirtualDevice::class) {
                    device = "Pixel C"
                    apiLevel = 30
                    systemImageSource = "aosp-atd"
                }

                deviceGroups {
                    register("phoneAndTablet") {
                        setOf(
                            pixel2Api30,
                            pixelCApi30,
                        ).forEach {
                            targetDevices.addLater(it)
                        }
                    }
                }
            }
        }
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
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

    implementation(platform(libs.kotlin.bom))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.appcompat)
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.arrow.core)
    implementation(libs.coil)
    implementation(libs.koin.android)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.logcat)
    implementation(libs.remotedata)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)

    debugImplementation(libs.flipper)
    debugImplementation(libs.flipper.leakcanary2.plugin)
    debugImplementation(libs.leakcanary)
    debugImplementation(libs.soloader)

    releaseImplementation(libs.flipper.noop)

    testImplementation(libs.koin.test)
    testImplementation(libs.kotlinx.coroutines.test)

    testImplementation(project(":test-util"))

    androidTestImplementation(libs.androidx.compose.ui.testing)
    androidTestImplementation(libs.androidx.test.rules)
    androidTestImplementation(libs.androidx.test.runner)
}
