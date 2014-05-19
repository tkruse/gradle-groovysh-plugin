package com.tkruse.gradle.groovysh

import org.gradle.api.tasks.compile.JavaCompile

class PatchedMainCompileTask extends JavaCompile {

    static final String NAME = 'compileGroovyshMain'

    PatchedMainCompileTask() {
        this.group = 'help'
        this.outputs.upToDateWhen { false }
    }

    @Override
    void executeWithoutThrowingTaskFailure() {
        String className = 'PatchedMain'
        File genFile = TaskHelper.generatePatchedMain(project, className)
        this.destinationDir = new File(project.buildDir, 'groovyshClasses')
        this.source = genFile
        this.classpath = project.configurations.appShellConf.asFileTree + project.files(genFile.getParent())
        super.executeWithoutThrowingTaskFailure()
    }

}
