# Wishlist / Brainstorming / TODOs

- Define ApplicationShell initial commands, imports (may require Groovy 2.3.2)
- Clarify whether to use Groovy indy jar
- Check project with ASM dependency
- Run without building classes for Groovy classpath?
- Java 8 check compatibility
- Fix classpath issues for Groovy <= 2.2.0
- test multiple gradle & groovy versions
- Tutorials / Examples / sampleProjects
    - Contributing
    - Spring Container
    - mongodb
    - hibernate
    - running unit tests without creating fixture again?
    - MBeans
    - REST call HTML / JSON processing
    - FileSets
    - Unit tests repeat
    - gradle tooling API
- Release to bintray / Maven central
- Check shell on Windows / MacOS
- Automatically go quiet
- Print gradle info on task execution keeping the grooovysh prompt below that, somehow.
- Support for class reloading when application sources changes
- Support for class reloading when gradle sources changes
- Support for running tasks multiple times forcefully
- Maven groovysh plugin
- Gradle/Maven scala shell plugin
- Gradle/Maven clojure shell plugin
- Promote to standard gradle plugin


## Done

- Run a ```shell``` task with application classes
- Run a ```buildShell``` task with gradle on the classpath
- Run a ```buildDevShell``` task where the ```project``` variable is the same as in ```build.gradle``` file
- ```shell``` task: Configure SourceSet (test or main)
- ```shell``` task uses independent configuration (extends runtime or testRuntime)
- ```shell``` task: Configure Task JavaExec params
- Configure ```shell``` and ```buildShell``` Tasks Groovy Version
- Checks whether Daemon or Parallel mode is on
- Tasks can be disabled
