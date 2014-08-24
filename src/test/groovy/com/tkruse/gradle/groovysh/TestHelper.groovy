package com.tkruse.gradle.groovysh

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

/**
 * Helper for tests
 */
class TestHelper {

    static Project createProjectWithPlugin() {
        Project project = ProjectBuilder.builder().build()
        project.apply(plugin:'java')
        project.apply(plugin:GroovyshPlugin.PLUGIN_ID)
        return project
    }

    static void setupTasks(final Project project) {
        Plugin plugin = project.plugins.getPlugin(GroovyshPlugin.PLUGIN_ID)
        assert plugin instanceof GroovyshPlugin
        // simulate AfterEvaluate
        ((GroovyshPlugin) plugin).setupTasks(project)
    }
}
