# Gradle Benchmark

The fantastic [gradle-profiler](https://github.com/gradle/gradle-profiler) tool lets us benchmark different Gradle commands and fine-tune Gradle.

## Builds

We often refer to "build times" as how long it takes for us to compile the project. However, this can vary a lot based on different factors.

* Clean build or incremental build?
* Gradle daemon enabled?
    * Gradle daemon warmed up?
    * How warm is it?
* Build cache enabled?
* Configuration cache enabled?
* Multi-module project? 
    * What module or modules have changes?
    * Are those changes impacting other modules?
* Are we changing resource files that need to be processed?
* Are there other programs using computer resources as well?

There are many things that can cause builds to vary drastically. So instead of thinking of a single concept of "build times", I like to group these up into build scenarios. Then run benchmarks on these scenarios to get a good idea of average times. This then allows tweaking and verifying through a more repeatable system. 

### Running

To run the benchmark for builds we can execute the `benchmark_builds.sh` file. This sources scenarios from `build-scenarios.txt`. Try to avoid running other programs that can use up a lot of resources.
