package com.tkruse.gradle.groovysh

import org.codehaus.groovy.tools.shell.Groovysh
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * a task that starts the groovysh shell in the current gradle process, useful for debugging gradle builds.
 */
class BuildDevShellTask extends DefaultTask {

    static final String NAME = 'buildDevShell'
    static final String CONFIGURATION_NAME = 'buildDevShellConf'

    BuildDevShellTask() {
        this.group = 'help'
        this.description = 'starts a groovysh shell to introspect the gradle setup itself'
        this.outputs.upToDateWhen { false }
    }

    @TaskAction
    void exec() {
        TaskHelper.checkDaemon(project)
        TaskHelper.checkQuiet(project)
        TaskHelper.checkParallel(project)

        println("$GroovyshPlugin.NAME: This is a gradle Build-Shell. The variable 'project' is bound to your project.")
        println("$GroovyshPlugin.NAME: Run e.g. 'project.ext.properties' to inspect your project.")

        Groovysh shell = new Groovysh()
        // this hacks into Groovysh internal API and may break in the future.
        shell.interp.context.variables.put('project', project)

        // convenience access
        for (String varname in ['ant',
                                'artifacts',
                                'buildDir',
                                'configurations',
                                'components',
                                'dependecies',
                                'extensions',
                                'gradle',
                                'repositories',
                                'rootDir',
                                'rootProject']) {
            shell.interp.context.variables.put(varname, project.getProperties().get(varname))
        }

        shell.run((String) null)
    }
}
