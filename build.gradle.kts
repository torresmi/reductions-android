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
    id("sonarqube-convention")
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

apply(from = "$rootDir/scripts/spotless.gradle")

tasks.named("clean", Delete::class) {
    delete(rootProject.buildDir)
}
