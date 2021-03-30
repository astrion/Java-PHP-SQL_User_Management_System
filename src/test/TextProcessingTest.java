package test;


import TextEdit.TextProcessing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextProcessingObject implements TextProcessing {}

public class TextProcessingTest {

    @Test
    void test_findChange_for_1_addition() {
        TextProcessingObject o = new TextProcessingObject();

        assertEquals(" World!", o.findChange("Hello", "Hello World!"));
        assertEquals("Hello ", o.findChange("World!", "Hello World!"));
        assertEquals(" World", o.findChange("Hello!", "Hello World!"));
    }

    @Test
    void test_findChange_for_1_removal() {
        TextProcessingObject o = new TextProcessingObject();

        assertEquals(" World!", o.findChange("Hello World!", "Hello"));
        assertEquals("Hello ", o.findChange("Hello World!", "World!"));
        assertEquals(" World", o.findChange("Hello World!", "Hello!"));
    }

}
