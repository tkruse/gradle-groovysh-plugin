package com.tkruse.gradle.groovysh

import org.gradle.api.Plugin
import org.gradle.api.Project

class GroovyshPlugin
    implements Plugin<Project> {

    public static final String NAME = 'groovysh';
    private final static String DAEMON_PROP = 'org.gradle.daemon';

    @Override
    void apply( final Project project )
    {
        project.repositories.jcenter()
        project.extensions.create(NAME, GroovyshPluginExtension)
        project.configurations.create(BuildShellTask.CONFIGURATION_NAME)
        project.tasks.create(BuildShellTask.NAME, BuildShellTask.class)

        URLClassLoader loader = GroovyObject.class.classLoader

        // groovy < 2.2.0 groovysh runs on jline 1.0
        project.dependencies.add(BuildShellTask.CONFIGURATION_NAME, "jline:jline:1.0")

        project.configurations.getByName(BuildShellTask.CONFIGURATION_NAME).each {File file ->
            loader.addURL(file.toURL())
        }
    }

    static void checkDeamon(Project project) {
        if (project.hasProperty(GroovyshPlugin.DAEMON_PROP) && project.property(GroovyshPlugin.DAEMON_PROP) == 'true') {
            throw new IllegalStateException("$NAME: Do not run $NAME with gradle daemon (use --no-daemon).")
        }
    }

    static void checkQuiet(Project project) {
        if (project.logger.isWarnEnabled()) {
            throw new IllegalStateException("$NAME: Do not run $NAME with logging output. (use -q)")
        }
    }
}
