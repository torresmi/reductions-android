package dependencies

object Deps {
    val androidTestRunner = dependency("com.android.support.test:runner", Version.androidTestRunner)
    val appCompat = dependency("androidx.appcompat:appcompat", Version.appCompat)

    object Arrow : Group("io.arrow-kt") {
        val core = withArtifact("arrow-core", Version.arrow)
        val syntax = withArtifact("arrow-syntax", Version.arrow)
    }

    val constraintLayout = dependency("androidx.constraintlayout:constraintlayout", Version.constraintLayout)
    val coil = dependency("io.coil-kt:coil", Version.coil)

    object Coroutines : Group("org.jetbrains.kotlinx") {
        val android = withArtifact("kotlinx-coroutines-android", Version.coroutines)
        val core = withArtifact("kotlinx-coroutines-core", Version.coroutines)
        val test = withArtifact("kotlinx-coroutines-test", Version.coroutines)
    }

    val espresso = dependency("com.android.support.test.espresso:espresso-core", Version.espresso)

    object Koin : Group("org.koin") {
        val android = withArtifact("koin-android", Version.koin)
        val test = withArtifact("koin-test", Version.koin)
    }

    val kotlin = dependency("org.jetbrains.kotlin:kotlin-stdlib-jdk8", Version.kotlin)
    val kotlinTest = dependency("io.kotlintest:kotlintest-runner-junit5", Version.kotlinTest)
    val material = dependency("com.google.android.material:material", Version.material)
    val materialValues = dependency("blue.aodev:material-values", Version.materialValues)
    val mockk = dependency("io.mockk:mockk", Version.mockk)
    val moshi = dependency("com.squareup.moshi:moshi", Version.moshi)
    val remoteData = dependency("com.github.torresmi:remotedata", Version.remoteData)

    object Retrofit : Group("com.squareup.retrofit2") {
        val core = withArtifact("retrofit", Version.retrofit)
        val moshiConverter = withArtifact("converter-moshi", Version.retrofit)
    }
}

object Plugins {
    val android = dependency("com.android.tools.build:gradle", Version.androidGradle)
    val detekt = dependency("io.gitlab.arturbosch.detekt:detekt-gradle-plugin", Version.detektGradle)
    val jacocoAndroid = dependency("com.vanniktech:gradle-android-junit-jacoco-plugin", Version.jacocoAndroidGradle)
    val kotlin = dependency("org.jetbrains.kotlin:kotlin-gradle-plugin", Version.kotlin)
    val ktlint = dependency("org.jlleitschuh.gradle:ktlint-gradle", Version.ktlintGradle)
    val versions = dependency("com.github.ben-manes:gradle-versions-plugin", Version.gradleVersions)
}

abstract class Group(val group: String) {
    fun withArtifact(artifact: String, version: String) = "$group:$artifact:$version"
}

private fun dependency(path: String, version: String) = "$path:$version"

object Version {
    val androidGradle = "4.2.0"
    val appCompat = "1.2.0-alpha01"
    val arrow = "0.10.4"
    val androidTestRunner = "1.1.0"
    val buildTools = "29.0.2"
    val coil = "0.8.0"
    val constraintLayout = "2.0.0-beta4"
    val coroutines = "1.3.5"
    val detektGradle = "1.3.0"
    val espresso = "3.1.0"
    val gradleVersions = "0.27.0"
    val jacoco = "0.8.5"
    val jacocoAndroidGradle = "0.16.0"
    val koin = "2.1.0-alpha-8"
    val kotlin = "1.3.61"
    val kotlinTest = "3.4.2"
    val ktlintGradle = "10.2.1"
    val material = "1.2.0-alpha03"
    val materialValues = "1.1.1"
    val mockk = "1.9.3"
    val moshi = "1.9.2"
    val remoteData = "1.1"
    val retrofit = "2.7.0"

    object Sdk {
        val min = 23
        val target = 29
        val compile = 29
    }
}
