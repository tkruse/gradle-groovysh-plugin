package com.example;

/**
 * Most basic use-case: Using gradle shell to build and use a POJO.
 *
 * $ gradle -q shell
 * This is a gradle Application Shell.
 * You can import your application classes and act on them.
 * Groovy Shell (2.3.1, JVM: 1.7.0_55)
 * Type ':help' or ':h' for help.
 * groovy:000> import com.example.Example
 * ===> com.example.Example
 * groovy:000> new Example().helloWorld()
 * ===> Hello World
 *
 */
public class Example {
    public String helloWorld() {
        return "Hello World";
    }
}
