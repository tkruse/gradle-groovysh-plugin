## Github API

code:

    apply plugin: 'java'
    apply plugin: 'com.github.tkruse.groovysh'

    ...

    dependencies {
        runtime("org.eclipse.mylyn.github:org.eclipse.egit.github.core:2.1.5")
    }

Session:

    $ gradle -q shell
    This is a gradle Application Shell.
    You can import your application classes and act on them.
    Groovy Shell (2.3.1, JVM: 1.7.0_55)
    Type ':help' or ':h' for help.
    ------------------------------------------------------------
    groovy:000> import org.eclipse.egit.github.core.service.*
    ===> org.eclipse.egit.github.core.client.*, org.eclipse.egit.github.core.*, org.eclipse.egit.github.core.service.*
    groovy:000> service = new RepositoryService();
    ===> org.eclipse.egit.github.core.service.RepositoryService@1943dd11
    groovy:000> service.getRepositories("tkruse").each{println it.name}
    ackg
    adream_stage
    bloom
    buildfarm
    catkin
    catkin-sphinx
    catkinize
    ...


