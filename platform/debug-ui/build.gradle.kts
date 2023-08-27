plugins {
    id("android-library-convention")
}

android {
}

dependencies {
    implementation(projects.platform.debugUiApi)

    implementation(platform(libs.kotlin.bom))

    debugImplementation(libs.beagle.log)
    debugImplementation(libs.beagle.ui.drawer)
    debugImplementation(libs.logcat)
}
