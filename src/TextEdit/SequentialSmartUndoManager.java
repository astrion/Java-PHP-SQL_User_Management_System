package TextEdit;

import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Stack;


public class SequentialSmartUndoManager implements TextProcessing {

    public UndoManager undoManager = new UndoManager();  // use 1 instance of Swing's UndoManager
    public Stack<LastEdit> lastEdits = new Stack<>();  // use stack for sequential undo operations

    public void addEdit(UndoableEdit edit, String prev, String next) {
        LastEdit lastEdit = new LastEdit(edit, prev, next);
        this.lastEdits.push(lastEdit);  // add LastEdit instances to track edit details
        this.undoManager.addEdit(lastEdit.edit);  // let the undoManager handle
        System.out.println("ADD EDIT ->" + lastEdit.toString());
    }

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

    public int getStepsToUndoInLatestMinutes(double minutes) {
        return getStepsToUndoInLatestSeconds(minutes * 60.0);
    }

    public int getStepsToUndoInLatestHours(double hours) {
        return getStepsToUndoInLatestSeconds(hours * 3600.0);
    }

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
