plugins {
    alias(libs.plugins.kotlin.jvm)
}

tasks {
    test {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(platform(libs.kotlin.bom))

    implementation(libs.kotlinx.coroutines.core)
}
