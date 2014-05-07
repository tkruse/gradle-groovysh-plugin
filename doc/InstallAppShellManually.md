# Installing an Application Shell manually

In case you do not wish to depend on the gradle-groovysh-plugin for any reason, here is how you can obtain
the same results:

In your ```build.gradle```:

```Groovy
apply plugin: 'java'

dependencies {
  compile('org.codehaus.groovy:groovy-all:2.3.0') { force = true }
  compile('commons-cli:commons-cli:1.2')
  // when using groovy < 2.2 above:
  // "jline:jline:1.0"
  // Groovy >= 2.2
  compile("jline:jline:2.11") {
    exclude(group: 'junit', module: 'junit')
  }
}

task shell(dependsOn: 'testClasses', type: JavaExec) {
  group = 'help'
  description 'Runs an interactive shell with all runtime dependencies. "Use with gradle -q shell".'
  doFirst {
    if (getProperty('org.gradle.daemon') == 'true') {
        throw new IllegalStateException('Do not run shell with gradle daemon, it will eat your arrow keys.')
    }
  }
  standardInput = System.in
  main = 'shell.ShellMain'
  // using Main directly has ansi issues (some keyboard keys not working)
  // main = 'org.codehaus.groovy.tools.shell.Main'
  classpath = sourceSets.main.runtimeClasspath
  jvmArgs = []
  // workingDir =
  // standardOutput = System.out
  // stops after eval, not useful now (maybe will change with Groovy >= 2.3.2)
  // args = ["load $rootDir/integration-test/src/main/groovy/GroovyshStartup.groovy"]

}
```

Also create the file shell.ShellMain.java (or whatever you want to call it, used as ```main = ...``` in task ```shell```):

```Java
package shell;

import org.codehaus.groovy.tools.shell.Main;
import org.fusesource.jansi.AnsiConsole;

class ShellMain {
  public static void main(String[] args) {
    // workaround for jAnsi problems, (backspace and arrow keys not working)
    AnsiConsole.systemUninstall();
    Main.main(args);
  }
}
```

You can also use a Groovy Class, of course.
