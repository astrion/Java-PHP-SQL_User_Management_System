package TextEdit;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

class CaretListenerLabel extends JLabel
        implements CaretListener {

    public Integer caretPos = 0;
    public CaretListenerLabel(String label) {
        super(label);
    }

    //Might not be invoked from the event dispatch thread.
    public void caretUpdate(CaretEvent e) {
        displaySelectionInfo(e.getDot(), e.getMark());
    }

    protected void displaySelectionInfo(final int dot,
                                        final int mark) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                if (dot == mark) {  // no selection
                    caretPos = dot;
                }
            }
        });
    }
}