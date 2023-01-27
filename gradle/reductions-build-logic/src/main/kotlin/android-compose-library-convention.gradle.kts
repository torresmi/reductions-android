plugins {
    id("android-library-convention")
}

android {
    buildFeatures {
        compose = true
    }

    composeOptions {
        val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")
        val composeCompilerVersion = libs.findVersion("androidx-compose-compiler")
        kotlinCompilerExtensionVersion = composeCompilerVersion.get().requiredVersion
    }
}
