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
        DefaultTableModel model = dataTable.getModel();
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
            Object[] lineStatePair = pairList.get(i);
            int lineNum = (int) lineStatePair[0];
            int stateNum = (int) lineStatePair[1];
            int latestState = (int) historyManager.get(lineNum).size() - 1;
            for (int j = latestState; j > stateNum; --j) {
                historyManager.get(lineNum).remove(j);
            }
            //decrease line number
            if ((stateNum == 0) && historyManager.get(lineNum).get(stateNum).equals("") && lineNum>0) {
                historyManager.remove(lineNum);
            }
        }

        // update JTextarea with the lines with the latest texts
        ArrayList<String> s = new ArrayList<>();
        for (int i = 0; i < historyManager.size(); i++) {
            s.add(historyManager.get(i).get(historyManager.get(i).size() - 1));
        }

        String[] lineSeparateArray = s.toArray(new String[s.size()]);
        String joined = String.join("\n", lineSeparateArray);
        area.setText(joined);

        //data refresh
        dataTable.Refresh(historyManager);
    }

    public static void Forget(List<List<String>> historyManager, TextEditTable dataTable) {

        // insert/extract the selection information into ArrayList
        ArrayList<Object[]> pairList = new ArrayList<Object[]>();
        DefaultTableModel model = dataTable.getModel();
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
            Object[] lineStatePair = pairList.get(i);
            int lineNum = (int) lineStatePair[0];
            int stateNum = (int) lineStatePair[1];
            int oldestState = 0;

            for (int j = oldestState; j < stateNum; j++) {
                historyManager.get(lineNum).remove(0);
            }
            // decrease state number

        }
        //data refresh
        dataTable.Refresh(historyManager);
    }

    public static void SelectLatest(TextEditTable dataTable) {
        DefaultTableModel model = dataTable.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            dataTable.setValueAt(true, i, Column.Select.Index());
        }
    }

    public static void SelectOldest(TextEditTable dataTable) {
        DefaultTableModel model = dataTable.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; --i) {
            dataTable.setValueAt(true, i, Column.Select.Index());
        }
    }
}

