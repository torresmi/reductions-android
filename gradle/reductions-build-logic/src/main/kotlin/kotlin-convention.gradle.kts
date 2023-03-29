import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

tasks.withType<JavaCompile> {
    targetCompatibility = JavaVersion.VERSION_11.toString()
}

tasks.withType(KotlinCompile::class.java).configureEach {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}
