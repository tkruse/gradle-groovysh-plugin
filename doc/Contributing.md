# Contributing to the gradle groovysh plugin

All contributions are welcome: ideas, bug reports, patches.

## Setting up a development environment of a project of your own

If you play around with groovysh in an existing project, you can try out ideas by using groovysh from
the buildSrc folder.

Clone this repository as ```buildSrc``` subfolder of a gradle root project.

Bring some patience for the unit tests and checks, or disable them by uncommenting the section in ```build.gradle```.

### Using the sample subfolder

As an alternative you can add a project to the samples subfolder. Just copy & paste the simple folder, and add a line
in ```samples/settings.gradle```. You need to run ```gradle jar``` in the ```gradle-groovysh-plugin``` folder after each
 change though. 

This would be a great way to report errors, if you can create a pull request adding a project or modiying
an existing one,
