plugins {
    alias(libs.plugins.android.lib)
    alias(libs.plugins.kotlin.android)
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
    implementation(platform(libs.kotlin.bom))

    debugImplementation(libs.beagle.log)
    debugImplementation(libs.beagle.ui.drawer)
    debugImplementation(libs.logcat)
}
