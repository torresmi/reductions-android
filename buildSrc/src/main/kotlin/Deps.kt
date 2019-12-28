package dependencies

object Deps {

    val androidTestRunner = dependency("com.android.support.test:runner", Version.androidTestRunner)
    val appCompat = dependency("androidx.appcompat:appcompat", Version.appCompat)
    val arrow = dependency("io.arrow-kt:arrow-core", Version.arrow)
    val constraintLayout = dependency("androidx.constraintlayout:constraintlayout", Version.constraintLayout)
    val espresso = dependency("com.android.support.test.espresso:espresso-core", Version.espresso)
    object Glide : Group("com.github.bumptech.glide") {
        val core = withArtifact("glide", Version.glide)
        val compiler = withArtifact("compiler", Version.glide)
    }
    val junit = dependency("junit:junit", Version.junit)
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
        val rxJavaCallAdapter = withArtifact("adapter-rxjava2", Version.retrofit)
    }
    object RxJava : Group("io.reactivex.rxjava2") {
        val android = withArtifact("rxandroid", Version.rxAndroid)
        val core = withArtifact("rxjava", Version.rxJava)
    }
    val rxRelay = dependency("com.jakewharton.rxrelay2:rxrelay", Version.rxRelay)
}

object Plugins {
    val androidTools = dependency("com.android.tools.build:gradle", Version.androidBuildTools)
    val junit = dependency("org.junit.platform:junit-platform-gradle-plugin", Version.junitPlatform)
    val kotlin = dependency("org.jetbrains.kotlin:kotlin-gradle-plugin", Version.kotlin)
    val versions = dependency("com.github.ben-manes:gradle-versions-plugin", Version.gradleVersions)
}

abstract class Group(val group: String) {
    fun withArtifact(artifact: String, version: String) = "$group:$artifact:$version"
}

private fun dependency(path: String, version: String) = "$path:$version"

private object Version {
    val androidBuildTools = "3.5.3"
    val appCompat = "1.1.0-alpha01"
    val arrow = "0.8.1"
    val androidTestRunner = "1.0.2"
    val constraintLayout = "2.0.0-alpha2"
    val espresso = "3.0.2"
    val glide = "4.8.0"
    val gradleVersions = "0.20.0"
    val junit = "4.13-beta-1"
    val junitPlatform = "1.0.1"
    val koin = "1.0.1"
    val kotlin = "1.3.10"
    val kotlinTest = "3.1.10"
    val material = "1.1.0-alpha02"
    val materialValues = "1.1.1"
    val mockk = "1.8.13"
    val moshi = "1.8.0"
    val remoteData = "1.1"
    val retrofit = "2.5.0"
    val rxAndroid = "2.1.0"
    val rxJava = "2.2.4"
    val rxRelay = "2.1.0"
}
