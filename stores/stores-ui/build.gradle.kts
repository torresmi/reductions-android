plugins {
    id("android-compose-library-convention")
}

dependencies {
    implementation(libs.bundles.compose.ui)
    implementation(libs.androidx.core.ktx)

    androidTestImplementation(libs.androidx.compose.ui.testing)
}
