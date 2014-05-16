package com.tkruse.gradle.groovysh

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Test

class GroovyshPluginTest {

    @Test
    void testApplyNoJava() {
        Project project = ProjectBuilder.builder().build()
        project.apply plugin: GroovyshPlugin.NAME
        Plugin plugin = project.plugins.getPlugin(GroovyshPlugin.NAME)
        assert plugin instanceof GroovyshPlugin
        assert project.tasks.findByName(BuildDevShellTask.NAME) == null
        assert project.tasks.findByName(ApplicationShellTask.NAME) == null

        project.groovysh.enableBuildDevShell = true
        project.groovysh.enableAppShell = true
        // simulate AfterEvaluate
        ((GroovyshPlugin) plugin).setupTasks(project)

        assert project.tasks.findByName(BuildDevShellTask.NAME) != null
        assert project.tasks.findByName(ApplicationShellTask.NAME) == null
        assert project.tasks.buildDevShell instanceof BuildDevShellTask
        //assert project.tasks.shell instanceof ApplicationShellTask
    }

    @Test
    void testApplyJava() {
        Project project = TestHelper.createProjectWithPlugin()
        TestHelper.setupTasks(project)
        assert project.tasks.findByName(BuildDevShellTask.NAME) != null
        assert project.tasks.findByName(ApplicationShellTask.NAME) != null
        assert project.tasks.buildDevShell instanceof BuildDevShellTask
        assert project.tasks.shell instanceof ApplicationShellTask

        assert project.configurations.appShellConf != null
        assert project.configurations.buildDevShellConf != null
    }


}
