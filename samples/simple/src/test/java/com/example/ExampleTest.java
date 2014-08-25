package com.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * example test
 */
public class ExampleTest {

    @Test
    public void testHelloWorld() {
        final Example example = new Example();
        assertEquals(example.helloWorld(), "Hello World");
    }

}
