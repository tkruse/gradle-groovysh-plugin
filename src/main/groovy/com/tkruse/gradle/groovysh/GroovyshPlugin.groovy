package com.tkruse.gradle.groovysh

import org.gradle.api.Plugin
import org.gradle.api.Project

class GroovyshPlugin
    implements Plugin<Project> {

    @Override
    void apply( final Project project )
    {
        project.repositories.jcenter()
        project.extensions.create("groovysh", GroovyshPluginExtension)
        project.configurations.create(BuildShellTask.CONFIGURATION_NAME)
        project.tasks.create(BuildShellTask.NAME, BuildShellTask.class)

        URLClassLoader loader = GroovyObject.class.classLoader

        // groovy < 2.2.0 groovysh runs on jline 1.0
        project.dependencies.add(BuildShellTask.CONFIGURATION_NAME, "jline:jline:1.0")

        project.configurations.getByName(BuildShellTask.CONFIGURATION_NAME).each {File file ->
            loader.addURL(file.toURL())
        }

    }
}
