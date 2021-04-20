package test;

import TextEdit.TextEditTable;
import TextEdit.TextProcessor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TextProcessorTest {

    TextProcessor textProcessor = new TextProcessor();

    static JTextArea getTestJTextArea() {
        return new JTextArea();
    }

    static List<List<String>> getTestEditHistory() {
        List<List<String>> testEditHistory = new ArrayList<>();
        testEditHistory.add(Arrays.asList("Hi!"));
        testEditHistory.add(Arrays.asList("This", "is", "some", "input"));
        testEditHistory.add(Arrays.asList("This", "is", "some", "other", "input"));
        testEditHistory.add(Arrays.asList("Bye!"));
        return testEditHistory;
    }

    static TextEditTable getTestTextEditTable() {
        Object[][] rowData = new Object[0][];
        return new TextEditTable(rowData);
    }

    @Test
    void test_TextProcessor_TextProcessor() {
        TextProcessor textProcessor = new TextProcessor();
        assertTrue(textProcessor != null);
    }

    @Test
    void test_TextProcessor_LineUpdates() {
        assertAll(() -> TextProcessor.LineUpdates(getTestJTextArea(), getTestEditHistory()));
    }

    @Test
    void test_TextProcessor_Print() {
        assertAll(() -> textProcessor.Print(getTestEditHistory()));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1}) // Note: too complex too analyze by data flow alone (i.e. more detailed testing would be needed)
    void test_TextEditTable_Undo(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Object[][] rowData = new Object[i][j];
                TextEditTable textEditTable = new TextEditTable(rowData);

                assertAll(() -> textProcessor.Undo(getTestEditHistory(), textEditTable, getTestJTextArea()));
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void test_TextEditTable_Forget(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Object[][] rowData = new Object[i][j];
                TextEditTable textEditTable = new TextEditTable(rowData);

                assertAll(() -> textProcessor.Forget(getTestEditHistory(), textEditTable));
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void test_TextEditTable_SelectLatest(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Object[][] rowData = new Object[i][j];
                TextEditTable textEditTable = new TextEditTable(rowData);

                assertAll(() -> textProcessor.SelectLatest(textEditTable));
            }
        }
    }

    @Test
    void test_TextProcessor_SelectOldest_old() {
        Object[][] rowData = new Object[0][0];
        TextEditTable textEditTable = new TextEditTable(rowData);

        assertAll(() -> textProcessor.SelectOldest(textEditTable));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void test_TextEditTable_SelectOldest(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Object[][] rowData = new Object[i][j];
                TextEditTable textEditTable = new TextEditTable(rowData);

                assertAll(() -> textProcessor.SelectOldest(textEditTable));
            }
        }
    }
}
