import com.android.build.api.dsl.ManagedVirtualDevice

plugins {
    id("android-application-convention")
    id("android-application-semver-convention")
    id("android-plus-pure-kotlin-test-runner")
}

android {
    defaultConfig {
        applicationId = "com.fuzzyfunctors.reductions"
    }

    testOptions {
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

                groups {
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

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }

    lint {
        abortOnError = false
        lintConfig = file("..lint.xml")
    }
}

dependencies {
    implementation(projects.core)
    implementation(projects.data)
    implementation(projects.domain)
    implementation(projects.presentation)
    implementation(projects.platform.debugUiApi)
    implementation(projects.platform.debugUi)
    implementation(projects.platform.flipperApi)
    implementation(projects.platform.flipper)

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
    implementation(libs.material)
    implementation(libs.remotedata)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)

    testImplementation(libs.koin.test)
    testImplementation(libs.kotest.koin)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk)

    testImplementation(projects.testUtil)

    androidTestImplementation(libs.bundles.android.test)

    detektPlugins(libs.twitter.detekt.rules)
}
