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
        /* ... the other params of gradle JavaExec can also be used */
    }

    buildShell {

       // gradle version determines version of connector in tooling API,
       // but connector can use other gradle version to build project via
       // connector.useGradleVersion('1.10')
       // gradleVersion = '2.0'

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
```buildShell``` task due to mysterious Classpath problems. The ```buildDevShell```
task always runs with the Groovy version gradle ships with ( 1.8.6 **(;_;)** ).

## Custom task configuration

If you want to define multiple different tasks, such as a special task for a shell with the unit
test dependencies in the classpath, you can extend The ApplicationShell task:

```Groovy
task testShell(type: com.tkruse.gradle.groovysh.ApplicationShellTask) {
    sourceSetName = 'test'
}
```

## Initial commands

In order to speed up your daily work with a shell, you can run some commands to import frequently used 
packages, create Instances, or even load groovy files. Note that the load command of Groovysh changed 
from ```load``` to ```:load``` between versions.

```Groovy
groovysh {
    shell {
        args = ['-e', 'import com.example.Example\ne = new Example()']
    }
}
```

or 

```Groovy
task testShell(type: com.tkruse.gradle.groovysh.ApplicationShellTask) {
    args = ['-e', 'import com.example.Example\ne = new Example()']
}
```
