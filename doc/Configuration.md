# Plugin Configuration

No configuration is required. To change the defaults, use a configuration block as below. All parts are optional.

```Groovy
groovysh {


    // false to disable the shell task
    enableAppShell = true
    // false to disable the shell task
    enableBuildShell = true
    // false to disable the buildDevShell task
    enablebuildDevShell = true

    // groovyVersion determines the features of the shell and buildShell tasks
    // groovyVersion = '2.3.0'

    shell {
        // default is 'main'
        sourceSetName = 'test'
        extraClasspath = configurations.runtime.asFileTree
        workingDir = file('src/main/webapp')
        jvmArgs = ['-Xmx1024m']
        /* arguments to groovysh */
        // args = ['--terminal=none']
        // groovyVersion = '2.3.0'
        /* ... the other params of gradle JavaExec can also be used */
    }

    buildShell {

       // gradle version determines version of connector in tooling API,
       // but connector can use other gradle version to build project via
       // connector.useGradleVersion('1.10')
       // gradleVersion = '1.12'

       /* ... params of gradle JavaExec can also be used */
    }

    buildDevShell {
    }
}
```

Disabling tasks mainly keeps your project environment a bit cleaner, but
just having them enable should not have any measurable impact on performance.
This is mostly provided in case one of the tasks fails to configure, but you
want to run another.

Groovy versions <= 2.2.1 are currently not supported by the ```shell`` and
```buildShell``` task due to mysterious Classpath problems. the ```buildDevShell```
task always runs with the Groovy version gradle ships with ( 1.8.6 **(;_;)** ).
