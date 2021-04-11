package TextEdit;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

public interface SecondaryUIComponents {

    // static methods
    static JTable getLastEditTable(Stack dataStack) {

        // set column names
        String[] colNames = new String[] {
                "#",
                "Timestamp",
                "HashCode",
                "EditType",
                "Difference",
                "Set"
        };

        // set rows
        int rowCount = dataStack == null ? 0 : dataStack.size();
        Object[][] dataArray = new Object[rowCount][colNames.length];
        LastEdit lastEdit;
        for (int i = 0; i < rowCount; i++){
            lastEdit = (LastEdit) dataStack.get(i);
            dataArray[i][0] = "" + i;
            dataArray[i][1] = lastEdit.dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            dataArray[i][2] = "" + lastEdit.edit.hashCode();
            dataArray[i][3] = lastEdit.edit.getPresentationName();
            dataArray[i][4] = lastEdit.difference;
            dataArray[i][5] = false;
        }

        return new JTable(dataArray, colNames){
            @Override
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 5:
                        return Boolean.class;
                    default:
                        return String.class;
                }
            }
        };
    }

    static JFrame getLastEditView() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        return frame;
    }

    // final attributes

    JFrame lastEditView = getLastEditView();

    // default methods

    default public String getUserInput(JFrame parentFrame, String caption, String initialSelectionValue) {
        // display a simple input dialog
        String input = JOptionPane.showInputDialog(parentFrame,
                caption, initialSelectionValue);
        return input;
    }

    default void updateLastEditView(Stack lastEdits) {

        // update title
        String now = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String title = EnumWindowCaption.VIEW_OF_LAST_EDITS.caption + " at " + now;

        // get table, add table to a scrollPane, and add the scrollPane to a panel
        JTable table = getLastEditTable(lastEdits);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        JPanel panel = new JPanel(new GridLayout(1, 0));
        panel.add(scrollPane);
        panel.setOpaque(true);

        // update LastEditView by adding the panel to it
        lastEditView.setTitle(title);
        lastEditView.setContentPane(panel);
        lastEditView.pack();
        lastEditView.repaint();
    }

    default void updateLastEditView(Stack lastEdits, boolean isVisible) {
        // update lastEditView and change its visibility
        updateLastEditView(lastEdits);
        lastEditView.setVisible(isVisible);
    }

    default String[] columnNames(){
        return new String[]{
                "#",
                "Timestamp",
                "HashCode",
                "EditType",
                "Difference",
                "Set"
        };
    };
}
