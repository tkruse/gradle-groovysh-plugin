package com.tkruse.gradle.groovysh

import org.gradle.api.Project
import org.gradle.api.GradleScriptException
import org.gradle.api.InvalidUserDataException
import org.gradle.api.logging.LogLevel
import org.gradle.api.artifacts.Dependency
import org.gradle.api.tasks.compile.JavaCompile

/**
 * Utils for shell tasks execution
 */
class TaskHelper {

    private final static String DAEMON_PROP = 'org.gradle.daemon'

    static void checkDaemon(final Project project) {
        if (project.hasProperty(DAEMON_PROP) && project.property(DAEMON_PROP) == 'true') {
            String msg = 'Do not run with gradle daemon (use --no-daemon).'
            println(msg)
            throw new IllegalStateException(msg)
        }
    }

    static void checkParallel(final Project project) {
        if (project.gradle.startParameter.parallelThreadCount != 0) {
            String msg = 'Do not run with parallel thread. (use --parallel-threads 0)'
            println(msg)
            throw new IllegalStateException(msg)
        }
    }

    static void checkQuiet(final Project project) {
        // Cannot use project.logging.level, it returns null (gradle 1.12)
        if (project.gradle.startParameter.logLevel != LogLevel.QUIET) {
            String msg = 'Do not run with logging output. (use -q)'
            println(msg)
            throw new IllegalStateException(msg)
        }
    }

    static void addGroovyDependencies(final Project project,
                                      final String configurationName,
                                      final String groovyVersion) {
        List<Dependency> deps = project.configurations.getByName(configurationName).allDependencies
                .collect { Dependency it -> it }
        String actualGroovyVersion = getActualGroovyVersion(deps)
        if (actualGroovyVersion == null) {
            actualGroovyVersion = groovyVersion
        }
        switch (actualGroovyVersion) {
            case ~/1\.8\.[0-9].*/:
            case ~/0\.[0-9]\.[0-9].*/:
            case ~/1\.[0-9]\.[0-9].*/:
            case ~/2\.0\.[0-9].*/:
            case ~/2\.1\.[0-9].*/:
                addIfMissing(project, configurationName, deps, 'org.fusesource.jansi', 'jansi', '1.6')
                addIfMissing(project, configurationName, deps, 'jline', 'jline', '1.0')
                break
            case ~/2\.[2-4]\.[0-9].*/:
                addIfMissing(project, configurationName, deps, 'jline', 'jline', '2.11')
                break
            default:
                String msg = "Unknown Groovy version '$actualGroovyVersion'"
                println(msg)
                throw new IllegalStateException(msg)
        }
        addIfMissing(project, configurationName, deps, 'commons-cli', 'commons-cli', '1.2')
        addIfMissing(project, configurationName, deps,
                'org.codehaus.groovy', 'groovy-all', actualGroovyVersion, 'groovy')
    }

    static String getActualGroovyVersion(final List<Dependency> deps) {
        for (Dependency dep : deps) {
            if (dep.group == 'org.codehaus.groovy') {
                if ((dep.name == 'groovy-all') || (dep.name == 'groovy')) {
                    return dep.version
                }
            }
        }
        return null
    }

    /**
     * adds given dependency to project dependencies if that group+module is not already declared
     */
    static void addIfMissing(final Project project, final String configurationName, final List<Dependency> deps,
                     final String group, final String module, final String version, String altModule = null) {
        boolean found = false
        for (Dependency dep : deps) {
            if (dep.group == group) {
                if ((dep.name == module) || ((altModule != null) && (dep.name == altModule))) {
                    found = true
                    if (dep.version != version) {
                        println("WARNING, version $dep.version mismatches \
desired groovysh version $version for $group:$module")
                    }
                }
            }
        }
        if (!found) {
            project.dependencies.add(configurationName, "$group:$module:$version")
        }
    }

    static File getPatchedMainDir(final Project project) {
        return new File("${project.buildDir}/groovyshSrc/org/codehaus/groovy/tools/shell")
    }

    static File getPatchedMainFile(final Project project, final String className) {
        return new File(getPatchedMainDir(project), className + '.java')
    }

    static void configureProjectForAppShell(Project project) {
        try {
            project.getConfigurations().create(GenerateMainTask.CONFIGURATION_NAME)
            addGroovyDependencies(
                project, GenerateMainTask.CONFIGURATION_NAME,
                ((GroovyshPluginExtension) project.getExtensions().getByName(GroovyshPlugin.NAME)).getGroovyVersion())

            GenerateMainTask generateTask = project.tasks.create(GenerateMainTask.NAME + 'Generate', GenerateMainTask)
            JavaCompile compileTask = project.tasks.create(GenerateMainTask.NAME, JavaCompile)
            compileTask.dependsOn(generateTask)
            configureCompileTask(project, compileTask)

        } catch (InvalidUserDataException e) {
            // task already exists, not 100 lines of stacktrace needed to understand
          throw new GradleScriptException("$GroovyshPlugin.NAME: Cannot create task ${GenerateMainTask.NAME}", e)
        }
    }

    static void configureCompileTask(Project project, JavaCompile compileTask) {
        File genFile = TaskHelper.getPatchedMainFile(project, GenerateMainTask.PATCH_CLASS_NAME)
        compileTask.setSource(genFile)
        compileTask.options.warnings = false
        compileTask.setDestinationDir(new File(project.getBuildDir(), 'groovyshClasses'))
        compileTask.setClasspath(project.getConfigurations().getByName(GenerateMainTask.CONFIGURATION_NAME)
                                 .getAsFileTree() + project.files(genFile.getParent()))
    }

    static File generatePatchedMain(final Project project, final String className) {
        project.buildDir.mkdir()
        File genDir = getPatchedMainDir(project)
        genDir.mkdirs()
        File genFile = getPatchedMainFile(project, className)
        genFile.delete()

        Properties properties = new Properties()
        String propFileName = '/com.tkruse.gradle.groovysh/TaskHelper.properties'
        InputStream inputStream = TaskHelper.getResourceAsStream(propFileName)
        properties.load(inputStream)

        genFile << properties.getProperty('mainTemplate')
        return genFile
    }

}
