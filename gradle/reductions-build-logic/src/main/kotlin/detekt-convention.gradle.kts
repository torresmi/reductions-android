import io.gitlab.arturbosch.detekt.Detekt

plugins {
    id("io.gitlab.arturbosch.detekt")
}

tasks.withType(Detekt::class).configureEach {
    buildUponDefaultConfig = true
    config.from("$rootDir/config/detekt/compose.yml", "$rootDir/config/detekt/core.yml")
    parallel = true
    baseline.set(file("$projectDir/baseline.xml"))
}
