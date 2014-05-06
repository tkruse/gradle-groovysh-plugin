package com.tkruse.gradle.groovysh

import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.gradle.testfixtures.ProjectBuilder

class ApplicationShellTaskTest extends GroovyTestCase {
    void testExecTest() {
        Project project = GroovyshPluginTest.createProjectWithPlugin()
        project.groovysh.shell.sourceSetName = 'test'
        GroovyshPluginTest.setupTasks(project)
        JavaExec shellTask = project.tasks.findByName('shell')
        assert shellTask != null
        assert shellTask.dependsOn.contains('testClasses')
    }

    void testExecMain() {
        Project project = GroovyshPluginTest.createProjectWithPlugin()
        GroovyshPluginTest.setupTasks(project)
        JavaExec shellTask = project.tasks.findByName('shell')
        assert shellTask != null
        assert shellTask.dependsOn.contains('classes')
    }
}
