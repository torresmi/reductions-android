// Disable kapt for tests
tasks.matching {
    it.name.startsWith("kapt") && it.name.endsWith("TestKotlin")
}.configureEach { enabled = false }
