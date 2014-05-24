# Example for multiple defined shell tasks

Using tasks ```mainShell``` and ```testShell``` as examples.

Session:

```Groovy
$ gradle -q mainShell
This is a gradle Application Shell.
You can import your application classes and act on them.
Groovy Shell (2.3.1, JVM: 1.7.0_55)
Type ':help' or ':h' for help.
groovy:000> import com.example.Example
===> com.example.Example
groovy:000> new Example().helloWorld()
===> Hello World
```

```Groovy
$ gradle -q testShell
This is a gradle Application Shell.
You can import your application classes and act on them.
Groovy Shell (2.3.1, JVM: 1.7.0_55)
Type ':help' or ':h' for help.
groovy:000> import com.example.ExampleTest
===> com.example.ExampleTest
```
