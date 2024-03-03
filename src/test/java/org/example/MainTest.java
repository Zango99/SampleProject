package org.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class MainTest {

    @Test
    public void testMainApp(){
        Main main = new Main();
        String s = main.testApp();
        Assertions.assertEquals(s, "This is sample app");
    }

}
