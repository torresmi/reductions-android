plugins {
    id("android-library-convention")
}

android {
}

dependencies {
    implementation(project(":flipper-api"))

    implementation(platform(libs.kotlin.bom))

    debugImplementation(libs.flipper)
    debugImplementation(libs.flipper.leakcanary2.plugin)
    debugImplementation(libs.leakcanary)
    debugImplementation(libs.soloader)

    releaseImplementation(libs.flipper.noop)
}
