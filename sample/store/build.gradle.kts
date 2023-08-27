plugins {
    id("android-application-convention")
}

android {
    namespace = "com.fuzzyfunctors.reductions.store"

    defaultConfig {
        applicationId = "com.fuzzyfunctors.reductions.store"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }
}

dependencies {
    implementation(projects.platform.flipperApi)
    implementation(projects.platform.flipper)
    implementation(projects.feature.stores.storesApi)
    implementation(projects.feature.stores.storesUi)

    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose.ui)
    implementation(libs.koin.android)
    implementation(libs.material)
}
