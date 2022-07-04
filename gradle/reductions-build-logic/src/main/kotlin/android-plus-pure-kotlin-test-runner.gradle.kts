import com.android.build.gradle.BaseExtension

/**
 * Runs other non-android project tests upon running an Android test variant
 */
configure<BaseExtension> {
    afterEvaluate {
        val mainModule = this
        val testDebugTask = tasks.named("testDebugUnitTest")
        val testReleaseTask = tasks.named("testReleaseUnitTest")
        rootProject.subprojects.forEach { module ->
            val testTaskKey = "test"

            module?.takeUnless { it == mainModule }
                ?.tasks
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
