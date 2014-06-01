# Wishlist / Brainstorming / TODOs

- Check project with ASM dependency
- Clarify whether to use Groovy indy jar
- Run without building classes for Groovy classpath?
- Groovysh Tips and tricks
- Fix declared vs inferred version checking
- check for invalid keys in extension (typos)
- Tutorials / Examples / sampleProjects
    - hibernate
    - running unit tests without creating fixture again?
    - JMX / MBeans
    - REST call HTML / JSON processing
    - FileSets
    - Unit tests repeat
    - gradle tooling API
- Check shell on Windows / MacOS
- Automatically go quiet
- Print gradle info on task execution keeping the grooovysh prompt below that, somehow.
- Support for class reloading when application sources changes
- Support for class reloading when gradle sources changes
- Support for running tasks multiple times forcefully
- Maven groovysh plugin (GMavenPLus has a buildShell)
- Promote to standard gradle plugin


## Done

- Prepopulate Variables for buildShell connection
- Define ApplicationShell initial commands or imports
- Run a ```shell``` task with application classes
- Run a ```buildShell``` task with gradle on the classpath
- Run a ```buildDevShell``` task where the ```project``` variable is the same as in ```build.gradle``` file
- ```shell``` task: Configure SourceSet (test or main)
- ```shell``` task uses independent configuration (extends runtime or testRuntime)
- ```shell``` task: Configure Task JavaExec params
- Configure ```shell``` and ```buildShell``` Tasks Groovy Version
- Checks whether Daemon or Parallel mode is on
- Tasks can be disabled
- Custom tasks can be defined by extending the Task classes
