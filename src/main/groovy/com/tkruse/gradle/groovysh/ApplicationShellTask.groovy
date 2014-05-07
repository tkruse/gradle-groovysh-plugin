package com.tkruse.gradle.groovysh

import org.gradle.api.file.FileCollection;
import org.gradle.api.internal.tasks.DefaultSourceSetContainer;
import org.gradle.api.tasks.JavaExec;


class ApplicationShellTask
    extends JavaExec
{

    static final String NAME = 'shell';
    static final String CONFIGURATION_NAME = 'appShellConf';

    public ApplicationShellTask()
    {
        this.group = 'help';
        this.description = 'starts a groovysh shell with the classpath set as runtime configuration output';
        this.outputs.upToDateWhen { false }

        String sourceSetName = project.groovysh.shell.sourceSetName;
        //        if (!project.hasProperty('sourceSets')) {
        //            throw new IllegalStateException("$NAME: appShell can only be used with Projects having sourceSets (use apply plugin: 'java')")
        //        }
        DefaultSourceSetContainer sourceSets = project.sourceSets;

        String extendedRuntime = 'runtime';
        if (sourceSetName != 'main') {
            extendedRuntime = "${sourceSetName}Runtime";
        }
        // use an independent configuration for the task dependencies, so that application compile is not influenced
        project.configurations.create(ApplicationShellTask.CONFIGURATION_NAME);
        project.configurations.appShellConf({extendsFrom(project.configurations.findByName(extendedRuntime))});

        // TODO: Configure groovy version / consider indy?
        project.dependencies.add(ApplicationShellTask.CONFIGURATION_NAME, 'org.codehaus.groovy:groovy-all:2.2.2');
        project.dependencies.add(ApplicationShellTask.CONFIGURATION_NAME, 'commons-cli:commons-cli:1.2');
        project.dependencies.add(ApplicationShellTask.CONFIGURATION_NAME, 'jline:jline:2.11');

        //println("using sourceSet $sourceSetName")
        String classTaskDependency = 'classes';
        if (sourceSetName != 'main') {
            classTaskDependency = "${sourceSetName}Classes";
        }
        this.dependsOn(classTaskDependency);
        FileCollection appClasspath = sourceSets.getByName(sourceSetName).runtimeClasspath;
        FileCollection shellClasspath = project.configurations.appShellConf.getAsFileTree();
        this.classpath = appClasspath + shellClasspath;

        this.standardInput = System.in;
        //this.main = 'com.tkruse.gradle.groovysh.ShellMain'
        this.main = 'org.codehaus.groovy.tools.shell.Main';
        // this.jvmArgs =
        // this.args =
        // this.workingDir =
    }

    @Override
    void exec() {
        println("$GroovyshPlugin.NAME: This is a gradle Application Shell.");
        println("$GroovyshPlugin.NAME: You can import your application classes and act on them.");
        super.exec();
    }
}
