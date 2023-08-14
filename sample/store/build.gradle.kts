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
    implementation(project(":stores-api"))
    implementation(project(":stores-ui"))

    implementation(libs.androidx.activity.compose)
    implementation(libs.bundles.compose.ui)
    implementation(libs.material)
}
