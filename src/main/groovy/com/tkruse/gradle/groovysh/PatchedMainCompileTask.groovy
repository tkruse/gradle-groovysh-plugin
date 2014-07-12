package com.tkruse.gradle.groovysh

import org.gradle.api.tasks.compile.JavaCompile

/**
 * JavaCompile Task specialized to compile java generated into build space
 */
class PatchedMainCompileTask extends JavaCompile {

    static final String NAME = 'compileGroovyshPatchedMain'
    static final String PATCH_CLASS_NAME = 'PatchedMain'
    static final String PATCH_CLASS_CAN_NAME = 'org.codehaus.groovy.tools.shell.' + PATCH_CLASS_NAME
    static final String CONFIGURATION_NAME = 'appShellCompileMainConf'

    PatchedMainCompileTask() {
        this.group = 'help'
        this.outputs.upToDateWhen { false }
    }

    @Override
    void executeWithoutThrowingTaskFailure() {
        project.configurations.create(CONFIGURATION_NAME)
        TaskHelper.addGroovyDependencies(project, CONFIGURATION_NAME, project.groovysh.groovyVersion)
        File genFile = TaskHelper.generatePatchedMain(project, PATCH_CLASS_NAME)
        this.destinationDir = new File(project.buildDir, 'groovyshClasses')
        this.source = genFile
        this.classpath = (project.configurations.getByName(CONFIGURATION_NAME).asFileTree
                + project.files(genFile.getParent()))
        super.executeWithoutThrowingTaskFailure()
    }

}
