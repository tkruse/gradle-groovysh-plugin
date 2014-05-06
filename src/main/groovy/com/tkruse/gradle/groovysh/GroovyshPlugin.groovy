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
        project.groovysh.extensions.create(BuildShellTask.NAME, BuildShellTaskExtension)
        project.groovysh.extensions.create(ApplicationShellTask.NAME, ApplicationShellTaskExtension)

        // need to have extensions read
        project.afterEvaluate {
            setupTasks(project)
        }
    }

    void setupTasks(final Project project) {
        if (project.groovysh.enableBuildShell) {
            project.configurations.create(BuildShellTask.CONFIGURATION_NAME)
            project.tasks.create(BuildShellTask.NAME, BuildShellTask.class)
            URLClassLoader loader = GroovyObject.class.classLoader
            // groovy < 2.2.0 groovysh runs on jline 1.0
            project.dependencies.add(BuildShellTask.CONFIGURATION_NAME, "jline:jline:1.0")
            project.configurations.getByName(BuildShellTask.CONFIGURATION_NAME).each { File file ->
                loader.addURL(file.toURL())
            }
        }
        if (project.groovysh.enableAppShell) {
            //NamedDomainObjectContainer cont = project.configurations.create(ApplicationShellTask.CONFIGURATION_NAME)
            if (project.configurations.getNames().contains('runtime')) {
                project.dependencies.add('runtime', 'org.codehaus.groovy:groovy-all:2.2.2')
                project.dependencies.add('runtime', 'commons-cli:commons-cli:1.2')
                project.dependencies.add('runtime', 'jline:jline:2.11')
                project.tasks.create(ApplicationShellTask.NAME, ApplicationShellTask.class)
                //project.dependencies.add(BuildShellTask.CONFIGURATION_NAME, "jline:jline:1.0")
            }
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
