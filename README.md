# Gradle plugin providing a Groovy Shell

---

**This plugin is broken starting with gradle 2.13, and I currently have no time to fix the issues.**

**If you like to have a REPL, an alternative is to explicitly create one as part of your project, see [docs](doc/InstallAppShellManually.md)**

---


[![Build Status](https://travis-ci.org/tkruse/gradle-groovysh-plugin.svg)](https://travis-ci.org/tkruse/gradle-groovysh-plugin)
[![Download](https://api.bintray.com/packages/tkruse/maven/gradle-groovysh-plugin/images/download.svg) ](https://bintray.com/tkruse/maven/gradle-groovysh-plugin/_latestVersion)

This plugin provides gradle tasks that start an interactive groovy shells (also known as REPL), based on
the '[groovysh](http://groovy.codehaus.org/Groovy+Shell)' command that ships with any [Groovy](http://groovy.codehaus.org/) version.

The main feature is an Application shell, meaning a shell where your application
classes (Java or other JVM language) can be imported, instantiated and run. This allows you to interact
directly with your database-layer, service layer, running application, etc. without having to
change a line of your code.

There is also build shell, which allows to connect to a gradle project using the gradle tooling API to run builds.

Another particular feature is a build development shell that has a variable ```project``` which represents
your gradle project, allowing you to introspect your project after it has been instantiated. This can be useful
for developing custom gradle plugins, or debugging large setups.


This plugin is **Work In Progress**, expect some rough edges, but please do report troubles you face.


## Prerequisites

* [Java](http://www.java.com/)
* [Gradle](http://www.gradle.org) (From gradle wrapper is fine)

- **NO GROOVY INSTALLATION REQUIRED** (gradle will fetch it like any other dependency)
- **NO CHANGE TO YOUR JAVA CODE REQUIRED**


## Installing the plugin

Include the plugin in your build.gradle file like this (using gradle > 2.1):

```Groovy
plugins {
  id 'java'
  id "com.github.tkruse.groovysh" version "1.0.9"
}

buildscript {
    repositories {
        jcenter()
    }
}

repositories {
    jcenter() // or maven central...
}
```
Also see https://plugins.gradle.org/plugin/com.github.tkruse.groovysh

For older gradle versions (or if you cannot use gradle plugin portal):

```Groovy
apply plugin: 'com.github.tkruse.groovysh'

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.tkruse.gradle:gradle-groovysh-plugin:1.0.7'
    }
}

repositories {
    jcenter() // or maven central...
}
```
(```mavenCentral()``` instead of ```jcenter()``` also works)

*Note*: With version 0.x.x of this plugin, the plugin id was just 'groovysh', so you would have
to write ```apply plugin: 'groovysh'```. This was changed in line with the gradle portal.

The plugin id has changed with version 1.0.0 due to the requirements of gradle-portal.

Currently your project needs to also have the java plugin applied for the ```shell``` task:

```Groovy
apply plugin: 'java'
```

## Configuring the Plugin

No configuration is required. To change the defaults, see [Configuration](doc/Configuration.md)

If you encounter groovy version mismatches, explicitly set your system version (until I find a clean way to fix this).
```Groovy
groovysh {
    // groovyVersion determines the features of the shell and buildShell tasks.
    groovyVersion = '2.4.4'
}
```


## Using the Plugin

Invoke either shell task with option *```-q```* (this means "quiet", else the common gradle output will get in the way).
If you have the gradle daemon configured, also add ```--no-daemon```

```bash
gradle -q shell
gradle -q buildShell
gradle -q buildDevShell
```
When using the gradle wrapper, that would be ```./gradlew``` instead.

You can find samples in the [samples subfolder](samples/README.md).

Note that before running the shell, your project will be build first (compileJava task), which may also mean
downloading its dependencies, so it can take a while for the shell to appear.


### Some things you can do with the ```shell```

- Instantiate a Spring Container (provided you add Spring dependencies to your project)
- Instantiate a Database connection, write business entities etc.
- Run your algorithms interactively

### Some things you can do with the ```buildShell```

```Groovy
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

#### Grovysh variable declarations

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

#### Gradle project lock

While a shell is running that was started from a project, gradle may lock the project,
and other tools may not work well during that time (e.g. IntelliJ gradle update).
Stopping the shell frees the lock again.

## TODOs

see [TODO](doc/TODO.md)

## Installing the plugin to modify it and contribute

See - [Dev setup](doc/Contributing.md)

### Getting a shell without installing this plugin:

See the docs at:
 - [Manual Build Shell README](doc/InstallBuildDevShellManually.md)
 - [Manual App Shell README](doc/InstallAppShellManually.md)
Or just install Groovy and run ```groovysh``` with a suitable classpath.

## Compatibility

* Java Versions (6?, 7, 8)

* Operating Systems (Ubuntu, Windows? MacOs?)

* Gradle versions (1.10, 1.11, 1.12, 2.x.x)

* Groovy Versions (2.x.x)

## FAQ

* Why do I have to add '-q' every time?

The gradle progress logging gets in the way of the output else, no API exists to remove that (see GRADLE-1147).

* My Unix prompt does not work after program exit, typed text remains invisible.

This is known to happen with jline1.12. Run ```reset``` in that shell as workaround.

* How does this compare to the Java 9 'JShell' REPL (Project Kulla)?

Both are REPLs, both can interact with java classes, so usage should be similar. There are no dependencies. Arguably typing Groovy code in a REPL allows for easier interaction.

* I get: `Cannot resolve external dependency jline:jline:2.11 because no repositories are defined.`

This means you need to define some plain repositories (not buildscript repositories):

```
repositories {
    jcenter() // or maven central...
}
```
