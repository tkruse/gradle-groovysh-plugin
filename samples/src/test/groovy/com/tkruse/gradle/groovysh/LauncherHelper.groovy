package com.tkruse.gradle.groovysh

import groovy.transform.TypeChecked
import org.gradle.tooling.BuildLauncher
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection

@TypeChecked
class LauncherHelper {

    static BuildLauncher getLauncherForProject(final String projectName, String[] tasks = ['shell']) {
        GradleConnector connector = GradleConnector.newConnector()
        connector.forProjectDirectory(new File(projectName))
        ProjectConnection connection = connector.connect()
        BuildLauncher launcher = connection.newBuild()
        launcher.forTasks(tasks)
        return launcher
    }
}
