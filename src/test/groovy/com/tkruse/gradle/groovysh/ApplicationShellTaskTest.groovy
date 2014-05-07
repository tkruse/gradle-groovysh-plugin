package com.tkruse.gradle.groovysh;

import org.gradle.api.Project;
import org.gradle.api.tasks.JavaExec;

class ApplicationShellTaskTest extends GroovyTestCase {

    void testExecTest() {
        Project project = GroovyshPluginTest.createProjectWithPlugin()
        project.dependencies.add('compile', 'org.slf4j:slf4j-api:1.7.7');
        project.dependencies.add('testCompile', 'junit:junit-dep:4.11');
        project.groovysh.shell.sourceSetName = 'test';
        GroovyshPluginTest.setupTasks(project);
        JavaExec shellTask = project.tasks.findByName('shell');
        assert shellTask != null;
        assert shellTask.dependsOn.contains('testClasses');
        assert shellTask.classpath.asPath.contains('groovy');
        assert shellTask.classpath.asPath.contains('slf4j');
        assert shellTask.classpath.asPath.contains('junit');
    }

    void testExecMain() {
        Project project = GroovyshPluginTest.createProjectWithPlugin();
        project.dependencies.add('compile', 'org.slf4j:slf4j-api:1.7.7');
        project.dependencies.add('testCompile', 'junit:junit-dep:4.11');
        GroovyshPluginTest.setupTasks(project);
        JavaExec shellTask = project.tasks.findByName('shell');
        assert shellTask != null;
        assert shellTask.dependsOn.contains('classes');
        assert shellTask.classpath.asPath.contains('groovy');
        assert shellTask.classpath.asPath.contains('slf4j');
        assert !shellTask.classpath.asPath.contains('junit');
    }
}
