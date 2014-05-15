package com.tkruse.gradle.groovysh

import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.api.tasks.JavaExec


class ApplicationShellTask extends JavaExec {

    static final String NAME = 'shell'
    static final String CONFIGURATION_NAME = 'appShellConf'

    ApplicationShellTask() {
        this.group = 'help'
        this.description = 'starts a groovysh shell with the classpath set as runtime configuration output'
        this.outputs.upToDateWhen { false }

        String sourceSetName = project.groovysh.shell.sourceSetName
        //        if (!project.hasProperty('sourceSets')) {
        //            throw new IllegalStateException(
        //            "$NAME: appShell can only be used with Projects having sourceSets (use apply plugin: 'java')")
        //        }
        SourceSetContainer sourceSets = project.sourceSets

        String extendedRuntime = 'runtime'
        String classTaskDependency = 'classes'

        if (sourceSetName != 'main') {
            extendedRuntime = "${sourceSetName}Runtime"
            classTaskDependency = "${sourceSetName}Classes"
        }
        // use an independent configuration for the task dependencies, so that application compile is not influenced
        project.configurations.create(ApplicationShellTask.CONFIGURATION_NAME)
        project.configurations.appShellConf { extendsFrom(project.configurations.findByName(extendedRuntime)) }

        TaskHelper.addGroovyDependencies(project, project.groovysh.shell.groovyVersion)

        //println("using sourceSet $sourceSetName")
        this.dependsOn(classTaskDependency)
        FileCollection appClasspath = sourceSets.getByName(sourceSetName).runtimeClasspath
        FileCollection shellClasspath = project.configurations.appShellConf.asFileTree
        this.classpath = appClasspath + shellClasspath
        FileCollection extraClasspath = project.groovysh.shell.extraClasspath
        if (extraClasspath != null) {
            this.classpath = this.classpath + extraClasspath
        }

        //this.main = 'com.tkruse.gradle.groovysh.ShellMain'
        this.main = 'org.codehaus.groovy.tools.shell.Main'
        List<String> jvmArgs = project.groovysh.shell.jvmArgs
        if (jvmArgs != null && jvmArgs.size() > 0) {
            this.jvmArgs = jvmArgs
        }
        List<String> args = project.groovysh.shell.args
        if (args != null && args.size() > 0) {
            this.args = args
        }
        File workingDir = project.groovysh.shell.workingDir
        if (workingDir != null) {
            this.workingDir = workingDir
        }

        FileCollection bootstrapClasspath = project.groovysh.shell.bootstrapClasspath
        if (bootstrapClasspath != null) {
            this.bootstrapClasspath = bootstrapClasspath
        }

        this.enableAssertions = project.groovysh.shell.enableAssertions

        Map<String, Object> environment = project.groovysh.shell.environment
        if (environment != null) {
            this.environment = environment
        }
        OutputStream errorOutput = project.groovysh.shell.errorOutput
        if (errorOutput != null) {
            this.errorOutput = errorOutput
        }
        String maxHeapSize = project.groovysh.shell.maxHeapSize
        if (maxHeapSize != null) {
            this.maxHeapSize = maxHeapSize
        }
        InputStream standardInput = project.groovysh.shell.standardInput
        if (standardInput != null) {
            this.standardInput = standardInput
        }
        OutputStream standardOutput = project.groovysh.shell.standardOutput
        if (standardOutput != null) {
            this.standardOutput = standardOutput
        }
        Map<String, Object> systemProperties = project.groovysh.shell.systemProperties
        if (systemProperties != null) {
            this.systemProperties = systemProperties
        }
    }

    @Override
    void exec() {
        TaskHelper.checkDaemon(project, NAME)
        TaskHelper.checkQuiet(project, NAME)
        TaskHelper.checkParallel(project, NAME)

        println("$GroovyshPlugin.NAME: This is a gradle Application Shell.")
        println("$GroovyshPlugin.NAME: You can import your application classes and act on them.")
        super.exec()
    }
}
