
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

plugins {
    id("io.gitlab.arturbosch.detekt")
}

val reportMerge by tasks.registering(ReportMergeTask::class) {
    output.set(rootProject.buildDir.resolve("reports/detekt/merge.xml"))
}

configure<DetektExtension> {
    tasks.withType(Detekt::class) detekt@{
        finalizedBy(reportMerge)

        reportMerge.configure {
            input.from(this@detekt.xmlReportFile)
        }
    }

    buildUponDefaultConfig = true
    config = files("$rootDir/config/detekt/compose.yml", "$rootDir/config/detekt/core.yml")
    parallel = true
    baseline = file("baseline.xml")
}
