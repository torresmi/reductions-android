import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }
    dependencies {
        classpath(libs.android.tools.gradle.plugin)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.detekt.gradle.plugin)
        classpath(libs.spotless.gradle.plugin)
        classpath(libs.sonarqube.gradle.plugin)
        classpath(libs.arrow.analysis.gradle.plugin)
        classpath(libs.doctor.gradle.plugin)
        classpath(libs.org.jacoco.core)
        classpath(libs.semver)
    }
}

val reportMerge by tasks.registering(ReportMergeTask::class) {
    output.set(rootProject.buildDir.resolve("reports/detekt/merge.xml"))
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }

    // Disable kapt for tests
    tasks.matching {
        it.name.startsWith("kapt") && it.name.endsWith("TestKotlin")
    }.configureEach { enabled = false }

    apply(from = "$rootDir/scripts/detekt.gradle")
    plugins.withType(DetektPlugin::class) {
        tasks.withType(Detekt::class) detekt@{
            finalizedBy(reportMerge)

            reportMerge.configure {
                input.from(this@detekt.xmlReportFile)
            }
        }
    }
}

subprojects {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        maven(url = "https://jitpack.io")
    }

    apply(plugin = "io.arrow-kt.analysis.kotlin")
    apply(from = "$rootDir/scripts/jacoco.gradle")
}

tasks.withType(KotlinCompile::class)
    .configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }

apply(plugin = "com.osacky.doctor")
apply(from = "$rootDir/scripts/spotless.gradle")
apply(from = "$rootDir/scripts/sonarqube.gradle")

tasks.named("clean", Delete::class) {
    delete(rootProject.buildDir)
}
