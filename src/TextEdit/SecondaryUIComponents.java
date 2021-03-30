package TextEdit;

import javax.swing.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

public interface SecondaryUIComponents {

    default public String getUserInput(JFrame parentFrame, String caption, String initialSelectionValue) {
        // display a simple input dialog
        String input = JOptionPane.showInputDialog(parentFrame,
                caption, initialSelectionValue);
        return input;
    }

    default public void displayLastEdits(Stack lastEdits) {
        // display a view of last edits
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                ViewOfLastEdits.title = EnumWindowCaption.VIEW_OF_LAST_EDITS.caption + " at " + now;
                ViewOfLastEdits.columnNames = new String[] {
                        "#",
                        "Timestamp",
                        "HashCode",
                        "EditType",
                        "Difference"
                };
                ViewOfLastEdits.lastEdits = lastEdits;
                ViewOfLastEdits view = new ViewOfLastEdits();
                view.createAndDisplay();
            }
        });
    }

}
