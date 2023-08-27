plugins {
    id("android-library-convention")
}

dependencies {
    api(project(":feature:stores:stores-data"))
    implementation(project(":feature:stores:stores-api"))

    implementation(platform(libs.kotlin.bom))

    implementation(libs.bundles.compose.ui)
    implementation(libs.material)
    implementation(libs.remotedata)

    testImplementation(project(":test-util"))

    testImplementation(libs.koin.test)
    testImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.bundles.android.test)
}
