package com.tkruse.gradle.groovysh

import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.SourceSetContainer

class ApplicationShellTask extends ShellTask {

    static final String NAME = 'shell'
    static final String CONFIGURATION_NAME_PREFIX = 'appShellConf_'

    String sourceSetName
    FileCollection extraClasspath

    ApplicationShellTask() {
        super()
        this.description = 'starts a groovysh shell with the classpath set as runtime configuration output'
        String defaultSourceSet = project.groovysh.shell.sourceSetName
        if (defaultSourceSet == null) {
            defaultSourceSet = 'main'
        }
        setSourceSetName(defaultSourceSet)
    }

    @Override
    ShellTaskExtension getTaskExtension() {
        return project.groovysh.shell
    }

    def setSourceSetName(String newVal) {
        assert newVal != null
        this.sourceSetName = newVal

        SourceSetContainer sourceSets = project.sourceSets

        String classTaskDependency = 'classes'

        if (sourceSetName != 'main') {
            classTaskDependency = "${sourceSetName}Classes"
        }

        //println("using sourceSet $sourceSetName")
        this.dependsOn(classTaskDependency)

        String extendedRuntime = 'runtime'
        if (sourceSetName != 'main') {
            extendedRuntime = "${sourceSetName}Runtime"
        }
        project.configurations.getByName(getConfigurationName())
                { Configuration config -> config.extendsFrom(project.configurations.findByName(extendedRuntime)) }

        FileCollection appClasspath = sourceSets.getByName(sourceSetName).runtimeClasspath
        FileCollection shellClasspath = project.configurations.getByName(getConfigurationName()).asFileTree
        this.classpath = appClasspath + shellClasspath
        FileCollection extraClasspath = project.groovysh.shell.extraClasspath
        if (extraClasspath != null) {
            this.classpath = this.classpath + extraClasspath
        }
    }

    @Override
    String getConfigurationName() {
        return CONFIGURATION_NAME_PREFIX + getName()
    }

    @Override
    void exec() {

        println('This is a gradle Application Shell.')
        println('You can import your application classes and act on them.')
        super.exec()
    }
}
