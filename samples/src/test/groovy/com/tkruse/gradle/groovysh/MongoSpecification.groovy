package com.tkruse.gradle.groovysh

import org.gradle.tooling.BuildLauncher
import spock.lang.Specification

/**
 * Tests the gradle API on the mongo sample project
 */
class MongoSpecification extends Specification {

    def testMongoDbRepoAndService() {
        setup:
        BuildLauncher launcher = LauncherHelper.getLauncherForProject('mongo')

        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream()
        ByteArrayOutputStream bytesErr = new ByteArrayOutputStream()
        String input = '''\
import com.example.config.*

ctx = Application.run()
repo = ctx.getBean(PersonRepository)
service = ctx.getBean(PersonService)
repo.save(new Person('jane', 'dane'))
repo.findAll()
service.normalizeNames(repo.findByLastName('dane')[0].id)
repo.findAll()
repo.deleteAll()
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
        assert bytesOut.toString().contains('firstName=jane')
        assert bytesOut.toString().contains('lastName=dane)')
        assert bytesOut.toString().contains('firstName=Jane')
        assert bytesOut.toString().contains('lastName=Dane)')

    }
}
