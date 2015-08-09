package com.tkruse.gradle.groovysh

import org.gradle.tooling.BuildLauncher
import spock.lang.Specification

/**
 * Tests the gradle API on the junit sample project
 */
class JUnitSpecification extends Specification {

    void testRunTests() {
        setup:
        BuildLauncher launcher = LauncherHelper.getLauncherForProject('junit')

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream()
        ByteArrayOutputStream bytesErr = new ByteArrayOutputStream()
        String input = '''\
import org.junit.runner.JUnitCore
import com.example.ExampleTest

jCore = new JUnitCore()
result = jCore.run(ExampleTest.class)
println \'runs:\' + result.getRunCount()
        '''
        ByteArrayInputStream bytesIn = new ByteArrayInputStream(input.bytes)

        launcher.standardOutput = bytesOut
        launcher.standardError = bytesOut
        launcher.standardInput = bytesIn

        when:
        launcher.run()

        then:
        assert bytesErr.toString() == ''
        assert bytesOut.toString() =~ ('Groovy Shell')
        assert bytesOut.toString().contains('com.example.ExampleTest')
        assert bytesOut.toString().contains('runs:1')
        assert !bytesOut.toString().contains('Exception')

    }
}
