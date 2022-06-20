import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

plugins {
    alias(libs.plugins.android.app) version libs.versions.android.tools apply false
    alias(libs.plugins.android.lib) version libs.versions.android.tools apply false
    alias(libs.plugins.kotlin.android) version libs.versions.kotlin apply false
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.detekt)
    alias(libs.plugins.spotless)
    alias(libs.plugins.doctor)
    alias(libs.plugins.arrow.analysis)
}

val reportMerge by tasks.registering(ReportMergeTask::class) {
    output.set(rootProject.buildDir.resolve("reports/detekt/merge.xml"))
}

allprojects {
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
}

apply(from = "$rootDir/scripts/spotless.gradle")
apply(plugin = "sonarqube-convention")

tasks.named("clean", Delete::class) {
    delete(rootProject.buildDir)
}
