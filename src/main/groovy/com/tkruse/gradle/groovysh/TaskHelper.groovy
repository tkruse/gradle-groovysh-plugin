package com.tkruse.gradle.groovysh

import org.gradle.api.Project
import org.gradle.api.logging.LogLevel

class TaskHelper {

    private final static String DAEMON_PROP = 'org.gradle.daemon'

    static void checkDaemon(final Project project, final String name) {
        if (project.hasProperty(DAEMON_PROP) && project.property(DAEMON_PROP) == 'true') {
            String msg = "$name: Do not run $name with gradle daemon (use --no-daemon)."
            println(msg)
            throw new IllegalStateException(msg)
        }
    }

    static void checkParallel(final Project project, final String name) {
        if (project.gradle.startParameter.parallelThreadCount != 0) {
            String msg = "$name: Do not run $name with parallel thread. (use --parallel-threads 0)"
            println(msg)
            throw new IllegalStateException(msg)
        }
    }

    static void checkQuiet(final Project project, final String name) {
        if (!project.gradle.startParameter.logLevel == LogLevel.QUIET) {
            String msg = "$name: Do not run $name with logging output. (use -q)"
            println(msg)
            throw new IllegalStateException(msg)
        }
    }

}
