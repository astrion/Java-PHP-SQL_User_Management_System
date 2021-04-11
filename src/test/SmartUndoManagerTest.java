package test;

import TextEdit.SmartUndoManager;
import org.junit.jupiter.api.Test;

import javax.swing.undo.UndoManager;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SmartUndoManagerTest {

    @Test
    void test_SmartUndoManager_default_attributes() {

        SmartUndoManager smartUndoManager = new SmartUndoManager();

        assertTrue(smartUndoManager.undoManager instanceof UndoManager);
        assertEquals(100, smartUndoManager.undoManager.getLimit());

        assertTrue(smartUndoManager.lastEdits instanceof Stack);
        assertEquals(0, smartUndoManager.lastEdits.size());

        //assertTrue(smartUndoManager.lastEditToRedo instanceof LastEdit);
        assertEquals(null, smartUndoManager.lastEditToRedo);
    }

}
