package test;

import TextEdit.TextEditTable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javax.swing.table.DefaultTableModel;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

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
    void test_TextEditTable_getModel(int n) {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Object[][] rowData = new Object[i][j];
                TextEditTable textEditTable = new TextEditTable(rowData);

                boolean isMethodExecutedWithoutInterruptions;
                try {
                    DefaultTableModel defaultTableModel = textEditTable.getModel();

                    isMethodExecutedWithoutInterruptions = true;
                } catch (Exception e) {
                    isMethodExecutedWithoutInterruptions = false;
                }
                assertTrue(isMethodExecutedWithoutInterruptions);
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

                boolean isMethodExecutedWithoutInterruptions;
                try {
                    textEditTable.Refresh(editHistory);

                    isMethodExecutedWithoutInterruptions = true;
                } catch (Exception e) {
                    isMethodExecutedWithoutInterruptions = false;
                }
                assertTrue(isMethodExecutedWithoutInterruptions);
            }
        }
    }

}
