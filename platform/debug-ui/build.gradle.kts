plugins {
    id("android-library-convention")
}

android {
}

dependencies {
    implementation(project(":debug-ui-api"))

    implementation(platform(libs.kotlin.bom))

    debugImplementation(libs.beagle.log)
    debugImplementation(libs.beagle.ui.drawer)
    debugImplementation(libs.logcat)
}
