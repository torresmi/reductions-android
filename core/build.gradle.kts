plugins {
    id("kotlin-library-convention")
}

dependencies {
    implementation(platform(libs.kotlin.bom))

    implementation(libs.kotlinx.coroutines.core)
}
