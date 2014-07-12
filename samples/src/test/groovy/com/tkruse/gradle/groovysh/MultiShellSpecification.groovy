package com.tkruse.gradle.groovysh

import org.gradle.tooling.BuildLauncher
import spock.lang.Specification

/**
 * Tests the gradle API on the multishell sample project
 */
class MultiShellSpecification extends Specification {

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

    def testImportCustomClassStartupFromCustomTask() {
        setup:
        BuildLauncher launcher = LauncherHelper.getLauncherForProject('multishell', ['clean', 'mainShell'] as String[])

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream()
        ByteArrayOutputStream bytesErr = new ByteArrayOutputStream()
        ByteArrayInputStream bytesIn = new ByteArrayInputStream()

        launcher.standardOutput = bytesOut
        launcher.standardError = bytesOut
        launcher.standardInput = bytesIn

        when:
        launcher.run()

        then:
        assert bytesErr.toString() == ''
        assert bytesOut.toString() =~ ('Groovy Shell')
        assert bytesOut.toString().contains('com.example.Example')
        assert bytesOut.toString().contains('e = new Example()')
        assert !bytesOut.toString().contains('Exception')
    }

    def testImportCustomClassTestTask() {
        setup:
        BuildLauncher launcher = LauncherHelper.getLauncherForProject('multishell', ['clean', 'testShell'] as String[])

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream()
        ByteArrayOutputStream bytesErr = new ByteArrayOutputStream()
        ByteArrayInputStream bytesIn = new ByteArrayInputStream(
                'import com.example.ExampleTest\ne = new ExampleTest()'.bytes)

        launcher.standardOutput = bytesOut
        launcher.standardError = bytesOut
        launcher.standardInput = bytesIn

        when:
        launcher.run()

        then:
        assert bytesErr.toString() == ''
        assert bytesOut.toString() =~ ('Groovy Shell')
        assert bytesOut.toString().contains('com.example.ExampleTest')
        assert !bytesOut.toString().contains('Exception')
    }
}
