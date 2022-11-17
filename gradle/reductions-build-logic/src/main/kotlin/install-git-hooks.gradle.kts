tasks.register("installGitHooks", Copy::class.java) {
    from(file("$rootDir/.githooks"))
    into(file("$rootDir/.git/hooks"))
    fileMode = 775
}
