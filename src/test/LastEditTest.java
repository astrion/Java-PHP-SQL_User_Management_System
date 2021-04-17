package test;

import TextEdit.LastEdit;
import org.junit.jupiter.api.Test;
import javax.swing.undo.UndoableEdit;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static test.SmartUndoManagerTest.getTestUndoableEdit;


public class LastEditTest {

    /**
     * test attributes
     * */
    @Test
    void test_SecondaryUIComponents_attributes() {

        LastEdit lastedit = new LastEdit(getTestUndoableEdit(true, true), "before1", "after1");
        assertTrue(lastedit.dateTime instanceof LocalDateTime);
        assertTrue(lastedit.difference instanceof String);
        assertTrue(lastedit.edit instanceof UndoableEdit);
    }
}
