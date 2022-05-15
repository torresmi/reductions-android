plugins {
    id("kotlin-convention")
}

dependencies {
    implementation(platform(libs.kotlin.bom))

    implementation(libs.kotlin.coroutines.core)
}
