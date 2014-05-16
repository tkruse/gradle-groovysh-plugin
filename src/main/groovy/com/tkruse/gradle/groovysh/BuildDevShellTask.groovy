package com.tkruse.gradle.groovysh

import org.codehaus.groovy.tools.shell.Groovysh
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

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
        shell.interp.context.variables.put('ant', project.ant)
        shell.interp.context.variables.put('artifacts', project.artifacts)
        shell.interp.context.variables.put('buildDir', project.buildDir)
        shell.interp.context.variables.put('configurations', project.configurations)
        shell.interp.context.variables.put('components', project.components)
        shell.interp.context.variables.put('dependecies', project.dependencies)
        shell.interp.context.variables.put('extensions', project.extensions)
        shell.interp.context.variables.put('gradle', project.gradle)
        shell.interp.context.variables.put('repositories', project.repositories)
        shell.interp.context.variables.put('rootDir', project.rootDir)
        shell.interp.context.variables.put('rootProject', project.rootProject)

        shell.run()
    }
}
