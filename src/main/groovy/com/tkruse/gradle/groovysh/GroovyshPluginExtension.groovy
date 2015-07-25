package com.tkruse.gradle.groovysh

/**
 * config for the plugin itself
 */
class GroovyshPluginExtension {

    boolean enableBuildDevShell = true
    boolean enableBuildShell = true
    boolean enableAppShell = true

    String groovyVersion = '2.4.4'
}
