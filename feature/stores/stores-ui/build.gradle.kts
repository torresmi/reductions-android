plugins {
    id("android-library-convention")
}

dependencies {
    api(project(":stores-data"))
    implementation(project(":stores-api"))

    implementation(platform(libs.kotlin.bom))

    implementation(libs.bundles.compose.ui)
    implementation(libs.material)
    implementation(libs.remotedata)

    testImplementation(project(":test-util"))

    testImplementation(libs.koin.test)
    testImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.bundles.android.test)
}
