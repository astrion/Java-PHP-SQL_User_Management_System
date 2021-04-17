package test;


import TextEdit.SecondaryUIComponents;
import TextEdit.SmartUndoManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static TextEdit.SecondaryUIComponents.lastEditView;
import static org.junit.jupiter.api.Assertions.*;
import static test.SmartUndoManagerTest.getTestUndoableEdit;

class SecondaryUIComponentObject implements SecondaryUIComponents {}

public class SecondaryUIComponentsTest {

    /**
     * test getLastEditTable() statically
     */
    @Test
    void test_SecondaryUIComponents_getLastEditTable() {
        SmartUndoManager smartUndoManager = new SmartUndoManager();

        // assert an empty table
        JTable emptyTable = SecondaryUIComponents.getLastEditTable(smartUndoManager.lastEdits);
        assertEquals("#", emptyTable.getColumnName(0));
        assertEquals("Timestamp", emptyTable.getColumnName(1));
        assertEquals("HashCode", emptyTable.getColumnName(2));
        assertEquals("EditType", emptyTable.getColumnName(3));
        assertEquals("Difference", emptyTable.getColumnName(4));
        assertEquals("Set", emptyTable.getColumnName(5));
        assertEquals(0, emptyTable.getRowCount());

        // add 3 test (fake) edits to smartUndoManager
        smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before1", "after1");
        smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before2", "after2");
        smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before3", "after3");
        assertEquals(3, smartUndoManager.lastEdits.size());

        // assert a non-empty table
        JTable nonemptyTable = SecondaryUIComponents.getLastEditTable(smartUndoManager.lastEdits);
        assertEquals(3, nonemptyTable.getRowCount());
    }

    /**
     * test getLastEditView() statically
     */
    @Test
    void test_SecondaryUIComponents_getLastEditView() {
        assertTrue(SecondaryUIComponents.getLastEditView() instanceof JFrame);
        assertTrue(SecondaryUIComponents.getLastEditView() != null);
    }

    /**
     * test attributes and their default values
     * */
    @Test
    void test_SecondaryUIComponents_attributes() {
        SecondaryUIComponentObject secondaryUIComponentObject = new SecondaryUIComponentObject();
        assertTrue(lastEditView instanceof JFrame);
        assertNotNull(lastEditView);
    }

    /**
     * test getUserInput() manually
     */
    @Test
    @Disabled("Enable this test if a manual test is executed or an automated test framework gets added")
    void test_SecondaryUIComponents_getUserInput() {
        SecondaryUIComponentObject secondaryUIComponentObject = new SecondaryUIComponentObject();
        JFrame lastEditView = SecondaryUIComponents.lastEditView;
        String caption = "Running manually test_SecondaryUIComponents_getUserInput(), please enter any input below:";
        String initialSelectionValue = "0";
        String userInput = secondaryUIComponentObject.getUserInput(lastEditView, caption, initialSelectionValue);
        assertTrue(userInput != null);
    }

    /**
     * test updateLastEditView()
     */
    @Test
    void test_SecondaryUIComponents_updateLastEditView() throws InterruptedException {
        SecondaryUIComponentObject secondaryUIComponentObject = new SecondaryUIComponentObject();
        SmartUndoManager smartUndoManager = new SmartUndoManager();

        // assert an update in a 0-second period (check timestamp in title)
        LocalDateTime start1 = LocalDateTime.now();
        secondaryUIComponentObject.updateLastEditView(smartUndoManager.lastEdits);
        LocalDateTime updateTime1 = LocalDateTime.parse(
                secondaryUIComponentObject.lastEditView.getTitle().replace("View of last edits at ", ""),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        long elapsedTimeInSeconds1 = Duration.between(start1, updateTime1).toSeconds();
        assertTrue(elapsedTimeInSeconds1 <= 0);

        // assert an update in a 5-second period (wait 5 seconds, add 3 test (fake) edits to smartUndoManager, check timestamp in title)
        LocalDateTime start2 = LocalDateTime.now();
        Thread.sleep(5000);  // wait 5 s = 5000 ms
        smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before1", "after1");
        smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before2", "after2");
        smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before3", "after3");
        assertEquals(3, smartUndoManager.lastEdits.size());

        // assert 2nd update
        secondaryUIComponentObject.updateLastEditView(smartUndoManager.lastEdits);
        LocalDateTime updateTime2 = LocalDateTime.parse(
                secondaryUIComponentObject.lastEditView.getTitle().replace("View of last edits at ", ""),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        long elapsedTimeInSeconds2 = Duration.between(start2, updateTime2).toSeconds();
        assertTrue(elapsedTimeInSeconds2 >= 5);
    }

    /**
     * test updateLastEditView(Stack lastEdits, boolean isVisible) manually
     */
    @Test
    void test_SecondaryUIComponents_updateLastEditView2() {

        // assert lastEditView is not visible by default
        SecondaryUIComponentObject secondaryUIComponentObject = new SecondaryUIComponentObject();
        assertFalse(secondaryUIComponentObject.lastEditView.isVisible());

        // assert lastEditView gets updated and becomes visible
        SmartUndoManager smartUndoManager = new SmartUndoManager();
        secondaryUIComponentObject.updateLastEditView(smartUndoManager.lastEdits, true);
        assertTrue(secondaryUIComponentObject.lastEditView.isVisible());

        // assert lastEditView gets updated and becomes not visible
        secondaryUIComponentObject.updateLastEditView(smartUndoManager.lastEdits, false);
        assertFalse(secondaryUIComponentObject.lastEditView.isVisible());
    }



}
