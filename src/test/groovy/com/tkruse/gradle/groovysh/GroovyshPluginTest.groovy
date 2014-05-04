package com.tkruse.gradle.groovysh

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class GroovyshPluginTest {

    @Test
    void testApply() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'groovysh'

        assert project.tasks.buildShell instanceof BuildShellTask
    }
}
