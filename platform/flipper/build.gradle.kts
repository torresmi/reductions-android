plugins {
    id("android-library-convention")
}

android {
}

dependencies {
    implementation(project(":platform:flipper-api"))

    implementation(platform(libs.kotlin.bom))
    implementation(platform(libs.okhttp.bom))

    implementation(libs.okhttp)
    implementation(libs.koin.core)

    debugImplementation(libs.flipper)
    debugImplementation(libs.flipper.leakcanary2.plugin)
    debugImplementation(libs.flipper.network.plugin)
    debugImplementation(libs.leakcanary)
    debugImplementation(libs.soloader)

    releaseImplementation(libs.flipper.noop)
}
