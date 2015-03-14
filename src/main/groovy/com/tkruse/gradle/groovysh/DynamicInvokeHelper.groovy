package com.tkruse.gradle.groovysh

import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.api.plugins.ExtensionContainer

/**
 * wraps the ugliest Casting operations needed to make Gradle's dynamic API typeCheckable
 */
class DynamicInvokeHelper {

    static GroovyshPluginExtension getPluginExtension(Project project) {
        return (GroovyshPluginExtension) project.extensions.findByName(GroovyshPlugin.NAME)
    }

    static ExtensionContainer getPluginExtensionContainer(Project project) {
        return ((ExtensionAware) getPluginExtension(project)).extensions
    }

    static BuildDevShellTaskExtension getBuildDevShellExtension(Project project) {
        return (BuildDevShellTaskExtension) getPluginExtensionContainer(project).findByName(BuildDevShellTask.NAME)
    }

    static BuildShellTaskExtension getBuildShellExtension(Project project) {
        return (BuildShellTaskExtension) getPluginExtensionContainer(project)
                .findByName(BuildShellTask.NAME)
    }

    static ApplicationShellTaskExtension getApplicationShellExtension(Project project) {
        return (ApplicationShellTaskExtension) getPluginExtensionContainer(project)
                .findByName(ApplicationShellTask.NAME)
    }

    static void addGroovyshExtentions(Project project) {
        project.extensions.create(GroovyshPlugin.NAME, GroovyshPluginExtension)
        // not sure how to create nested extentions else...
        getPluginExtensionContainer(project).create(BuildDevShellTask.NAME, BuildDevShellTaskExtension)
        getPluginExtensionContainer(project).create(BuildShellTask.NAME, BuildShellTaskExtension)
        getPluginExtensionContainer(project).create(ApplicationShellTask.NAME, ApplicationShellTaskExtension)
    }
}
