package com.example;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExampleTest {

    @Test
    public void testHelloWorld() {
        Example example = new Example();
        assertEquals(example.helloWorld(), "Hello World");
    }

}
