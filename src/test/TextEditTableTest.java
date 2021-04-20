package test;

import TextEdit.TextEditTable;
import TextEdit.TextProcessor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.table.DefaultTableModel;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TextEditTableTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void test_TextEditTable_TextEditTable(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Object[][] rowData = new Object[i][j];
                TextEditTable textEditTable = new TextEditTable(rowData);
                assertTrue(textEditTable != null);
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void test_TextEditTable_columnClass(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Object[][] rowData = new Object[i][j];
                TextEditTable textEditTable = new TextEditTable(rowData);

                Class<?> actualColumnClass = textEditTable.getColumnClass(n);
                switch (n) {
                    case 0:
                        assertEquals(Integer.class, actualColumnClass);
                        break;
                    case 1:
                        assertEquals(Integer.class, actualColumnClass);
                        break;
                    case 3:
                        assertEquals(Boolean.class, actualColumnClass);
                        break;
                    default:
                        assertEquals(String.class, actualColumnClass);

                }
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void test_TextEditTable_getModel(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Object[][] rowData = new Object[i][j];
                TextEditTable textEditTable = new TextEditTable(rowData);

                assertAll(() -> textEditTable.getModel());
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void test_TextEditTable_newColumns(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Object[][] rowData = new Object[i][j];
                TextEditTable textEditTable = new TextEditTable(rowData);

                final int index = n; // Note: this is unused in newColumns(), index is kept in this test for exploring method behaviour
                final int line = i;
                final int state = j;
                assertAll(() -> textEditTable.newColumns(n, "some text here...", line, state));
            }
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void test_TextEditTable_addRow(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Object[][] rowData = new Object[i][j];
                TextEditTable textEditTable = new TextEditTable(rowData);

                final int line = i;
                final int state = j;
                assertAll(() -> textEditTable.addRow("some text here...", line, state));
            }
        }
    }


    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void test_TextEditTable_refresh(int n) {
        var editHistory = List.of(
                List.of("Hi!"),
                List.of("This", "is", "some", "input"),
                List.of("This", "is", "some", "other", "input"),
                List.of("Bye!")
        );

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Object[][] rowData = new Object[i][j];
                TextEditTable textEditTable = new TextEditTable(rowData);

                assertAll(() -> textEditTable.Refresh(editHistory));
            }
        }
    }

}
