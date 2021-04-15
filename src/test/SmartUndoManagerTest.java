package test;

import TextEdit.LastEdit;
import TextEdit.SmartUndoManager;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;

public class SmartUndoManagerTest {


    private static UndoableEdit getTestUndoableEdit(boolean canRedo, boolean canUndo) {
        return new UndoableEdit() {
            @Override
            public void undo() throws CannotUndoException {

            }

            @Override
            public boolean canUndo() {
                return canUndo;
            }

            @Override
            public void redo() throws CannotRedoException {

            }

            @Override
            public boolean canRedo() {
                return canRedo;
            }

            @Override
            public void die() {

            }

            @Override
            public boolean addEdit(UndoableEdit anEdit) {
                return false;
            }

            @Override
            public boolean replaceEdit(UndoableEdit anEdit) {
                return false;
            }

            @Override
            public boolean isSignificant() {
                return false;
            }

            @Override
            public String getPresentationName() {
                return null;
            }

            @Override
            public String getUndoPresentationName() {
                return null;
            }

            @Override
            public String getRedoPresentationName() {
                return null;
            }
        };
    }

    /**
     * test attributes and their default values
     * */
    @Test
    void test_SmartUndoManager_attributes_and_their_default_values() {

        SmartUndoManager smartUndoManager = new SmartUndoManager();

        // test undoManager
        assertTrue(smartUndoManager.undoManager != null);
        assertEquals(100, smartUndoManager.undoManager.getLimit());

        // test lastEdits
        assertTrue(smartUndoManager.lastEdits instanceof Stack);
        assertEquals(0, smartUndoManager.lastEdits.size());

        // test lastEditToRedo
        assertEquals(null, smartUndoManager.lastEditToRedo);
    }

    /**
     * test clear()
     * */
    @Test
    void test_SmartUndoManager_clear() {

        SmartUndoManager smartUndoManager = new SmartUndoManager();

        // add 3 test (fake) edits
        smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before1", "after1");
        smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before2", "after2");
        smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before3", "after3");
        assertEquals(3, smartUndoManager.lastEdits.size());

        // clear
        smartUndoManager.clear();

        // assert clear took place
        assertEquals(0, smartUndoManager.lastEdits.size());

    }

    /**
     * test addEdit()
     * */
    @Test
    void test_SmartUndoManager_addEdit() {
        SmartUndoManager smartUndoManager = new SmartUndoManager();
        assertEquals(0, smartUndoManager.lastEdits.size());

        // assert 3 test (fake) edits get added
        int actualNumberOfTestEdits = 3;
        for (int i = 0; i < actualNumberOfTestEdits; i++) {
            smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before" + i, "after" + i);
            assertEquals(i + 1, smartUndoManager.lastEdits.size());
        }
    }

    /**
     * test getStepsToUndoInLatestSeconds()
     * */
    @Test
    void test_SmartUndoManager_getStepsToUndoInLatestSeconds() {
        SmartUndoManager smartUndoManager = new SmartUndoManager();
        assertEquals(0, smartUndoManager.lastEdits.size());

        // assert N test (fake) edits got added
        int expectedNumberOfTestEdits = 3;
        for (int i = 0; i < expectedNumberOfTestEdits; i++)
            smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before" + i, "after" + i);
        assertEquals(expectedNumberOfTestEdits, smartUndoManager.lastEdits.size());

        // assert N last test edits are counted in an elapsed-time search for the last 10 seconds (units in seconds)
        int actualNumberOfTestEdits = smartUndoManager.getStepsToUndoInLatestSeconds(10); // 10 s
        assertEquals(expectedNumberOfTestEdits, actualNumberOfTestEdits);

    }

    /**
     * test getStepsToUndoInLatestMinutes()
     * */
    @Test
    void test_SmartUndoManager_getStepsToUndoInLatestMinutes() {
        SmartUndoManager smartUndoManager = new SmartUndoManager();
        assertEquals(0, smartUndoManager.lastEdits.size());

        // assert N test (fake) edits got added
        int expectedNumberOfTestEdits = 3;
        for (int i = 0; i < expectedNumberOfTestEdits; i++)
            smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before" + i, "after" + i);
        assertEquals(expectedNumberOfTestEdits, smartUndoManager.lastEdits.size());

        // assert N last test edits are counted in an elapsed-time search for the last 10 seconds (units in minutes)
        int actualNumberOfTestEdits = smartUndoManager.getStepsToUndoInLatestMinutes(0.02); // 10 s ~ 10/60 min
        assertEquals(expectedNumberOfTestEdits, actualNumberOfTestEdits);
    }

    /**
     * test getStepsToUndoInLatestHours()
     * */
    @Test
    void test_SmartUndoManager_getStepsToUndoInLatestHours() {
        SmartUndoManager smartUndoManager = new SmartUndoManager();
        assertEquals(0, smartUndoManager.lastEdits.size());

        // assert N test (fake) edits got added
        int expectedNumberOfTestEdits = 3;
        for (int i = 0; i < expectedNumberOfTestEdits; i++)
            smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before" + i, "after" + i);
        assertEquals(expectedNumberOfTestEdits, smartUndoManager.lastEdits.size());

        // assert N last test edits are counted in an elapsed-time search for the last 10 seconds (units in hours)
        int actualNumberOfTestEdits = smartUndoManager.getStepsToUndoInLatestHours(0.0002); // 10 s ~ 10/3600 h
        assertEquals(expectedNumberOfTestEdits, actualNumberOfTestEdits);
    }

    /**
     * test undoMultipleNToLastEdits()
     * */
    @Disabled("Disable this test until all UndoableEdit objects are real or properly mocked")
    @Test
    void test_SmartUndoManager_undoMultipleNToLastEdits() {
        SmartUndoManager smartUndoManager = new SmartUndoManager();
        assertEquals(0, smartUndoManager.lastEdits.size());

        // assert N test (fake) edits got added
        int actualNumberOfTestEdits = 3;
        for (int i = 0; i < actualNumberOfTestEdits; i++)
            smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before" + i, "after" + i);
        assertEquals(actualNumberOfTestEdits, smartUndoManager.lastEdits.size());

        // assert those N test edits got removed as well
        smartUndoManager.undoMultipleNToLastEdits(actualNumberOfTestEdits);
        assertEquals(0, smartUndoManager.lastEdits.size());
    }

    @Test
    void test_SmartUndoManager_undo() {
        SmartUndoManager smartUndoManager = new SmartUndoManager();
        assertEquals(0, smartUndoManager.lastEdits.size());

        // add 1 edit
        smartUndoManager.addEdit(getTestUndoableEdit(true, true), "before", "after");

    }

}
