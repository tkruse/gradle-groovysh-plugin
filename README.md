Gradle plugin providing a Groovy Shell (groovysh)
=================================================

This plugin provides gradle tasks that start an interactive groovy shells, based on
the 'groovysh' command that ships with any Groovy version.

The main particular feature is an Application shell, meaning a shell where your application
classes (Java or other JVM language) can be imported, instantiated and run. This allows you to interact
directly with your database-layer, service layer, running application, etc. without having to
change a line of your code.

The other particular feature is that a build shell has a variable 'project' which represents
your gradle project, allowing you to introspect your project after it has been instantiated.



This plugin is WORK IN PROGRESS, expect some rough edges.


Using the Plugin
----------------

In your buildgradle, apply the plugin:

    apply plugin: 'groovysh'

Invoke either shell task with option -q.
If you have the gradle daemon configured, also add --no-daemon

    $ gradle -q shell
    $ gradle -q buildshell

Things you can do with the shell:

- Instantiate a Spring Container (provided you add Spring dependencies to your project)
- Instantiate a Database connection, write business entities etc.
- Run your algorithms interactively

Things you can do with the buildshell:

    project.tasks
    project.configurations
    project.ext.properties.each {println it}
    project.repositories.each {println it.name}

    project.apply(plugin: 'java')
    project.tasks.compileJava.execute() // only executes once
    project.tasks.clean.execute() // only executes once


Caveats
-------

Notice that the groovy version for the build shell is the same as for gradle (1.8.6), whereas for the application
shell a much more recent version of groovy (2.3.x) can be used. As a consequence the application shell is much prettier,
and the allowed syntax is different.

Also if you are new to groovysh, the number one quirk to know is that you must not declare variables, e.g.:

    groovy:000> def x = 3
    ===> 3
    groovy:000> x
    Unknown property: x
    groovy:000> x = 3
    ===> 3
    groovy:000> x
    ===> 3



Features
--------

- Run a shell where project variable is the same as in build.gradle file
- Run a shell with application classes (Prototype)
- AppShell: Configure SourceSet
- Disable Tasks (become hidden)

Wishlist / Brainstorming
------------------------

- Cleaner handling of configurations (extend runtime, testRuntime)
- Configure ApplicationShell Task JavaExec params
- Configure ApplicationShell Task Groovy Version
- Configure ApplicationShell Broken Ansi Keyboard (may require Groovy 2.3.2)
- Define ApplicationShell initial commands, import (may require Groovy 2.3.2)
- Better checking whether Daemon or Parallel mode is on
- Tutorials / Examples
    - Spring Container
    - mongodb
    - hibernate
    - running unit tests without creating fixture again?
    - MBeans
    - REST call HTML / JSON processing
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



Installing the plugin
---------------------

For now, clone this repository as buildSrc subfolder of your gradle root project.
Alternatively, you can clone it into the buildSrc subfolder and add this buildgradle in the buildSrc folder:

    apply plugin: "java"
    dependencies {
        runtime subprojects.collect { owner.project(it.path) }
    }

and a settings.gradle:

    include 'gradle-groovysh-plugin'

A better way will be provided as this plugin matures.


Getting a shell without depending on this plugin:
-------------------------------------------------

See the docs at:
 - [manual Build Shell README](doc/InstallBuildShellManually.md)
 - [manual App Shell README](doc/InstallAppShellManually.md)


Building the Plugin
-------------------

To build the plugin, just type the following commmand:

    ./gradlew clean build
