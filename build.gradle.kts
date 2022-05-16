import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.report.ReportMergeTask

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
        maven(url = "https://plugins.gradle.org/m2/")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
        maven(url = "https://jitpack.io")
    }

    plugins.withType(DetektPlugin::class) {
        tasks.withType(Detekt::class) detekt@{
            finalizedBy(reportMerge)

            reportMerge.configure {
                input.from(this@detekt.xmlReportFile)
            }
        }
    }
}

apply(plugin = "com.osacky.doctor")
apply(plugin = "sonarqube-convention")
apply(plugin = "spotless-convention")

tasks.named("clean", Delete::class) {
    delete(rootProject.buildDir)
}
