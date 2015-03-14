package com.tkruse.gradle.groovysh

import static com.tkruse.gradle.groovysh.DynamicInvokeHelper.getApplicationShellExtension

import org.gradle.api.artifacts.Configuration
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.SourceSetContainer

/**
 * A task that starts a groovy shell with the application runtime classpath configured
 */
class ApplicationShellTask extends AbstractShellTask {

    static final String NAME = 'shell'
    static final String CONFIGURATION_NAME_PREFIX = 'appShellConf_'

    String sourceSetName
    FileCollection extraClasspath

    ApplicationShellTask() {
        super()
        this.description = 'starts a groovysh shell with the classpath set as runtime configuration output'
        String defaultSourceSet = getTaskExtension().sourceSetName
        if (defaultSourceSet == null) {
            defaultSourceSet = 'main'
        }
        setSourceSetName(defaultSourceSet)
    }

    @Override
    ShellTaskExtension getTaskExtension() {
        return getApplicationShellExtension(project)
    }

    def setSourceSetName(String newVal) {
        assert newVal != null
        this.sourceSetName = newVal

        /*
         * Would be nicer to use a Gradle JavaPlugin API here... but conventions should be stable
         */

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
        FileCollection extraClasspath = getTaskExtension().extraClasspath
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
