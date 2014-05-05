package com.tkruse.gradle.groovysh

import org.codehaus.groovy.tools.shell.Groovysh
import org.codehaus.groovy.tools.shell.Main
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.TaskAction

class ApplicationShellTask
    extends JavaExec
{

    static final String NAME = 'shell';
    static final String CONFIGURATION_NAME = 'appShellConf';
    private final static String DAEMON_PROP = 'org.gradle.daemon';

    public ApplicationShellTask()
    {
        this.group = 'help';
        this.description = 'starts a groovysh shell with the classpath set as runtime configuration output';
        this.outputs.upToDateWhen { false }
//        if (!project.hasProperty('sourceSets')) {
//            throw new IllegalStateException("$NAME: appShell can only be used with Projects having sourceSets (use apply plugin: 'java')")
//        }
        this.dependsOn('classes')
        this.classpath = project.sourceSets.main.runtimeClasspath

        this.standardInput = System.in
        //this.main = 'com.tkruse.gradle.groovysh.ShellMain'
        this.main = 'org.codehaus.groovy.tools.shell.Main'
        // this.jvmArgs =
        // this.args =
        // this.workingDir =
    }

    @Override
    void exec() {
        println("$NAME: This is a gradle Application Shell.")
        println("$NAME: You can import your application classes and act on them.")
        super.exec()
    }
}
