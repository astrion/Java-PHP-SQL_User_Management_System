package TextEdit;

import javax.swing.*;
import java.util.List;
import java.util.ArrayList;


public class AreaProcessor {

    int start = 0;
    int lineNo = 0;



    //constructor
    public AreaProcessor(JTextArea area, List<List<String>> undoManager) {
        String text = area.getText();

        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == 10) {
                String currLine = (text.substring(start, i+1));
                //System.out.println("currLine " + currLine);
                //System.out.println("start " + start + "| i " + i);
                //System.out.println("size UM " + undoManager.size());
                //System.out.println("lineNo " + lineNo);
                // line is new
                if (undoManager.isEmpty() || undoManager.size() <= lineNo ) {
                    List<String> currLsit = new ArrayList<String>();
                    currLsit.add(currLine);
                    undoManager.add(currLsit);
                }
                //if line exists
                else if (undoManager.size() > lineNo) {
                    List currLineInUM = undoManager.get(lineNo);
                    String currState = (String) currLineInUM.get(currLineInUM.size()-1);
                    //System.out.println("currLineInUM " + currState);
                    //System.out.println("currLine " + currLine);
                    if (!currState.equals(currLine)){
                        currLineInUM.add(currLine);
                    }



                }
                start = i+1;
                lineNo++;
            }
        }
        //printing
        for (int i = 0; i < undoManager.size(); i++){

            System.out.print("ID: " + (i+1) + " | ");

            List currLineinUM = undoManager.get(i);
//            System.out.println("check size: " + check.size());
            for (int j = 0; j < currLineinUM.size(); j++) {
                System.out.print("state :" + j + " " + currLineinUM.get(j));
            }
        }



    }


}






