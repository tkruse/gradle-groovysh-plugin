package com.tkruse.gradle.groovysh

import org.codehaus.groovy.tools.shell.Groovysh
import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

class BuildShellTask
    extends DefaultTask
{

    static final String NAME = 'buildShell';


    public BuildShellTask()
    {
        this.group = 'help';
        this.description = 'starts a groovysh shell to introspect the gradle setup itself';
        this.outputs.upToDateWhen { false }
    }

    @TaskAction
    void exec()
    {
        GroovyshPlugin.checkDeamon(project)
        GroovyshPlugin.checkQuiet(project)

        println("$NAME: This is a gradle Build Shell. The variable 'project' is bound to your root project.")
        println("$NAME: Run e.g. 'project.ext.properties' to inspect your project.")

        Groovysh shell = new org.codehaus.groovy.tools.shell.Groovysh()
        // this hacks into Groovysh internal API and may break in the future.
        shell.interp.context.variables.put("project", project)
        shell.run()
    }
}
