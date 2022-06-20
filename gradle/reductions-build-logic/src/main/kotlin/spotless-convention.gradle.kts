import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    id("com.diffplug.spotless")
}

configure<SpotlessExtension> {
    format("misc") {
        target(
            "*.gradle",
            "*.md",
            ".gitignore",
            "*.kt",
            "*.kts",
            "*.graphql",
        )

        trimTrailingWhitespace()
        endWithNewline()
    }

    val kotlinTrailingCommaData = mapOf(
        "ij_kotlin_allow_trailing_comma" to "true",
        "ij_kotlin_allow_trailing_comma_on_call_site" to "true",
    )

    kotlin {
        target("**/*.kt")
        targetExclude(
            "$buildDir/**/*.kt",
            "bin/**/*.kt",
        )

        ktlint().userData(kotlinTrailingCommaData)
    }
    kotlinGradle {
        target("**/*.gradle.kts")
        targetExclude(
            "**/build/**/*.kts",
        )
        ktlint().userData(kotlinTrailingCommaData)
    }
}
