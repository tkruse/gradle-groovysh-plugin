package com.tkruse.gradle.groovysh

import org.gradle.api.file.FileCollection

/**
 * common shell tasks configuration
 */
class ShellTaskExtension {

    String sourceSetName = 'main'
    FileCollection extraClasspath
    List<String> jvmArgs
    List<String> args
    File workingDir
    // following JavaExec properties provided just for completeness sake
    FileCollection bootstrapClasspath
    boolean enableAssertions = false
    Map<String, Object> environment
    OutputStream errorOutput
    String maxHeapSize
    InputStream standardInput = System.in
    OutputStream standardOutput
    Map<String, Object> systemProperties
    String gradleVersion

}
