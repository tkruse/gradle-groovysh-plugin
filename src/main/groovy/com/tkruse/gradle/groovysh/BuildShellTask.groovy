package com.tkruse.gradle.groovysh

import org.gradle.api.artifacts.Dependency
import org.gradle.api.file.FileCollection

/**
 * A task that starts a groovysh shell with the gradle tooling api onthe classpath
 */
class BuildShellTask extends ShellTask {

    static final String NAME = 'buildShell'
    static final String CONFIGURATION_NAME_PREFIX = 'buildShellConf_'
    static final List<String> FIXEDARGS = ['-e', '''\
import org.gradle.tooling.*
import org.gradle.tooling.model.*

connector = GradleConnector.newConnector()
''']

    String gradleVersion = '1.12'

    @Override
    ShellTaskExtension getTaskExtension() {
        return project.groovysh.buildShell
    }

    BuildShellTask() {
        super()
        this.description = 'starts a groovysh shell with the with a gradleConnector connected to the Project'
    }

    @Override
    String getConfigurationName() {
        return CONFIGURATION_NAME_PREFIX + getName()
    }

    void addGradleDependencies() {
        List<Dependency> deps = project.configurations.getByName(getConfigurationName()).allDependencies
                .collect { Dependency it -> it }
        TaskHelper.addIfMissing(project, getConfigurationName(),
                deps, 'org.gradle', 'gradle-tooling-api', taskExtension.gradleVersion)
        TaskHelper.addIfMissing(project, getConfigurationName(), deps, 'org.slf4j', 'slf4j-simple', '1.7.7')
    }

    void setupClasspath() {
        FileCollection shellClasspath = project.configurations.getByName(getConfigurationName()).asFileTree
        this.classpath = shellClasspath

        FileCollection extraClasspath = project.groovysh.shell.extraClasspath
        if (extraClasspath != null) {
            this.classpath = this.classpath + extraClasspath
        }
    }

    @Override
    void exec() {
        addGradleDependencies()
        setupClasspath()
        this.args = FIXEDARGS + this.args

        println('''\
This is a gradle Build Shell.
It has a connector variable prepared for you. Connect to projects like this:
connector.forProjectDirectory(new File("."))
connection = connector.connect()
project = connection.getModel(GradleProject)
launcher = connection.newBuild()
''')
        super.exec()
    }

}
