package TextEdit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

// Processing Texts
public class TextProcessor {

    // Update historyManager data structure from the provided text
    public static void LineUpdates(JTextArea area, List<List<String>> historyManager) {
        // Split the provided text using new line character
        String[] lines = area.getText().split("\n");

        // Process line by line
        for (int i = 0; i < lines.length; i++) {
            // if line is new
            if (historyManager.isEmpty() || historyManager.size() <= i) {
                List<String> currList = new ArrayList<String>();
                // add blank state when new line is not blank to set an initial state to undo
                if (!lines[i].equals(""))
                    currList.add("");
                currList.add(lines[i]);
                // Append line into the structure
                historyManager.add(currList);
            }
            // if line is already there
            else {
                List<String> currLineInUM = historyManager.get(i);
                // Set current state using latest state
                String currState = currLineInUM.get(currLineInUM.size() - 1);
                // Add new line only when line is updated
                if (!currState.equals(lines[i])) {
                    currLineInUM.add(lines[i]);
                }
            }
        }
    }

    // Print data structure
    public static void Print(List<List<String>> historyManager) {
        System.out.println("#AreaProcessor.Print");
        for (int i = 0; i < historyManager.size(); i++) {
            System.out.print("Line: " + (i + 1) + " | ");
            List<String> currLineinUM = historyManager.get(i);
            for (int j = currLineinUM.size() - 1; j >= 0; --j) {
                System.out.print(" state :" + j + " " + currLineinUM.get(j));
            }
            System.out.println();
        }
    }

    // Perform Undo
    public static void Undo(List<List<String>> historyManager, TextEditTable dataTable, JTextArea area) {

        // insert/extract the selection information into ArrayList
        ArrayList<Object[]> pairList = new ArrayList<Object[]>();
        // obtain a model of the table to access its entries
        DefaultTableModel model = dataTable.getModel();
        // iterate through entries of the table from the beginning to the end
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, Column.Select.Index()).equals(true)) {
                // line number, state number, row number to delete
                pairList.add(new Object[]{
                        model.getValueAt(i, Column.Line.Index()),
                        model.getValueAt(i, Column.State.Index()),
                        (Integer) i
                });
            }
        }


        // delete unnecessary entries
        for (int i = pairList.size() - 1; i >= 0; --i) {
            // line state pair is used as a container to store delete target line, state, row number (unused) info
            Object[] lineStatePair = pairList.get(i);
            int lineNum = (int) lineStatePair[0];
            int stateNum = (int) lineStatePair[1];
            // obtain the latest state within the given line number
            int latestState = (int) historyManager.get(lineNum).size() - 1;
            // from the latest state, it loops through the states until it reach the target state
            // thus, all newer states are deleted from the data structure
            for (int j = latestState; j > stateNum; --j) {
                historyManager.get(lineNum).remove(j);
            }
            // when (1)the state number is 0  (2)the line has a blank state (3)the line number is bigger than 0
            // then we remove the line so that there is no gap among lines  (eg. line 0,2,3 -> line 0,1,2)
            if ((stateNum == 0) && historyManager.get(lineNum).get(stateNum).equals("") && lineNum > 0) {
                historyManager.remove(lineNum);
            }
        }

        // update JTextarea with the lines with the latest texts
        ArrayList<String> tempStringArray = new ArrayList<>();
        // loop through the data structure to update JTextArea
        for (int i = 0; i < historyManager.size(); i++) {
            // tempStringArray has following structure (eg. size[2][1]: ["apple"]["banana"])
            tempStringArray.add(historyManager.get(i).get(historyManager.get(i).size() - 1));
        }
        // convert the above to (eg. size[2]: ["apple","banana"])
        String[] lineSeparateArray = tempStringArray.toArray(new String[tempStringArray.size()]);
        // convert the above to (eg. "apple\nbanana")
        String joined = String.join("\n", lineSeparateArray);
        area.setText(joined);

        //data refresh
        dataTable.Refresh(historyManager);
    }


    // perform a forget function
    public static void Forget(List<List<String>> historyManager, TextEditTable dataTable) {

        // insert/extract the selection information into ArrayList
        ArrayList<Object[]> pairList = new ArrayList<Object[]>();
        // obtain a model of the table to access its entries
        DefaultTableModel model = dataTable.getModel();

        // iterate through entries of the table from the beginning to the end
        for (int i = 0; i < model.getRowCount(); i++) {
            // if the current row is selected then
            if (model.getValueAt(i, Column.Select.Index()).equals(true)) {
                // obtain line number, state number, row number to delete
                pairList.add(new Object[]{
                        model.getValueAt(i, Column.Line.Index()),
                        model.getValueAt(i, Column.State.Index()),
                        (Integer) i
                });
            }
        }

        // delete unnecessary entries
        // iteration starts from the latest delete items - this can be start from 0, but it happened to be this way as
        // this section of code is re-used from undo part.
        for (int i = pairList.size() - 1; i >= 0; --i) {
            Object[] lineStatePair = pairList.get(i);
            int lineNum = (int) lineStatePair[0];
            int stateNum = (int) lineStatePair[1];
            int oldestState = 0;
            // starting from the oldest state, it deletes record one by one to the target state. It stops before the
            // target entry so that the current selection history is preserved
            for (int j = oldestState; j < stateNum; j++) {
                historyManager.get(lineNum).remove(0);
            }
        }
        //data refresh
        dataTable.Refresh(historyManager);
    }

    // select latest entries of radio buttons so that forget function can use the selections
    // a small trick is used here. Due to selection implementation, all the non-selected entries in the same line
    // is deselected. Thus, selecting from the state 0 to the state n will only leave the selection of state n.
    // using this trick, the software can obtain the latest selections for each lines. (eg. line 0 state n, line1 state n)
    public static void SelectLatest(TextEditTable dataTable) {
        // obtain a model of the table to access its entries
        DefaultTableModel model = dataTable.getModel();
        // starting from the first entry, it approaches to
        for (int i = 0; i < model.getRowCount(); i++) {
            dataTable.setValueAt(true, i, Column.Select.Index());
        }
    }

    // select oldest entries of radio buttons so that undo function can use the selections
    // a small trick is used here. Due to selection implementation, all the non-selected entries in the same line
    // is deselected. Thus, selecting from the state n to the state 0 will only leave the selection of state 0.
    // using this trick, the software can obtain the oldest selections for each lines. (eg. line0 state0, line1 state 0)
    public static void SelectOldest(TextEditTable dataTable) {
        // obtain a model of the table to access its entries
        DefaultTableModel model = dataTable.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; --i) {
            dataTable.setValueAt(true, i, Column.Select.Index());
        }
    }
}

