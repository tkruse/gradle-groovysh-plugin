package com.tkruse.gradle.groovysh

import org.gradle.tooling.BuildLauncher
import spock.lang.Specification

/**
 * Tests the gradle API on the simple sample project
 */
class SimpleSpecification extends Specification {

    def testImportCustomClass() {
        setup:
        BuildLauncher launcher = LauncherHelper.getLauncherForProject('simple')

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream()
        ByteArrayOutputStream bytesErr = new ByteArrayOutputStream()
        ByteArrayInputStream bytesIn = new ByteArrayInputStream(
                'import com.example.Example\ne = new Example()'.bytes)

        launcher.standardOutput = bytesOut
        launcher.standardError = bytesOut
        launcher.standardInput = bytesIn

        when:
        launcher.run()

        then:
        assert bytesErr.toString() == ''
        assert bytesOut.toString() =~ ('Groovy Shell')
        assert bytesOut.toString().contains('com.example.Example')
        assert !bytesOut.toString().contains('Exception')

    }

    def testBuildshell() {
        setup:
        BuildLauncher launcher = LauncherHelper.getLauncherForProject('simple', ['clean', 'buildShell'] as String[])

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream()
        ByteArrayOutputStream bytesErr = new ByteArrayOutputStream()
        ByteArrayInputStream bytesIn = new ByteArrayInputStream(
                'connector.getClass()\n'.bytes)

        launcher.standardOutput = bytesOut
        launcher.standardError = bytesOut
        launcher.standardInput = bytesIn

        when:
        launcher.run()

        then:
        assert bytesErr.toString() == ''
        assert bytesOut.toString() =~ ('Groovy Shell')
        assert bytesOut.toString().contains('class org.gradle.tooling.internal.consumer.DefaultGradleConnector')
        assert !bytesOut.toString().contains('Exception')

    }
}
