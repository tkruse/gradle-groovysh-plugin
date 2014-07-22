package com.tkruse.gradle.groovysh

import org.gradle.api.Project
import org.gradle.api.invocation.Gradle
import org.junit.Test

/**
 * Tests task setup. Stronger execution tests are in samples subfolder.
 */
class BuildDevShellTaskTest {

    @Test
    void testConfigureMain() {
        Project project = TestHelper.createProjectWithPlugin()
        TestHelper.setupTasks(project)
        BuildDevShellTask buildDevShellTask = (BuildDevShellTask) project.tasks.findByName(BuildDevShellTask.NAME)
        assert buildDevShellTask != null
        checkDependencies(Gradle.gradleVersion, project)
    }

    private static void checkDependencies(String gradleVersion, Project project) {
        List<String> dependencyVersions =
                project.configurations.buildDevShellConf
                        .dependencies.asList().collect { it.name + it.version }
        if (gradleVersion.startsWith('1')) {
            assert dependencyVersions.contains('jline1.0')
        } else {
            assert dependencyVersions.contains('jline2.11')
        }
    }

    @Test
    void testConfigureMainWithGradleVersion() {
        Project project = TestHelper.createProjectWithPlugin()

        project.groovysh.buildShell.gradleVersion = '2.0'

        TestHelper.setupTasks(project)
        BuildDevShellTask buildDevShellTask = (BuildDevShellTask) project.tasks.findByName(BuildDevShellTask.NAME)
        assert buildDevShellTask != null
        checkDependencies(Gradle.gradleVersion, project)
    }
}
