package com.example

import org.junit.Test

/**
 * example test
 */
class ExampleTest {

    @Test
    void testHelloWorld() {
        Example example = new Example()
        assert example.helloWorld() == 'Hello World'
    }

}
