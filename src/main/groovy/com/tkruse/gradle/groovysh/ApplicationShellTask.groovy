package com.tkruse.gradle.groovysh

import org.codehaus.groovy.tools.shell.Groovysh
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class ApplicationShellTask
    extends DefaultTask
{

    static final String NAME = 'shell';
    static final String CONFIGURATION_NAME = 'buildShellConf';
    private final static String DAEMON_PROP = 'org.gradle.daemon';

    public ApplicationShellTask()
    {
        this.group = 'help';
        this.description = 'starts a groovysh shell with the classpath set as runtime configuration output';
        this.outputs.upToDateWhen { false }
    }

    @TaskAction
    void exec()
    {
        GroovyshPlugin.checkDeamon(project)
        GroovyshPlugin.checkQuiet(project)

        println("$NAME: This is a gradle Application Shell. The classes of your application can be imported.")

        Groovysh shell = new org.codehaus.groovy.tools.shell.Groovysh()
        shell.run()
    }
}
