package TextEdit;

import javax.swing.*;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.ArrayList;

public class LineProceesor {

    JTextArea area_;
    int delay_;
    //List<undoStack> undoManager = new ArrayList<undoStack>();

    int start = 0;
    int lineNo = 0;


    //constructor
    public LineProceesor(JTextArea area, List<undoStack> undoManager) {
      String text = area.getText();

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == 10) {
                String currLine = (text.substring(start, i+1));
                //System.out.println("currLine " + currLine);
                //System.out.println("start " + start + "| i " + i);
                //System.out.println("size UM " + undoManager.size());
                //System.out.println("lineNo " + lineNo);
                if (undoManager.isEmpty() || undoManager.size() <= lineNo ) {
                    List<String> currLsit = new ArrayList<String>();
                    currLsit.add(currLine);
                    undoManager.add(new undoStack(lineNo, currLsit));
                }
                else if (undoManager.size() > lineNo) {
                    undoManager.get(lineNo).addState(currLine);


                }
                start = i;
                lineNo++;
            }
        }
        for (int i = 0; i < undoManager.size(); i++){
            System.out.print(undoManager.get(i).getID());
            List<String> check = undoManager.get(i).getFullList();
            //System.out.println("check size: " + check.size());
            for (int j = 0; j < check.size(); j++) {
                System.out.print(check.get(j));
            }
        }



    }


}




