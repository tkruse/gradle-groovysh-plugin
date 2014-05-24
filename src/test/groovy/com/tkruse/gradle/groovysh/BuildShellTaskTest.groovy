package com.tkruse.gradle.groovysh

import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.junit.Test

class BuildShellTaskTest {

    @Test
    void testConfigureMain() {
        Project project = TestHelper.createProjectWithPlugin()
        project.dependencies.add('compile', 'org.slf4j:slf4j-api:1.7.7')
        project.dependencies.add('testCompile', 'junit:junit-dep:4.11')
        TestHelper.setupTasks(project)
        project.tasks.findByName(BuildShellTask.NAME).addGroovyDependencies()
        JavaExec buildShellTask = (JavaExec) project.tasks.findByName(BuildShellTask.NAME)
        List<String> dependencyVersions =
                project.configurations.buildShellConf_buildShell
                        .dependencies.asList().collect { it.name + it.version }
        assert buildShellTask != null
        assert !buildShellTask.dependsOn.contains('classes')
        assert buildShellTask.classpath.asPath.contains('groovy')
        assert buildShellTask.classpath.asPath.contains('slf4j')
        assert !buildShellTask.classpath.asPath.contains('junit')
        assert dependencyVersions.contains('gradle-tooling-api1.12')
    }

    @Test
    void testGroovyVersions() {
        for (String version in ['2.2.1', '2.2.2', '2.3.0', '2.3.1']) {
            for (String gradleVersion in ['1.9', '1.10', '1.11', '1.12']) {
                Project project = TestHelper.createProjectWithPlugin()
                project.dependencies.add('testCompile', 'junit:junit-dep:4.11')

                project.groovysh.groovyVersion = version
                project.groovysh.buildShell.gradleVersion = gradleVersion
                TestHelper.setupTasks(project)
                project.tasks.findByName(BuildShellTask.NAME).addGroovyDependencies()
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
        Project project = TestHelper.createProjectWithPlugin()

        project.groovysh.buildShell.gradleVersion = '1.11'

        TestHelper.setupTasks(project)
        project.tasks.findByName(BuildShellTask.NAME).addGroovyDependencies()
        JavaExec buildShellTask = (JavaExec) project.tasks.findByName(BuildShellTask.NAME)

        assert buildShellTask != null
        List<String> dependencyVersions =
                project.configurations.buildShellConf_buildShell.dependencies.asList().collect { it.name + it.version }
        assert dependencyVersions.contains('jline2.11')
        assert dependencyVersions.contains('commons-cli1.2')
        assert dependencyVersions.contains('groovy-all2.3.1')
        assert dependencyVersions.contains('gradle-tooling-api1.11')

    }
}
