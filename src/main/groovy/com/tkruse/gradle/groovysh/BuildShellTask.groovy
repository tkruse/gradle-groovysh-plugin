package com.tkruse.gradle.groovysh

import org.gradle.api.file.FileCollection

class BuildShellTask extends ShellTask {

    static final String NAME = 'buildShell'
    static final String CONFIGURATION_NAME_PREFIX = 'buildShellConf_'

    String gradleVersion = '1.12'

    @Override
    ShellTaskExtension getTaskExtension() {
        return project.groovysh.buildShell
    }

    BuildShellTask() {
        super()
        this.description = 'starts a groovysh shell with the with a gradleConnector connected to the Project'

        project.dependencies.add(getConfigurationName(), 'org.gradle:gradle-tooling-api:' + taskExtension.gradleVersion)

        FileCollection shellClasspath = project.configurations.getByName(getConfigurationName()).asFileTree
        this.classpath = shellClasspath

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
        println('This is a gradle Build Shell.')
        println('It has a connection to your projct that you can use to trigger tasks.')
        super.exec()
    }
}
