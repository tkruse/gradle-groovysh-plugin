package com.tkruse.gradle.groovysh

import org.gradle.tooling.BuildLauncher
import spock.lang.Specification

/**
 * Tests the gradle API on the filesets sample project
 */
class FilesetsSpecification extends Specification {

    void testBuildDevShell() {
        setup:
        BuildLauncher launcher = LauncherHelper.getLauncherForProject('filesets',
                ['clean', 'buildDevShell'] as String[], ['-q'] as String[])

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream()
        ByteArrayOutputStream bytesErr = new ByteArrayOutputStream()
        String input = '''\
println '1:' + project.tasks.copyTask2.getInputs().getFiles().collect({it.name}).sort()
println '2:' + project.tasks.copyTask.getInputs().getFiles().collect({it.name}).sort()
project.tasks.copyTask.execute()
println '3:' + project.tasks.copyTask2.getInputs().getFiles().collect({it.name}).sort()
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
        assert bytesOut.toString().contains('2:[notMe-staging.txt, takeMe.md, takeme.txt]')
        assert bytesOut.toString().contains('3:[takeMe.md]')
        assert !bytesOut.toString().contains('Exception')

    }
}
