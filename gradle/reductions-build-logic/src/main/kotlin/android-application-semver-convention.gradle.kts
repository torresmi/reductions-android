import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import net.thauvin.erik.gradle.semver.SemverIncrementBuildMetaTask
import org.jetbrains.kotlin.konan.properties.loadProperties

plugins {
    id("net.thauvin.erik.gradle.semver")
}

tasks.withType<SemverIncrementBuildMetaTask> {
    doFirst {
        buildMeta = java.lang.String.format("%d", buildMeta.toInt() + 1)
    }
}

configure<ApplicationAndroidComponentsExtension> {
    onVariants { variant ->
        if (variant.buildType == "release") {
            val properties = loadProperties("${project.path.drop(1)}/version.properties")
            variant.outputs.forEach {
                it.versionCode.set(properties.getProperty("version.buildmeta").toInt())
                it.versionName.set(properties.getProperty("version.semver").substringBefore("+"))
            }
        }
    }
}
