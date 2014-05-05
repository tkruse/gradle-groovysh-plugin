# Installing a Build Shell manually

In case you do not wish to depend on the gradle-groovysh-plugin for one reason or another, here is how you can obtain
the same results:

    configurations {
      // for gradle shell experiment
      addToClassLoader {extendsFrom compile}
    }

    dependencies {
      // for gradle shell experiment
      addToClassLoader "jline:jline:1.0"
    }

    /** Gradle shell experiment */

    URLClassLoader loader = GroovyObject.class.classLoader
    configurations.addToClassLoader.each {File file ->
      loader.addURL(file.toURL())
    }

    task('buildshell') {
      // This task is useful for learning gradle and making changes to the gradle files
      group 'help'
      description 'Runs an interactive shell for gradle. Experimental.'
      doFirst {
        if (getProperty('org.gradle.daemon') == 'true') {
          throw new IllegalStateException('Do not run shell with gradle daemon, it will eat your arrow keys.')
        }
      }
      doLast {
        def shell = new org.codehaus.groovy.tools.shell.Groovysh()
        shell.interp.context.variables.put("project", this)
        shell.run()
      }
    }

Remember that this will give you a shell for the Groovy version of gradle (1.8.6 for Gradle 1.12 at the time of writing this), which is
much less usable than the latest Groovy shell.
