package com.tkruse.gradle.groovysh;

import groovy.lang.Closure;
import org.gradle.api.Project;
import org.gradle.api.tasks.compile.JavaCompile;

import java.io.File;

/**
 * JavaCompile Task specialized to compile java generated into build space
 */
// NOTE: This is written as java file because else the plugin would not be gradle1.x gradle2.x compatible
public class PatchedMainCompileTask extends JavaCompile {

    static final String NAME = "compileGroovyshPatchedMain";
    static final String PATCH_CLASS_NAME = "PatchedMain";
    static final String PATCH_CLASS_CAN_NAME = "org.codehaus.groovy.tools.shell." + PATCH_CLASS_NAME;
    static final String CONFIGURATION_NAME = "appShellCompileMainConf";

    public PatchedMainCompileTask() {
        this.setGroup("help");
        this.getOutputs().upToDateWhen(new Closure(this) {
            @Override
            public Object call() {
                return false;
            }

            @Override
            public Object call(Object arguments) {
                return false;
            }
        });
    }

    @Override
    public void executeWithoutThrowingTaskFailure() {
        Project project = getProject();
        project.getConfigurations().create(CONFIGURATION_NAME);
        TaskHelper.addGroovyDependencies(project, CONFIGURATION_NAME,
                ((GroovyshPluginExtension) project.getExtensions().getByName(GroovyshPlugin.NAME)).getGroovyVersion());
        File genFile = TaskHelper.generatePatchedMain(project, PATCH_CLASS_NAME);
        this.setDestinationDir(new File(project.getBuildDir(), "groovyshClasses"));
        this.setSource(genFile);
        this.setClasspath(project.getConfigurations().getByName(CONFIGURATION_NAME).getAsFileTree()
                .plus(project.files(genFile.getParent())));
        super.executeWithoutThrowingTaskFailure();
    }

}
