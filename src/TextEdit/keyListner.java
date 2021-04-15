package TextEdit;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

public class keyListner {

    static int id = 2;
    public static void kl(JTextArea area) {

        CaretListenerLabel caretListenerLabel = new CaretListenerLabel(
                "Caret Status");
        area.addCaretListener(caretListenerLabel);

        Vector newLineVector = new Vector();
        newLineVector.add(-10);
        //newLineVector.add(-9);
        Vector<paraStorage> paraStor = new Vector();
        paraStor.add(new paraStorage(1));  // adds the 1st paragraph to the vector
        paraStor.get(0).setSize(1);

        area.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // System.out.println("0:"+e.getKeyCode());
            }

            @Override
            public void keyPressed(KeyEvent e) {
                //System.out.println(e.getKeyCode());
                int caret = caretListenerLabel.caretPos;
                switch (e.getKeyCode()){
                    case 10:
                        //int caret = caretListenerLabel.caretPos;
                        newLineVector.add(caret);
                        int caretLessOne = caret-1;
                        int nlvLessOne = (Integer) newLineVector.get(newLineVector.size() -2);

                        //System.out.println("caret " + caret + " --  lastEntry " + newLineVector.get(newLineVector.size()-1));
                        if (nlvLessOne == caretLessOne){
                            System.out.println("stack " + nlvLessOne + "multiple Enters in a row");

                        }
                        else {
                            paraStor.add(new paraStorage(id));
                            paraStor.get(id-1).setStart(caret); //id-1 = current para, because 0 base index
                            paraStor.get(id-1).setSize(1); //temp size
                            paraStor.get(id-2).setSize(caret - paraStor.get(id-2).getStart());
                            if (id==2) {
                                paraStor.get(id-2).setSize((caret+1) - paraStor.get(id-2).getStart());
                            }


                            System.out.println("previous para: id " + paraStor.get(id-2).getID() + " | start " + paraStor.get(id-2).getStart() +" | size " + paraStor.get(id-2).getSize());
                            System.out.println("new para: id " + paraStor.get(id-1).getID() + " | start " + paraStor.get(id-1).getStart() +" | size " + paraStor.get(id-1).getSize());
                            id++;
                        }
                        break;

                    case 8:
                        for (int i = paraStor.size(); i > 0 ; i--) {
                            if (caret > paraStor.get(i-1).getStart()){
                                paraStor.get(i-1).setSize(paraStor.get(i-1).getSize()-1);
                                for (int j = i ; j < paraStor.size(); j++){
                                    paraStor.get(j).setStart(paraStor.get(j).getStart()-1);
                                }
                                break;
                            }
                        }
                        //remove empty paragraphs
                        for (int i = 0 ; i < paraStor.size(); i++) {
                            if (paraStor.get(i).getSize() < 1) {
                                for (int j = i ; j < paraStor.size(); j++){
                                    paraStor.get(j).setID(paraStor.get(j).getID()-1);
                                }
                                paraStor.remove(i);
                                id--;
                                System.out.println("id: " + id);
                                System.out.println("paraSzie: " + paraStor.size());
                            }
                        }

                        //check to have at least 1 paragraph at all times
                        if (paraStor.size()==0) {
                            paraStor.add(new paraStorage(1));
                            paraStor.get(0).setSize(1);
                        }
                        break;

                    case 127:
                        for (int i = 0 ; i < paraStor.size(); i++) {
                            System.out.println(" para: id " + paraStor.get(i).getID() + " | start " + paraStor.get(i).getStart() +" | size " + paraStor.get(i).getSize());
                        }
                        System.out.println();
                }

            }

            @Override
            public void keyReleased(KeyEvent e) {
                //System.out.println("2:"+e.getKeyChar());
            }
        });
    }
}
