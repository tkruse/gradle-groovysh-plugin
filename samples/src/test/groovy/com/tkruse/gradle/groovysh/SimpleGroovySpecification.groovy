package com.tkruse.gradle.groovysh

import org.gradle.tooling.BuildLauncher
import spock.lang.Specification

/**
 * Tests the gradle API on the simpleGroovy sample project
 */
class SimpleGroovySpecification extends Specification {

    void testImportCustomclass() {
        setup:
        BuildLauncher launcher = LauncherHelper.getLauncherForProject('simpleGroovy',
                ['clean', 'shell'] as String[],
                ["-PgroovyVersion=$groovyVersion",
                 '-PgroovyshPluginIgnoreNullConsole=true',
                 '-q'] as String[])

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

        where:
        groovyVersion << ['2.0.0', '2.0.8',
                          '2.1.0', '2.1.9',
                          '2.2.0', '2.2.2',
                          '2.3.0', '2.3.9',
                          '2.4.0', '2.4.4']
    }
}
