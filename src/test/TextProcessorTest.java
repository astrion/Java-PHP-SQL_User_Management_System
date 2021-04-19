package test;

import TextEdit.TextProcessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextProcessorTest {

    @Test
    void test_TextProcessor_TextProcessor() {
        TextProcessor textProcessor = new TextProcessor();
        assertTrue(textProcessor != null);
    }

}
