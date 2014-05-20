package com.tkruse.gradle.groovysh

import groovy.transform.TypeChecked
import org.gradle.tooling.BuildLauncher
import org.gradle.tooling.GradleConnector
import org.gradle.tooling.ProjectConnection
import org.gradle.tooling.model.GradleProject

@TypeChecked
class LauncherHelper {

    static BuildLauncher getLauncheForProject(final String projectName) {
        GradleConnector connector = GradleConnector.newConnector()
        connector.forProjectDirectory(new File(projectName))
        ProjectConnection connection = connector.connect()
        BuildLauncher launcher = connection.newBuild()
        launcher.forTasks('shell')
        return launcher
    }
}
