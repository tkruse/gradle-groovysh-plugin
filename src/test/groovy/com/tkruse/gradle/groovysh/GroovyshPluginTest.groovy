package com.tkruse.gradle.groovysh

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class GroovyshPluginTest {

    @Test
    void testApplyNoJava() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'groovysh'
        project.extensions.groovysh.enableAppShell = false
        assert project.tasks.buildShell instanceof BuildShellTask
    }

    @Test
    void testApplyWithJava() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'java'
        project.apply plugin: 'groovysh'
        assert project.tasks.shell instanceof ApplicationShellTask
    }
}
