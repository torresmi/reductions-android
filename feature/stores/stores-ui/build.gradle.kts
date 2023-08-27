plugins {
    id("android-library-convention")
}

dependencies {
    implementation(projects.feature.stores.storesApi)
    implementation(projects.feature.stores.storesData)

    implementation(platform(libs.kotlin.bom))

    implementation(libs.bundles.compose.ui)
    implementation(libs.material)
    implementation(libs.remotedata)

    testImplementation(projects.testUtil)

    testImplementation(libs.koin.test)
    testImplementation(libs.kotlinx.coroutines.test)

    androidTestImplementation(libs.bundles.android.test)
}
