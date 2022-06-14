import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

configure<KotlinAndroidProjectExtension> {
    sourceSets.all {
        languageSettings.optIn("androidx.compose.material3.ExperimentalMaterial3Api")
    }
}
