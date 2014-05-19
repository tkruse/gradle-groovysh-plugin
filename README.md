# Gradle plugin providing a Groovy Shell

[![Build Status](https://travis-ci.org/tkruse/gradle-groovysh-plugin.svg)](https://travis-ci.org/tkruse/gradle-groovysh-plugin)

This plugin provides gradle tasks that start an interactive groovy shells, based on
the '[groovysh](http://groovy.codehaus.org/Groovy+Shell)' command that ships with any [Groovy](http://groovy.codehaus.org/) version.

The main feature is an Application shell, meaning a shell where your application
classes (Java or other JVM language) can be imported, instantiated and run. This allows you to interact
directly with your database-layer, service layer, running application, etc. without having to
change a line of your code.

Another particular feature is a build development shell that has a variable ```project``` which represents
your gradle project, allowing you to introspect your project after it has been instantiated.

Finally there is a build shell, which allows to connect to a gradle project using the gradle tooling API to run builds.


This plugin is **Work In Progress**, expect some rough edges.


## Prerequisites

* [Java](http://www.java.com/)
* [Gradle](http://www.gradle.org) (From gradle wrapper is fine)

- **NO GROOVY INSTALLATION REQUIRED**
- **NO CHANGE TO YOUR JAVA CODE REQUIRED**



## Using the Plugin

In your buildgradle, apply the plugin:

```Groovy
apply plugin: 'groovysh'
```

Invoke either shell task with option ```-q```.
If you have the gradle daemon configured, also add ```--no-daemon```

```bash
gradle -q shell
gradle -q buildShell
gradle -q buildDevShell
```

When using the gradle wrapped, that would be ```./gradlew``` instead.

## Configuring the Plugin

To change the defaults, use a configuration block as below. All parts are optional.

```Groovy
groovysh {

    // false to disable the buildDevShell task
    enablebuildDevShell = true
    // false to disable the buildDevShell task
    enableAppShell = true

    // groovyVersion determines the features of the shell and buildShell tasks
    // groovyVersion = '2.3.0'

    shell {
        // default is 'main'
        sourceSetName = 'test'
        extraClasspath = configurations.runtime.asFileTree
        workingDir = file('src/main/webapp')
        jvmArgs = ['-Xmx1024m']
        /* arguments to groovysh */
        // args = ['--terminal=none']
        // groovyVersion = '2.3.0'
        /* ... the other params of gradle JavaExec can also be used */
    }

    buildShell {

       // gradle version determines version of connector in tooling API,
       // but connector can use other gradle version to build project via
       // connector.useGradleVersion('1.10')
       // gradleVersion = '1.12'

       /* ... params of gradle JavaExec can also be used */
    }

    buildDevShell {
    }
}
```




## Features

- Run a shell task with application classes
- Run a buildShell task with gradle on the classpath
- Run a buildDevShell task where the ```project``` variable is the same as in ```build.gradle``` file
- shell task: Configure SourceSet (test or main)
- shell task uses independent configuration (extends runtime or testRuntime)
- shell task: Configure Task JavaExec params
- Configure shell and buildShell Tasks Groovy Version
- Checks whether Daemon or Parallel mode is on
- Tasks can be disabled

### Wishlist / Brainstorming / TODOs

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


### Some things you can do with the ```shell```

- Instantiate a Spring Container (provided you add Spring dependencies to your project)
- Instantiate a Database connection, write business entities etc.
- Run your algorithms interactively

### Some things you can do with the ```buildShell```

```Groovy
groovy:000> import org.gradle.tooling.*
===> org.gradle.tooling.*
groovy:000> import org.gradle.tooling.model.*
===> org.gradle.tooling.model.*
groovy:000> connector = GradleConnector.newConnector()
===> org.gradle.tooling.internal.consumer.DefaultGradleConnector@6d3666fb
groovy:000> connector.forProjectDirectory(new File("."))
groovy:000> connection = connector.connect()
===> org.gradle.tooling.internal.consumer.DefaultProjectConnection@598b4d64
groovy:000> project = connection.getModel(GradleProject.class)
===> GradleProject{path=':'}
groovy:000> launcher = connection.newBuild()
===> org.gradle.tooling.internal.consumer.DefaultBuildLauncher@3a370a0
groovy:000> launcher.run()
```

### Some things you can do with the ```buildDevShell```:

```Groovy
groovy:000> project.tasks
===> [task ':assemble', task ':build', ...
groovy:000> project.configurations
===> [configuration ':appShellConf', configuration ':archives', ...
groovy:000> project.ext.properties
===> {org.gradle.parallel=false, org.gradle.daemon=false, SLF4J_VERSION=1.7.7}
groovy:000> project.repositories.each {println it.name}
BintrayJCenter
groovy:000> project.apply(plugin: 'java')
groovy:000> project.sourceSets.main.getCompileTaskName()
===> compileMain
groovy:000> project.tasks.compileJava.execute() // only executes once
groovy:000> project.tasks.clean.execute() // only executes once
groovy:000> project.gradle.startParameter
===> StartParameter{taskNames=[buildDevShell], ...}
```

### Caveats


Notice that the Groovy version for the build shell is the same as for gradle (1.8.6), whereas for the application
shell a much more recent version of Groovy (2.3.x) can be used. As a consequence the application shell is much prettier,
and the allowed syntax is different.

Also if you are new to groovysh, the number one quirk to know is that you **must not** declare variables, e.g.:

```Groovy
groovy:000> def x = 3
===> 3
groovy:000> x
Unknown property: x
groovy:000> x = 3
===> 3
groovy:000> x
===> 3
```


## Installing the plugin


Clone this repository as ```buildSrc``` subfolder of your gradle root project.

Bring some patience for the unit tests.
A better way will be provided as this plugin matures.


### Getting a shell without installing this plugin:

See the docs at:
 - [Manual Build Shell README](doc/InstallBuildDevShellManually.md)
 - [Manual App Shell README](doc/InstallAppShellManually.md)
Or just install Groovy and run ```groovysh``` with a suitable classpath.

## Compatibility

TODO: Extend with time

* Java Versions (6?, 7, 8)

* Operating Systems (Ubuntu Precise)

* Gradle versions (1.8?, 1.9, 1.10?, 1.11?, 1.12?)

* Groovy Versions (>= 2.2.1)
