package com.tkruse.gradle.groovysh

import org.gradle.api.Project
import org.gradle.api.tasks.JavaExec
import org.junit.Test

class ApplicationShellTaskTest {

    @Test
    void testConfigureTest() {
        Project project = TestHelper.createProjectWithPlugin()
        project.dependencies.add('compile', 'org.slf4j:slf4j-api:1.7.7')
        project.dependencies.add('testCompile', 'junit:junit-dep:4.11')
        project.groovysh.shell.sourceSetName = 'test'
        TestHelper.setupTasks(project)
        JavaExec shellTask = (JavaExec) project.tasks.findByName(ApplicationShellTask.NAME)
        assert shellTask != null
        assert shellTask.dependsOn.contains('testClasses')
        assert shellTask.classpath.asPath.contains('groovy')
        assert shellTask.classpath.asPath.contains('slf4j')
        assert shellTask.classpath.asPath.contains('junit')
    }

    @Test
    void testConfigureMain() {
        Project project = TestHelper.createProjectWithPlugin()
        project.dependencies.add('compile', 'org.slf4j:slf4j-api:1.7.7')
        project.dependencies.add('testCompile', 'junit:junit-dep:4.11')
        TestHelper.setupTasks(project)
        JavaExec shellTask = (JavaExec) project.tasks.findByName(ApplicationShellTask.NAME)
        assert shellTask != null
        assert shellTask.dependsOn.contains('classes')
        assert shellTask.classpath.asPath.contains('groovy')
        assert shellTask.classpath.asPath.contains('slf4j')
        assert !shellTask.classpath.asPath.contains('junit')
    }

    @Test
    void testGroovyVersions() {
        for (version in ['2.2.1', '2.2.2', '2.3.0']) {
            Project project = TestHelper.createProjectWithPlugin()
            project.dependencies.add('testCompile', 'junit:junit-dep:4.11')

            project.groovysh.shell.groovyVersion = version
            TestHelper.setupTasks(project)
            List<String> dependencyVersions =
                    project.configurations.appShellConf.dependencies.asList().collect { it.name + it.version }
            assert dependencyVersions.contains('jline2.11')
            assert dependencyVersions.contains('commons-cli1.2')
            assert dependencyVersions.contains('groovy-all' + version)
        }
    }

    @Test
    void testConfigureMainWithArgs() {
        Project project = TestHelper.createProjectWithPlugin()
        project.dependencies.add('testCompile', 'junit:junit-dep:4.11')
        project.groovysh.shell.args = ['foo']
        project.groovysh.shell.jvmArgs = ['-Xms512m']
        project.groovysh.shell.workingDir = project.file('subfolder')
        project.groovysh.shell.extraClasspath = project.files('lib/junit.jar')

        project.groovysh.shell.bootstrapClasspath = project.files('lib/foo.jar')
        project.groovysh.shell.enableAssertions = true
        project.groovysh.shell.environment = ['FOO': 'BAR']

        ByteArrayOutputStream bos1 = new ByteArrayOutputStream()
        project.groovysh.shell.errorOutput = bos1
        project.groovysh.shell.maxHeapSize = '1024m'
        ByteArrayInputStream bis = new ByteArrayInputStream('test'.bytes)
        project.groovysh.shell.standardInput = bis
        ByteArrayOutputStream bos2 = new ByteArrayOutputStream()
        project.groovysh.shell.standardOutput = bos2
        project.groovysh.shell.systemProperties = ['BAR': 'FOO']

        TestHelper.setupTasks(project)
        JavaExec shellTask = (JavaExec) project.tasks.findByName(ApplicationShellTask.NAME)

        assert shellTask != null
        List<String> dependencyVersions =
                project.configurations.appShellConf.dependencies.asList().collect { it.name + it.version }
        assert dependencyVersions.contains('jline2.11')
        assert dependencyVersions.contains('commons-cli1.2')
        assert dependencyVersions.contains('groovy-all2.3.0')
        assert shellTask.dependsOn.contains('classes')
        assert shellTask.classpath.asPath.contains('groovy')
        assert shellTask.classpath.asPath.contains('junit')
        assert shellTask.args == ['foo']
        assert shellTask.allJvmArgs.contains('-Xms512m')
        assert shellTask.workingDir == project.file('subfolder')

        assert shellTask.bootstrapClasspath.asPath.contains('lib/foo.jar')
        assert shellTask.enableAssertions
        assert shellTask.environment == ['FOO': 'BAR']
        assert shellTask.errorOutput == bos1
        assert shellTask.maxHeapSize == '1024m'
        assert shellTask.standardInput == bis
        assert shellTask.standardOutput == bos2
        assert shellTask.systemProperties == ['BAR': 'FOO']

    }
}
