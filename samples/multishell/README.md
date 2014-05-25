# Example for multiple defined shell tasks

Using tasks ```mainShell``` and ```testShell``` as examples.

```mainShell``` has defined initial commands:

```Groovy
$ gradle -q mainShell
This is a gradle Application Shell.
You can import your application classes and act on them.
import com.example.Example
===> com.example.Example
e = new Example()
===> com.example.Example@3cbbe701
Groovy Shell (2.3.1, JVM: 1.7.0_55)
Type ':help' or ':h' for help.
------------------------------
groovy:000> e
===> com.example.Example@3cbbe701
groovy:000> 
```

```testShell``` has the test  classes on the classpath:

```Groovy
$ gradle -q testShell
This is a gradle Application Shell.
You can import your application classes and act on them.
Groovy Shell (2.3.1, JVM: 1.7.0_55)
Type ':help' or ':h' for help.
groovy:000> import com.example.ExampleTest
===> com.example.ExampleTest
```
