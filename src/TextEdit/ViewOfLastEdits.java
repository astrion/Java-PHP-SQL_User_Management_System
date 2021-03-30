package TextEdit;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

public class ViewOfLastEdits extends JPanel {

    public static String title;
    public static String[] columnNames;
    public static Stack lastEdits;

    private static Object[][] getArrayFromStack(String[] colNames, Stack dataStack) {
        Object[][] dataArray = new String[dataStack.size()][colNames.length];
        LastEdit lastEdit;
        for (int i = 0; i < dataStack.size(); i++){
            lastEdit = (LastEdit) dataStack.get(i);
            dataArray[i][0] = "" + i;
            dataArray[i][1] = lastEdit.dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            dataArray[i][2] = "" + lastEdit.edit.hashCode();
            dataArray[i][3] = lastEdit.edit.getPresentationName();
            dataArray[i][4] = lastEdit.difference;
        }
        return dataArray;
    }

    public ViewOfLastEdits() {
        super(new GridLayout(1, 0));

        Object[][] data = getArrayFromStack(columnNames, lastEdits);

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        // create a scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        // ass scrollPane to this panel
        add(scrollPane);
    }

    public static void createAndDisplay() {
        // create and set up a window
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // create and set up a content pane
        ViewOfLastEdits newContentPane = new ViewOfLastEdits();
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);

        // display the window
        frame.pack();
        frame.setVisible(true);
    }

}
