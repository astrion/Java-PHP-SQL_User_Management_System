package test;


import TextEdit.TextProcessing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextProcessingObject implements TextProcessing {}

public class TextProcessingTest {

    @Test
    void test_findChange_for_1_addition() {
        TextProcessingObject o = new TextProcessingObject();

        assertEquals(" World!", o.findDifference("Hello", "Hello World!"));
        assertEquals("Hello ", o.findDifference("World!", "Hello World!"));
        assertEquals(" World", o.findDifference("Hello!", "Hello World!"));
    }

    @Test
    void test_findChange_for_1_removal() {
        TextProcessingObject o = new TextProcessingObject();

        assertEquals(" World!", o.findDifference("Hello World!", "Hello"));
        assertEquals("Hello ", o.findDifference("Hello World!", "World!"));
        assertEquals(" World", o.findDifference("Hello World!", "Hello!"));
    }

}
