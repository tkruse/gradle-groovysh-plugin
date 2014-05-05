Gradle plugin providing a Groovy Shell (groovysh)
=================================================

This plugin is WORK IN PROGRESS.

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

Using the Plugin
----------------

In your buildgradle, apply the plugin:

    apply plugin: 'groovysh'

Invoke the buildshell task with option -q. If you have the gradle daemon configured, also add --no-daemon

    $ gradle -q buildshell

Building the Plugin
-------------------

To build the plugin, just type the following commmand:

    ./gradlew clean build

Features
--------

- Run a shell where project variable is the same as in build.gradle file

Wishlist
--------
- Run a shell with application classes
