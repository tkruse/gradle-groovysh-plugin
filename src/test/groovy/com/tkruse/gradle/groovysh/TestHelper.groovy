package com.tkruse.gradle.groovysh

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

class TestHelper {
    static Project createProjectWithPlugin() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: 'java'
        project.apply plugin: GroovyshPlugin.NAME
        project
    }

    static void setupTasks(final Project project) {
        Plugin plugin = project.plugins.getPlugin(GroovyshPlugin.NAME)
        assert plugin instanceof GroovyshPlugin
        // simulate AfterEvaluate
        ((GroovyshPlugin) plugin).setupTasks(project)
    }
}
