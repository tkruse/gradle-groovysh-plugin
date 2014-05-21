package com.tkruse.gradle.groovysh

import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.SourceSetContainer

class ApplicationShellTask extends ShellTask {

    static final String NAME = 'shell'
    static final String CONFIGURATION_NAME = 'appShellConf'

    @Override
    ShellTaskExtension getTaskExtension() {
        return project.groovysh.shell
    }

    ApplicationShellTask() {
        super()
        this.description = 'starts a groovysh shell with the classpath set as runtime configuration output'

        String sourceSetName = project.groovysh.shell.sourceSetName
        SourceSetContainer sourceSets = project.sourceSets

        String extendedRuntime = 'runtime'
        String classTaskDependency = 'classes'

        if (sourceSetName != 'main') {
            extendedRuntime = "${sourceSetName}Runtime"
            classTaskDependency = "${sourceSetName}Classes"
        }
        project.configurations.appShellConf { extendsFrom(project.configurations.findByName(extendedRuntime)) }

        //println("using sourceSet $sourceSetName")
        this.dependsOn(classTaskDependency)
        FileCollection appClasspath = sourceSets.getByName(sourceSetName).runtimeClasspath
        FileCollection shellClasspath = project.configurations.appShellConf.asFileTree
        this.classpath = appClasspath + shellClasspath
        FileCollection extraClasspath = project.groovysh.shell.extraClasspath
        if (extraClasspath != null) {
            this.classpath = this.classpath + extraClasspath
        }
    }

    @Override
    String getConfigurationName() {
        return CONFIGURATION_NAME
    }

    @Override
    void exec() {
        println('This is a gradle Application Shell.')
        println('You can import your application classes and act on them.')
        super.exec()
    }
}
