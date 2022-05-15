plugins {
    // Warning that can be ignored. https://github.com/gradle/gradle/issues/13020
    `kotlin-dsl`
}

dependencies {
    implementation(libs.android.tools.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.semver)
    implementation(libs.detekt.gradle.plugin)
    implementation(libs.sonarqube.gradle.plugin)
    implementation(libs.spotless.gradle.plugin)
    implementation(libs.arrow.analysis.gradle.plugin)
}
