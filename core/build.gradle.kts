plugins {
    id("kotlin-module-convention")
}

dependencies {
    implementation(platform(libs.kotlin.bom))

    implementation(libs.kotlinx.coroutines.core)
}
