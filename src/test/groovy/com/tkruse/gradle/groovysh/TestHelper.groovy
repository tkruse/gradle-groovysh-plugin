package com.tkruse.gradle.groovysh

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder

/**
 * Helper for tests
 */
class TestHelper {

    static Project createProjectWithPluginAndJava() {
        Project project = createProjectWithPlugin()
        project.apply(plugin:'java')
        return project
    }

    static Project createProjectWithPlugin() {
        Project project = ProjectBuilder.builder().build()
        project.apply(plugin:GroovyshPlugin.PLUGIN_ID)
        // not good for people behind proxies, do not put this in plugin class
        project.repositories.jcenter()

        return project
    }

    static void setupTasks(final Project project) {
        Plugin plugin = project.plugins.getPlugin(GroovyshPlugin.PLUGIN_ID)
        assert plugin.class == GroovyshPlugin
        // simulate AfterEvaluate
        ((GroovyshPlugin) plugin).setupTasks(project)
    }
}
