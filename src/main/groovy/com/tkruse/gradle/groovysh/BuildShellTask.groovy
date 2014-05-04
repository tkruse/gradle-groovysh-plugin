package com.tkruse.gradle.groovysh

import org.gradle.api.DefaultTask
import org.gradle.api.Task
import org.gradle.api.GradleException
import org.gradle.api.tasks.TaskAction

class BuildShellTask
    extends DefaultTask
{

    static final String NAME = 'buildShell';
    static final String CONFIGURATION_NAME = 'buildShellConf';
    private final static String DAEMON_PROP = 'org.gradle.daemon';

    public BuildShellTask()
    {
        this.group = 'help';
        this.description = 'starts a groovysh shell to introspect the gradle setup itself';
        this.outputs.upToDateWhen { false }
    }

    @TaskAction
    void exec()
    {
        println('This is a gradle Build Shell. The variable "project" is bound to your root project.')
        println('Run e.g. "project.ext.properties" to inspect your project.')

        if (project.hasProperty(DAEMON_PROP) && project.property(DAEMON_PROP) == 'true') {
            throw new IllegalStateException('Do not run $NAME with gradle daemon (use --no-daemon).')
        }

        def shell = new org.codehaus.groovy.tools.shell.Groovysh()
        shell.interp.context.variables.put("project", project)
        shell.run()
    }
}
