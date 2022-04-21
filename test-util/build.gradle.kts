plugins {
    kotlin("jvm")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

dependencies {
    implementation(project(":core"))

    implementation("com.github.torresmi:remotedata:_")
    api("io.kotest:kotest-assertions-core:_")
    api("io.kotest:kotest-property:_")
    api("io.kotest:kotest-runner-junit5:_")
    api("com.appmattus.fixture:fixture:_")
    api("io.github.serpro69:kotlin-faker:_")
}
