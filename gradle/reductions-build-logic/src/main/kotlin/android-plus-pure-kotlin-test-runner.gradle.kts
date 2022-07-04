import com.android.build.gradle.BaseExtension

/**
 * Runs other non-android project tests upon running an Android test variant
 */
configure<BaseExtension> {
    afterEvaluate {
        val testDebugTask = tasks.named("testDebugUnitTest")
        val testReleaseTask = tasks.named("testReleaseUnitTest")
        rootProject.subprojects
            .filter {
                !it.plugins.hasPlugin("com.android.library") &&
                    !it.plugins.hasPlugin("com.android.application")
            }
            .forEach { module ->
                module.afterEvaluate {
                    val testTaskKey = "test"

                    module?.tasks
                        ?.find { it.name.equals(testTaskKey) }
                        ?.let {
                            testDebugTask {
                                dependsOn(it)
                            }
                            testReleaseTask {
                                dependsOn(it)
                            }
                        }
                }
            }
    }
}
