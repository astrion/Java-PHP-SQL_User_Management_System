package TextEdit;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Timer;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class TextEdit extends JFrame implements ActionListener {
    private static JTextArea textArea;
    private static JFrame frame;
    private static JScrollPane historyTableScrollPane;
    private static JPanel historyManagementPanel;
    private static JPanel undoforgetButtonPanel;
    private static JPanel gridPanel;
    private static Boolean tableExist = false;
    private static TextEditTable historyTable;
    private static JButton undoJButton;
    private static JButton undoAllJButton;
    private static JButton forgetJButton;
    private static JButton forgetAllJButton;
    private static int returnValue = 0;
    private final String DEFAULT_TEXT = "";
    private Timer timer;
    private static boolean textChanged = false;
    private LocalDateTime lastEditTime = LocalDateTime.now();
    static List<List<String>> historyManager = new ArrayList<List<String>>();
    private final int WAITTIME = 1000;

    public TextEdit() {
        run();
    }

    public void run() {
        // Set timer to run every 500ms. For a proper operation, 1000ms delay on start is added
        timer = new Timer();
        timer.schedule(new Trigger(), 1000, 500);

        //                    ********** UI Structure **********
        //|----------------------------------Frame------------------------------|
        //|  |====================GridPanel (row:2 column:1)================|   |
        //|  |  |------JScrollPane--------|    |----------JPanel--------|   |   |
        //|  |  |    |---JTextArea----|   |    |  |---JScrollPane---|   |   |   |
        //|  |  |    |                |   |    |  |  TextEditTable  |   |   |   |
        //|  |  |    |----------------|   |    |------------------------|   |   |
        //|  |  |-------------------------|    |  |------JPanel-----|   |   |   |
        //|  |                                 |  |   JButtons x 4  |   |   |   |
        //|  |                                 |------------------------|   |   |
        //|  |==============================================================|   |
        //|---------------------------------------------------------------------|



        // Set the look-and-feel (LNF) of the application
        // Try to default to whatever the host system prefers
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TextEdit.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TextArea
        // Set attributes of the app window
        textArea = new JTextArea();
        textArea.setLineWrap(true);

        // The DefaultTableModel supports addRow method to update its contents
        historyTable = new TextEditTable(new Object[][]{});

        frame = new JFrame(EnumWindowCaption.PRIMARY.caption);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(640, 480);

        // Put text area into scroll pane to enable scrolling feature
        JScrollPane textScrollPane = new JScrollPane(textArea);
        // Put  history(undo) management table into scrollable pane
        historyTableScrollPane = new JScrollPane(historyTable);

        // Create JButtons for undo, undo all, forget, and forget all
        undoJButton = new JButton("Undo");
        undoAllJButton = new JButton("Undo All");
        forgetJButton = new JButton("Forget");
        forgetAllJButton = new JButton("Forget All");
        // Create new panel for undoforget buttons
        undoforgetButtonPanel = new JPanel();
        undoforgetButtonPanel.add(undoJButton);
        undoforgetButtonPanel.add(undoAllJButton);
        undoforgetButtonPanel.add(forgetJButton);
        undoforgetButtonPanel.add(forgetAllJButton);

        // Add actions for buttons
        // Perform undo for selected items
        undoJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TextProcessor.Undo(historyManager, historyTable, textArea);
            }
        });
        // Perform undoALL for selected (oldest from history) items
        undoAllJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextProcessor.SelectOldest(historyTable);
                TextProcessor.Undo(historyManager, historyTable, textArea);
            }
        });
        // Perform forget for selected items
        forgetJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextProcessor.Forget(historyManager, historyTable);
            }
        });
        // Perform forgetALL for selected (newest from history) items
        forgetAllJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextProcessor.SelectLatest(historyTable);
                TextProcessor.Forget(historyManager, historyTable);
            }
        });

        // Add GridLayout to have history table scroll pane and undo forget button panel
        historyManagementPanel = new JPanel(new GridLayout(2, 1));
        historyManagementPanel.add(historyTableScrollPane);
        historyManagementPanel.add(undoforgetButtonPanel);

        // Set GridPanel and add Text Scroll Pane into left, and Table Scroll Pane into right
        gridPanel = new JPanel(new GridLayout(1, 2));
        frame.setContentPane(gridPanel);
        gridPanel.add(textScrollPane);


        // Build the menu
        JMenuBar menu_main = new JMenuBar();

        JMenu menu_file = new JMenu(EnumCommandCaption.FILE.getCaption());
        JMenu menu_edit = new JMenu(EnumCommandCaption.EDIT.getCaption());
        JMenu menu_help = new JMenu(EnumCommandCaption.HELP.getCaption());

        JMenuItem menuitem_new = new JMenuItem(EnumCommandCaption.NEW.getCaption());
        JMenuItem menuitem_open = new JMenuItem(EnumCommandCaption.OPEN.getCaption());
        JMenuItem menuitem_save = new JMenuItem(EnumCommandCaption.SAVE.getCaption());
        JMenuItem menuitem_quit = new JMenuItem(EnumCommandCaption.QUIT.getCaption());

        JMenuItem menuitem_undo_forget_edits = new JMenuItem(EnumCommandCaption.VIEW_HIDE_UNDO_FORGET_EDIT_HISTORY.getCaption());

        JMenuItem menuitem_about = new JMenuItem(EnumCommandCaption.ABOUT.getCaption());

        menuitem_new.addActionListener(this);
        menuitem_open.addActionListener(this);
        menuitem_save.addActionListener(this);
        menuitem_quit.addActionListener(this);

        menuitem_undo_forget_edits.addActionListener(this);

        menuitem_about.addActionListener(this);

        menu_main.add(menu_file);
        menu_main.add(menu_edit);
        menu_main.add(menu_help);

        menu_file.add(menuitem_new);
        menu_file.add(menuitem_open);
        menu_file.add(menuitem_save);
        menu_file.add(menuitem_quit);

        menu_edit.add(menuitem_undo_forget_edits);
        menu_help.add(menuitem_about);

        // Set menus into frame and make it visible
        frame.setJMenuBar(menu_main);
        frame.setVisible(true);

        // When key is typed, then record current time and set textChange flag for checking idle period (1sec)
        textArea.getDocument().addUndoableEditListener(
                new UndoableEditListener() {
                    @Override
                    public void undoableEditHappened(UndoableEditEvent e) {
                        // get one character from UndoableEditEvent
                        lastEditTime = LocalDateTime.now();
                        textChanged = true;
                    }
                }
        );
    }

    // This block runs every 1000ms to check key type idle status
    class Trigger extends TimerTask {
        public void run() {
            // When no undoable event exists, then ignore the timer event
            if (!textChanged) {
                return;
            }
            LocalDateTime now = LocalDateTime.now();
            // Compare currnt time with the latest user input time to 1000ms idling
            if (ChronoUnit.MILLIS.between(lastEditTime, now) > WAITTIME) {
                // Update historyManager data structure from text area
                TextProcessor.LineUpdates(textArea, historyManager);
//                TextProcessor.Print(historyManager);
                // Refresh table using new data
                historyTable.Refresh(historyManager);
                // Set flag for timer operation
                textChanged = false;
            }
        }
    }

    // Define operation for user menu selection
    @Override
    public void actionPerformed(ActionEvent e) {
        String ingest = DEFAULT_TEXT;
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Choose destination.");
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);

        String ae = e.getActionCommand();
        EnumCommandCaption command = EnumCommandCaption.get(ae);
        switch (command) {

            case NEW:

                historyManager.clear();
                historyTable.Refresh(historyManager);
                textArea.setText(DEFAULT_TEXT);
                break;

            case OPEN:

                historyManager.clear();
                historyTable.Refresh(historyManager);
                returnValue = jfc.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File f = new File(jfc.getSelectedFile().getAbsolutePath());
                    try {
                        FileReader read = new FileReader(f);
                        Scanner scan = new Scanner(read);
                        while (scan.hasNextLine()) {
                            String line = scan.nextLine() + "\n";
                            ingest = ingest + line;
                        }
                        textArea.setText(ingest);
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                }
                break;

            case SAVE:

                returnValue = jfc.showSaveDialog(null);
                try {
                    File f = new File(jfc.getSelectedFile().getAbsolutePath());
                    FileWriter out = new FileWriter(f);
                    out.write(textArea.getText());
                    out.close();
                } catch (FileNotFoundException ex) {
                    Component f = null;
                    JOptionPane.showMessageDialog(f, "File not found.");
                } catch (IOException ex) {
                    Component f = null;
                    JOptionPane.showMessageDialog(f, "Error.");
                }
                break;

            case QUIT:

                System.exit(0);
                break;


            case VIEW_HIDE_UNDO_FORGET_EDIT_HISTORY:
                // toggle (show and hide) history management table
                if (tableExist) {
                    gridPanel.remove(historyManagementPanel);
                } else {
                    gridPanel.add(historyManagementPanel);
                }
                tableExist = !tableExist;
                // Set it visible to refresh frame
                frame.setVisible(true);
                break;

            case ABOUT:

                // TODO: (OPTIONAL) add a Splash Screen that can get reloaded from here
                System.out.println("ABOUT THIS...");
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + ae);
        }
    }


}

