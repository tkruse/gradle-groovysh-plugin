# Example for running tests interactively.

Session:

```Groovy
$ gradle -q shell
This is a gradle Application Shell.
You can import your application classes and act on them.
Groovy Shell (2.3.1, JVM: 1.7.0_55)
Type ':help' or ':h' for help.
--------------------------------------------------
groovy:000> import org.junit.runner.JUnitCore
===> org.junit.runner.JUnitCore
groovy:000> jCore = new JUnitCore()
===> org.junit.runner.JUnitCore@53daaf33
groovy:000> import com.example.ExampleTest
===> org.junit.runner.JUnitCore, com.example.ExampleTest
groovy:000> result = jCore.run(ExampleTest.class)
===> org.junit.runner.Result@42f8b48b
groovy:000> result.getFailures()
===> []
```
