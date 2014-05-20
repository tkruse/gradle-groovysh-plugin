package com.tkruse.gradle.groovysh

import groovy.transform.TypeChecked
import spock.lang.*
import org.gradle.tooling.*

public class SimpleSpecification extends Specification {

    def "import custom class"() {
        setup:
        BuildLauncher launcher = LauncherHelper.getLauncheForProject('simple')

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream()
        ByteArrayOutputStream bytesErr = new ByteArrayOutputStream()
        ByteArrayInputStream bytesIn = new ByteArrayInputStream(
                'import com.example.Example\ne = new Example()\n:quit'.bytes)

        launcher.standardOutput = bytesOut
        launcher.standardError = bytesOut
        launcher.standardInput = bytesIn

        when:
        launcher.run()

        then:
        assert bytesErr.toString() == ''
        assert bytesOut.toString() =~ ('Groovy Shell')
        assert bytesOut.toString().contains('import com.example.Example')
        assert !bytesOut.toString().contains('Exception')

    }
}
