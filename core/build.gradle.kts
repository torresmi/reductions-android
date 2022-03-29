plugins {
    kotlin("jvm")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(platform(Libs.kotlin_bom))

    implementation(Libs.kotlinx_coroutines_core)
}
