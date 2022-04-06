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
        classpath(Libs.com_android_tools_build_gradle)
        classpath(Libs.kotlin_gradle_plugin)
        classpath(Libs.detekt_gradle_plugin)
        classpath(Libs.spotless_plugin_gradle)
        classpath(Libs.sonarqube_gradle_plugin)
        classpath(Libs.io_arrow_kt_analysis_kotlin_gradle_plugin)
        classpath(Libs.doctor_plugin)
        classpath(Libs.org_jacoco_core)
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
    }

    apply(plugin = "io.arrow-kt.analysis.kotlin")
    apply(from = "$rootDir/scripts/jacoco.gradle")
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
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
