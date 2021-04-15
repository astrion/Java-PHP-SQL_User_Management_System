package TextEdit;

import javax.swing.undo.UndoableEdit;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class LastEdit implements TextProcessing {

    public LocalDateTime dateTime;
    public String difference;
    public int index; // position in previous string
    public UndoableEdit edit;

    public LastEdit(UndoableEdit edit, String prev, String next) {
        this.dateTime = LocalDateTime.now(); //.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.difference = findDifference(prev, next);
        this.edit = edit;  // edit contains hashCode(), getPresentationName(), etc.
        // NOTE: add more fields as needed, as long as Swing does not provide them
        //setDifferenceAndIndex(prev, next);
    }

    public String toString() {
        return " timestamp:" + this.dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) +
                " diff: " + this.difference +
                " hashCode:" + this.edit.hashCode() +
                " presentationName"  + this.edit.getPresentationName() +
                " r:" + this.edit.getRedoPresentationName() +
                " u:" + this.edit.getUndoPresentationName();
    }
}
