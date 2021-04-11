package TextEdit;

import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Stack;


public class SmartUndoManager implements TextProcessing {

    public UndoManager undoManager = new UndoManager();  // reuse Swing's UndoManager at the core of any undo/redo operation
    public Stack<LastEdit> lastEdits = new Stack<>();  // use stack for supporting N-step sequential undo operations
    public LastEdit lastEditToRedo = null; // keep a temporary last edit for supporting a 1-step redo operation


    /**
     *  clear lastEdits
     * */
    public void clear() {
        this.undoManager.discardAllEdits();
        this.lastEdits.clear();
    }

    // TODO: review and implement
    /**
     *  clear lastEdits (from Last to N-to-Last edits)
     * */
    /*
    public void clearMultipleNToLastEdits(int steps) {
        String actualLastEdit;
        for (int s = 0; s < steps; s++) {
            if (this.undoManager.canUndo()) {
                this.undoManager;

                actualLastEdit = this.lastEdits.lastElement().toString();
                this.lastEdits.pop();
                System.out.println("DEL EDIT -> " + actualLastEdit);
            }
        }
    }
    */

    /**
     *  push a lastEdit to the stack for the user, add a lastEdit's edit to a Swing's Undo-Manager instance
     * */
    public void addEdit(UndoableEdit edit, String prev, String next) {
        LastEdit lastEdit = new LastEdit(edit, prev, next);
        this.lastEdits.push(lastEdit);  // add LastEdit instances to track edit details
        this.undoManager.addEdit(lastEdit.edit);  // let the undoManager handle
        System.out.println("ADD EDIT ->" + lastEdit.toString());
    }

    /**
     *  calculate N steps for a given elapsed time (in seconds)
     * */
    public int getStepsToUndoInLatestSeconds(double seconds) {
        // find out how many undo steps are expected to undo edits in the latest amount of seconds
        int expectedSteps = 0;
        LocalDateTime now = LocalDateTime.now();
        double timeElapsed;
        for (int s = 0; s < lastEdits.size(); s++) {
            timeElapsed = (double) Duration.between(lastEdits.get(s).dateTime, now).toSeconds();
            if (timeElapsed < seconds)
                expectedSteps++;
        }
        return expectedSteps;
    }

    /**
     *  calculate N steps for a given elapsed time (in minutes)
     * */
    public int getStepsToUndoInLatestMinutes(double minutes) {
        return getStepsToUndoInLatestSeconds(minutes * 60.0);
    }

    /**
     *  calculate N steps for a given elapsed time (in hours)
     * */
    public int getStepsToUndoInLatestHours(double hours) {
        return getStepsToUndoInLatestSeconds(hours * 3600.0);
    }


    /**
     *  apply a 1-step redo (as in any other text editor)
     * */
    public void redo() {
        // redo once
        if (undoManager.canRedo()) {
            //lastEditToRedo = this.lastEdits.get(this.lastEdits.size());
            undoManager.redo();
            lastEdits.push(lastEditToRedo);
            //next = area.getText();
            System.out.println("applied redo to: " + lastEditToRedo.toString());
        } else
            System.out.println("cannot redo");
    }

    /**
     *  apply a 1-step undo (as in any other text editor)
     * */
    public void undo() {
        // remember lastEditToRedo once, in case a 1-step redo operation is ever needed
        lastEditToRedo = this.lastEdits.get(this.lastEdits.size() - 1);
        this.undoMultipleNToLastEdits(1);
    }

    /**
     *  apply a N-step sequential undo (from Last to N-to-Last edits)
     * */
    public void undoMultipleNToLastEdits (int steps) {
        // apply multiple undo operations to N steps sequentially (linear flow, from Last to N-to-Last edits
        String actualLastEdit;
        for (int s = 0; s < steps; s++) {
            if (this.undoManager.canUndo()) {
                this.undoManager.undo();

                actualLastEdit = this.lastEdits.lastElement().toString();
                this.lastEdits.pop();
                System.out.println("DEL EDIT -> " + actualLastEdit);
            }
        }
    }

    // TODO: review and implement
    /*
    public void undoMultipleLastEditsAsSelected (int[] indexes) throws Exception {
        throw new Exception("TODO: not implemented yet");
    }
    */

    // TODO: implement deletion of items in lastEdits
    /*
    public void flush () throws Exception {
        throw new Exception("TODO: not implemented yet");
    }
    */
}
