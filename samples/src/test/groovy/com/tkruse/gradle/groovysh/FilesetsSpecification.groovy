package com.tkruse.gradle.groovysh

import org.gradle.tooling.BuildLauncher
import spock.lang.Specification

public class FilesetsSpecification extends Specification {

    def "Run test"() {
        setup:
        BuildLauncher launcher = LauncherHelper.getLauncheForProject('filesets', ['clean', 'buildDevShell'] as String[])

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream()
        ByteArrayOutputStream bytesErr = new ByteArrayOutputStream()
        String input = '''\
println '1:' + project.tasks.copyTask2.getInputs().getFiles().collect{it.name}
println '2:' + project.tasks.copyTask.getInputs().getFiles().collect{it.name}
project.tasks.copyTask.execute()
println '3:' + project.tasks.copyTask2.getInputs().getFiles().collect{it.name}
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
        assert bytesOut.toString().contains('1:[]')
        assert bytesOut.toString().contains('2:[takeMe.md, notMe-staging.txt, takeme.txt]')
        assert bytesOut.toString().contains('3:[takeMe.md, notMe-staging.txt, takeme.txt]')
        assert !bytesOut.toString().contains('Exception')

    }
}
