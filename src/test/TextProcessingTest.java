package test;


import TextEdit.TextProcessing;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextProcessingObject implements TextProcessing {}

public class TextProcessingTest {

    /**
     * assert 1 difference is returned after 1 sub-string addition
     * */
    @Test
    void test_findChange_for_1_addition() {
        TextProcessingObject o = new TextProcessingObject();

        assertEquals(" World!", o.findDifference("Hello", "Hello World!")); // added suffix
        assertEquals("Hello ", o.findDifference("World!", "Hello World!")); // added prefix
        assertEquals(" World", o.findDifference("Hello!", "Hello World!")); // added mid-string
    }

    /**
     * assert 1 difference is returned after an 1 sub-string removal
     * */
    @Test
    void test_findChange_for_1_removal() {
        TextProcessingObject o = new TextProcessingObject();

        assertEquals(" World!", o.findDifference("Hello World!", "Hello")); // removed suffix
        assertEquals("Hello ", o.findDifference("Hello World!", "World!")); // removed prefix
        assertEquals(" World", o.findDifference("Hello World!", "Hello!")); // removed mid-string
    }

}
