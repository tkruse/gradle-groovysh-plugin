package com.tkruse.gradle.groovysh

import org.gradle.api.tasks.compile.JavaCompile

class PatchedMainCompileTask extends JavaCompile {

    static final String NAME = 'compileGroovyshMain'
    static final String PATCH_CLASS_NAME = 'PatchedMain'

    PatchedMainCompileTask() {
        this.group = 'help'
        this.outputs.upToDateWhen { false }
    }

    @Override
    void executeWithoutThrowingTaskFailure() {
        File genFile = TaskHelper.generatePatchedMain(project, PATCH_CLASS_NAME)
        this.destinationDir = new File(project.buildDir, 'groovyshClasses')
        this.source = genFile
        this.classpath = project.configurations.appShellConf.asFileTree + project.files(genFile.getParent())
        super.executeWithoutThrowingTaskFailure()
    }

}
