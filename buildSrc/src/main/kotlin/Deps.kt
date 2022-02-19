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
    fun withArtifact(artifact: String, version: String) = "$group:$artifact:_"
}

private fun dependency(path: String, version: String) = "$path:_"

object Version {
    val androidGradle = "_"
    val appCompat = "_"
    val arrow = "_"
    val androidTestRunner = "_"
    val buildTools = "_"
    val coil = "_"
    val constraintLayout = "_"
    val coroutines = "_"
    val detektGradle = "_"
    val espresso = "_"
    val gradleVersions = "_"
    val jacoco = "_"
    val jacocoAndroidGradle = "_"
    val koin = "_"
    val kotlin = "_"
    val kotlinTest = "_"
    val ktlintGradle = "_"
    val material = "_"
    val materialValues = "_"
    val mockk = "_"
    val moshi = "_"
    val remoteData = "_"
    val retrofit = "_"

    object Sdk {
        val min = 23
        val target = 29
        val compile = 29
    }
}
