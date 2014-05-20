# Example for introspecting the gradle build process

Session:

```Groovy
$ gradle -q clean buildDevShell
groovysh: This is a gradle Build-Shell. The variable 'project' is bound to your project.
groovysh: Run e.g. 'project.ext.properties' to inspect your project.
Groovy Shell (1.8.6, JVM: 1.7.0_55)
Type 'help' or '\h' for help.
-----------------------------------------------------------------------
groovy:000> project.tasks.copyTask2.getInputs().getFiles().collect{it.name}
===> []
groovy:000> project.tasks.copyTask.getInputs().getFiles().collect{it.name}
===> [takeMe.md, notMe-staging.txt, takeme.txt]
groovy:000> project.tasks.copyTask.execute()
===> null
groovy:000> project.tasks.copyTask.getInputs().getFiles().collect{it.name}
===> [takeMe.md, notMe-staging.txt, takeme.txt]
```
