package com.tkruse.gradle.groovysh

import org.gradle.api.Project
import org.junit.Test

/**
 * Tests task setup. Stronger execution tests are in samples subfolder.
 */
class BuildShellTaskTest {

    @Test
    void testConfigureMain() {
        Project project = TestHelper.createProjectWithPluginAndJava()
        project.dependencies.add('compile', 'org.slf4j:slf4j-api:1.7.12')
        project.dependencies.add('testCompile', 'junit:junit-dep:4.11')
        TestHelper.setupTasks(project)
        BuildShellTask buildShellTask = (BuildShellTask) project.tasks.findByName(BuildShellTask.NAME)
        buildShellTask.addGroovyDependencies()
        buildShellTask.addGradleDependencies()
        buildShellTask.setupClasspath()
        List<String> dependencyVersions =
                project.configurations.buildShellConf_buildShell
                        .dependencies.asList().collect { it.name + it.version }
        assert !buildShellTask.dependsOn.contains('classes')

        assert buildShellTask.classpath.asPath.contains('groovy')
        assert buildShellTask.classpath.asPath.contains('slf4j')
        assert !buildShellTask.classpath.asPath.contains('junit')
        assert dependencyVersions.contains('gradle-tooling-api2.6')
    }

    @Test
    void testGroovyVersions() {
        for (String version in ['2.2.1', '2.2.2', '2.3.0', '2.3.9', '2.4.4']) {
            for (String gradleVersion in ['1.12', '2.0', '2.1', '2.2.1', '2.3', '2.4', '2.5', '2.6']) {
                Project project = TestHelper.createProjectWithPluginAndJava()
                project.dependencies.add('testCompile', 'junit:junit-dep:4.11')

                project.groovysh.groovyVersion = version
                project.groovysh.buildShell.gradleVersion = gradleVersion
                TestHelper.setupTasks(project)
                BuildShellTask buildShellTask = (BuildShellTask) project.tasks.findByName(BuildShellTask.NAME)
                buildShellTask.addGroovyDependencies()
                buildShellTask.addGradleDependencies()
                List<String> dependencyVersions =
                        project.configurations.buildShellConf_buildShell
                                .dependencies.asList().collect { it.name + it.version }
                assert dependencyVersions.contains('jline2.11')
                assert dependencyVersions.contains('commons-cli1.2')
                assert dependencyVersions.contains('groovy-all' + version)
                assert dependencyVersions.contains('gradle-tooling-api' + gradleVersion)
            }
        }
    }

    @Test
    void testConfigureMainWithGradleVersion() {
        Project project = TestHelper.createProjectWithPluginAndJava()

        project.groovysh.buildShell.gradleVersion = '2.6'

        TestHelper.setupTasks(project)
        BuildShellTask buildShellTask = (BuildShellTask) project.tasks.findByName(BuildShellTask.NAME)
        buildShellTask.addGroovyDependencies()
        buildShellTask.addGradleDependencies()
        buildShellTask.setupClasspath()
        assert buildShellTask != null
        List<String> dependencyVersions =
                project.configurations.buildShellConf_buildShell.dependencies.asList().collect { it.name + it.version }
        assert dependencyVersions.contains('jline2.11')
        assert dependencyVersions.contains('commons-cli1.2')
        assert dependencyVersions.contains('groovy-all2.4.4')
        assert dependencyVersions.contains('gradle-tooling-api2.6')

    }
}
