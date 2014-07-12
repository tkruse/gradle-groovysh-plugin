# Using Grapes with Groovysh

groovy:000> import groovy.grape.Grape
===> [import groovy.grape.Grape]
groovy:000> Grape.grab(group:'example', module:'example', version:'0.1.0')
===> null


# policy file to start tomcat / spring boot 

See the sample for how to start a Spring Application and get a handle to the ApplicationContext.
Starting tomcat can cause a Exception like this:

    java.security.AccessControlException: access denied (javax.management.MBeanTrustPermission register)

There are several ways to solve this, here is just one.
Place a file called e.g. allowjmx.policy in your project Dir, with this content:
 
    grant {
      permission javax.management.MBeanTrustPermission "register";
    };
    
In your ```build.gradle```, use:

```Groovy
groovysh {
    shell {
        jvmArgs = ['-noverify', "-Djava.security.policy=$projectDir/allowjmx.policy"]
    }
}
```

# :edit command for lengthy code-blocks

When writing classes of lengthy methods, line by line-editing can quickly become tiring. 
Use the :edit command to start your favorite quick-starting editor to edit the current buffer.

# Gradle BuildLauncher Arguments

```
$gradle -Pfoo=123
```

is equivalent to 
```
```
