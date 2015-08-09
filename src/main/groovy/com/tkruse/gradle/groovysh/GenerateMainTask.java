package com.tkruse.gradle.groovysh;

import groovy.lang.Closure;
import org.gradle.api.Project;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import java.io.File;

/**
 * JavaCompile Task specialized to compile java generated into build space
 */
// NOTE: This is written as java file because else the plugin would not be gradle1.x gradle2.x compatible
public class GenerateMainTask extends DefaultTask {

    static final String NAME = "compileGroovyshPatchedMain";
    public static final String PATCH_CLASS_NAME = "PatchedMain";
    public static final String PATCH_CLASS_CAN_NAME = "org.codehaus.groovy.tools.shell." + PATCH_CLASS_NAME;
    public static final String CONFIGURATION_NAME = "appShellCompileMainConf";

    public GenerateMainTask() {
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


    @TaskAction
    public void apply() {
        Project project = getProject();
        TaskHelper.generatePatchedMain(project, PATCH_CLASS_NAME);
    }

}
