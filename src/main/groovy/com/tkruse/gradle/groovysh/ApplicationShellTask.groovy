package com.tkruse.gradle.groovysh

import org.gradle.api.internal.tasks.DefaultSourceSetContainer
import org.gradle.api.tasks.JavaExec


class ApplicationShellTask
    extends JavaExec
{

    static final String NAME = 'shell';

    public ApplicationShellTask()
    {
        this.group = 'help';
        this.description = 'starts a groovysh shell with the classpath set as runtime configuration output';
        this.outputs.upToDateWhen { false }

        String sourceSetName = project.groovysh.shell.sourceSetName
        //        if (!project.hasProperty('sourceSets')) {
        //            throw new IllegalStateException("$NAME: appShell can only be used with Projects having sourceSets (use apply plugin: 'java')")
        //        }
        DefaultSourceSetContainer sourceSets = project.sourceSets
        println("using sourceSet $sourceSetName")
        String classTaskDependency = 'classes';
        if (sourceSetName != 'main') {
            classTaskDependency = "${sourceSetName}Classes"
        }
        this.dependsOn(classTaskDependency)
        this.classpath = sourceSets.getByName(sourceSetName).runtimeClasspath
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
