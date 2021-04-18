package TextEdit;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;


public class AreaProcessor {

    public static void LineUpdates(JTextArea area, List<List<String>> undoManager) {
        String[] lines = area.getText().split("\n");

        for (int i = 0; i < lines.length; i++) {
            // line is new
            if (undoManager.isEmpty() || undoManager.size() <= i) {
                List<String> currLsit = new ArrayList<String>();
                currLsit.add(lines[i]);
                undoManager.add(currLsit);
            }
            //if line exists
            else {
                List<String> currLineInUM = undoManager.get(i);
                String currState = currLineInUM.get(currLineInUM.size() - 1);
                if (!currState.equals(lines[i])) {
                    currLineInUM.add(lines[i]);
                }
            }
        }
    }

    public static void Print(List<List<String>> undoManager) {
        System.out.println("#AreaProcessor.Print");
        //printing
        for (int i = 0; i < undoManager.size(); i++) {

            System.out.print("Line: " + (i + 1) + " | ");
            List<String> currLineinUM = undoManager.get(i);
            for (int j = currLineinUM.size()-1; j >=0; --j) {
                System.out.print(" state :" + j + " " + currLineinUM.get(j));
            }
            System.out.println();
        }
    }
}






