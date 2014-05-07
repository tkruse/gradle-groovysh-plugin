# Gradle plugin providing a Groovy Shell

[![Build Status](https://travis-ci.org/tkruse/gradle-groovysh-plugin.svg)](https://travis-ci.org/tkruse/gradle-groovysh-plugin)

This plugin provides gradle tasks that start an interactive groovy shells, based on
the '[groovysh](http://groovy.codehaus.org/Groovy+Shell)' command that ships with any [Groovy](http://groovy.codehaus.org/) version.

The main particular feature is an Application shell, meaning a shell where your application
classes (Java or other JVM language) can be imported, instantiated and run. This allows you to interact
directly with your database-layer, service layer, running application, etc. without having to
change a line of your code.

The other particular feature is that a build shell has a variable ```project``` which represents
your gradle project, allowing you to introspect your project after it has been instantiated.


This plugin is **Work In Progress**, expect some rough edges.


## Prerequisites

* [Java](http://www.java.com/)
* [Gradle](http://www.gradle.org)

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
gradle -q buildshell
```

## Configuring the Plugin

To change the defaults, use a configuration block as below. All parts are optional.

```Groovy
groovysh {

    // false to disable the buildshell task
    enableBuildShell = true
    // false to disable the buildshell task
    enableAppShell = true

    buildShell {
    }

    shell {
        // default is 'main'
        sourceSetName = 'test'
        extraClasspath = configurations.runtime.asFileTree
        workingDir = file('src/main/webapp')
        jvmArgs = ['-Xmx1024m']
        /* arguments to groovysh */
        // args = ['--terminal=none']
        /* ... the other params of gradle JavaExec can also be used */
    }
}
```




## Features

- Run a shell where ```project``` variable is the same as in ```build.gradle``` file
- Run a shell with application classes (Prototype)
- AppShell: Configure SourceSet (test or main)
- Disable Tasks (become hidden)
- AppShell uses independent configuration (extends runtime or testRuntime)
- AppShell: Configure Task JavaExec params

### Wishlist / Brainstorming / TODOs

- Configure ApplicationShell Task Groovy Version
- Configure ApplicationShell Broken Ansi Keyboard (may require Groovy 2.3.2)
- Define ApplicationShell initial commands, imports (may require Groovy 2.3.2)
- Better checking whether Daemon or Parallel mode is on
- Clarify whether to use Groovy indy jar
- Java 8 check compatibility
- Tutorials / Examples
    - Spring Container
    - mongodb
    - hibernate
    - running unit tests without creating fixture again?
    - MBeans
    - REST call HTML / JSON processing
    - FileSets
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

### Somethings you can do with the ```buildshell```:

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
```

### Caveats


Notice that the groovy version for the build shell is the same as for gradle (1.8.6), whereas for the application
shell a much more recent version of groovy (2.3.x) can be used. As a consequence the application shell is much prettier,
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


For now, clone this repository as ```buildSrc``` subfolder of your gradle root project.
Alternatively, you can clone it into the ```buildSrc``` subfolder and add this buildgradle in the buildSrc folder:

```Groovy
apply plugin: "java"
dependencies {
    runtime subprojects.collect { owner.project(it.path) }
}
```

and a settings.gradle:

```Groovy
include 'gradle-groovysh-plugin'
```

Bring some patience for the unit tests.
A better way will be provided as this plugin matures.


## Getting a shell without depending on this plugin:

See the docs at:
 - [Manual Build Shell README](doc/InstallBuildShellManually.md)
 - [Manual App Shell README](doc/InstallAppShellManually.md)


## Building the Plugin

To build the plugin, just type the following commmand:

```bash
./gradlew clean build
```

## Compatibility

TODO: Extend with time

* Java Versions (7)

* Operating Systems (Ubuntu Precise)

* Gradle versions (1.9)

* Groovy Versions (2.2.2)
