plugins {
    id("android-library-convention")
}

android {
    namespace = "com.fuzzyfunctors.reductions.debug.ui"

    compileSdk = 33

    defaultConfig {
        minSdk = 23

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_11
        sourceCompatibility = JavaVersion.VERSION_11
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
