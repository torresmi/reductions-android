plugins {
    id("com.android.library")
    kotlin("android")
}

android {
    compileSdk = 31

    defaultConfig {
        minSdk = 23
        targetSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom:_"))

    debugImplementation("com.github.pandulapeter.beagle:log:_")
    debugImplementation("com.squareup.logcat:logcat:_")
    debugImplementation("com.github.pandulapeter.beagle:ui-drawer:_")
}
