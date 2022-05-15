import org.sonarqube.gradle.SonarQubeExtension

plugins {
    id("org.sonarqube")
}

configure<SonarQubeExtension> {
    properties {
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.projectKey", System.getenv("SONAR_PROJECT_KEY"))
        property("sonar.organization", System.getenv("SONAR_ORG_KEY"))
        property("sonar.login", System.getenv("SONAR_TOKEN"))
        property("sonar.junit.reportPaths", "build/test-results/**/*.xml")
        property("sonar.jacoco.reportPaths", "build/reports/jacoco/**/*.xml")
        property("sonar.kotlin.detekt.reportPaths", System.getenv("GITHUB_WORKSPACE") + "/build/reports/detekt/merge.xml")
        property("sonar.androidLint.reportPaths", System.getenv("GITHUB_WORKSPACE") + "/app/build/reports/lint-results-debug.xml")
    }
}

val skipProjects = setOf(
    ":test-util",
    ":debug-ui"
)
skipProjects.forEach {
    project(it) {
        sonarqube {
            isSkipProject = true
        }
    }
}
