package TextEdit;

import javax.swing.*;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.undo.UndoableEdit;
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

public final class TextEdit extends JFrame implements ActionListener, TextProcessing, SecondaryUIComponents {
    private static JTextArea area;
    private static JFrame frame;
    private static JScrollPane dataTableScrollPane;
    private static JPanel dataManagementPanel;
    private static JPanel dataButtonPanel;
    private static JPanel gridPanel;
    private static JTabbedPane undoForgetTabbedPane;
    private static Boolean tableExist = false;
    private static TextEditTable dataTable;
    private static UndoableEdit nextEdit;
    private static UndoableEdit prevEdit;
    private static JButton undoJbutton;
    private static JButton forgetJbutton;
    private static JButton forgetAllJbutton;
    private static int returnValue = 0;
    private final String DEFAULT_TEXT = "";
    private String prev = DEFAULT_TEXT;
    private String next = DEFAULT_TEXT;
    private SmartUndoManager smartUndoManager = new SmartUndoManager();
    private Timer timer;
    private static boolean textChanged = false;
    private LocalDateTime lastEditTime = LocalDateTime.now();
    static List<List<String>> undoManager = new ArrayList<List<String>>();
    private final int WAITTIME = 1000;

    public TextEdit() {
        run();
    }

    public void run() {
        timer = new Timer();
        timer.schedule(new Trigger(), 1000, 500);

        frame = new JFrame(EnumWindowCaption.PRIMARY.caption);

        // Set the look-and-feel (LNF) of the application
        // Try to default to whatever the host system prefers
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(TextEdit.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TextArea
        // Set attributes of the app window
        area = new JTextArea();
        area.setLineWrap(true);


        // The DefultTableModel supports addRow method to update its contents
        dataTable = new TextEditTable(new Object[][]{});

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setSize(640, 480);
        frame.setVisible(true);

        // Put text area into scroll pane to enable scrolling feature
        JScrollPane textScrollPane = new JScrollPane(area);
        dataTableScrollPane = new JScrollPane(dataTable);
        dataButtonPanel = new JPanel();
        undoJbutton = new JButton("Undo");
        forgetJbutton = new JButton("Forget");
        forgetAllJbutton = new JButton("Forget All");
        dataButtonPanel.add(undoJbutton);
        dataButtonPanel.add(forgetJbutton);
        dataButtonPanel.add(forgetAllJbutton);

        // Add actions for buttons
        undoJbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextProcessor.Undo(undoManager, dataTable, area);
            }
        });
        forgetJbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextProcessor.Forget(undoManager, dataTable);
            }
        });
        forgetAllJbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TextProcessor.SelectLatest(dataTable);
                TextProcessor.Forget(undoManager, dataTable);
            }
        });

        dataManagementPanel = new JPanel(new GridLayout(2, 1));
        dataManagementPanel.add(dataTableScrollPane);
        dataManagementPanel.add(dataButtonPanel);

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

        JMenuItem menuitem_clear_edit_history = new JMenuItem(EnumCommandCaption.CLEAR_LAST_EDIT_HISTORY.getCaption());
        JMenuItem menuitem_clear_edit_history_by_n_steps = new JMenuItem(EnumCommandCaption.CLEAR_LAST_EDIT_HISTORY_BY_N_STEPS.getCaption());
        JMenuItem menuitem_clear_edit_history_by_elapsed_Time = new JMenuItem(EnumCommandCaption.CLEAR_LAST_EDIT_HISTORY_BY_ELAPSED_TIME.getCaption());
        JMenuItem menuitem_undo_by_n_steps = new JMenuItem(EnumCommandCaption.UNDO_SEQUENTIALLY_BY_N_STEPS.getCaption());
        JMenuItem menuitem_undo_by_elapsed_time = new JMenuItem(EnumCommandCaption.UNDO_SEQUENTIALLY_BY_ELAPSED_TIME.getCaption());
        JMenuItem menuitem_undo_by_any_order = new JMenuItem(EnumCommandCaption.UNDO_BY_ANY_ORDER.getCaption());
        JMenuItem menuitem_view_last_edits = new JMenuItem(EnumCommandCaption.VIEW_HIDE_LAST_EDIT_HISTORY.getCaption());

        JMenuItem menuitem_about = new JMenuItem(EnumCommandCaption.ABOUT.getCaption());

        menuitem_new.addActionListener(this);
        menuitem_open.addActionListener(this);
        menuitem_save.addActionListener(this);
        menuitem_quit.addActionListener(this);

        menuitem_clear_edit_history.addActionListener(this);
        menuitem_clear_edit_history_by_n_steps.addActionListener(this);
        menuitem_clear_edit_history_by_elapsed_Time.addActionListener(this);
        menuitem_undo_by_n_steps.addActionListener(this);
        menuitem_undo_by_elapsed_time.addActionListener(this);
        menuitem_undo_by_any_order.addActionListener(this);
        menuitem_view_last_edits.addActionListener(this);

        menuitem_about.addActionListener(this);

        menu_main.add(menu_file);
        menu_main.add(menu_edit);
        menu_main.add(menu_help);

        menu_file.add(menuitem_new);
        menu_file.add(menuitem_open);
        menu_file.add(menuitem_save);
        menu_file.add(menuitem_quit);

        menu_edit.add(menuitem_clear_edit_history);
        menu_edit.add(menuitem_clear_edit_history_by_n_steps);
        menu_edit.add(menuitem_clear_edit_history_by_elapsed_Time);

        menu_edit.add(menuitem_undo_by_n_steps);
        menu_edit.add(menuitem_undo_by_elapsed_time);
        menu_edit.add(menuitem_undo_by_any_order);
        menu_edit.add(menuitem_view_last_edits);

        menu_help.add(menuitem_about);

        frame.setJMenuBar(menu_main);
        frame.setVisible(true);


        area.getDocument().addUndoableEditListener(
                new UndoableEditListener() {
                    @Override
                    public void undoableEditHappened(UndoableEditEvent e) {
                        // get one character from UndoableEditEvent
                        nextEdit = e.getEdit();
                        if (prevEdit == null) {
                            prevEdit = nextEdit;
                        }

                        // following block is to handle when the previous mode (insert, delete) does not match with the
                        // next mode.
                        if (!prevEdit.getPresentationName().equals(nextEdit.getPresentationName())) {
                            //call grouping when insert <-->delete changes
                            smartUndoManager.addEdit(prevEdit, prev, next);

                            // update prev
                            prev = next;
                            textChanged = false;
                        }
                        // accumulate editable events
                        smartUndoManager.undoManager.undoableEditHappened(e);

                        // update next
                        next = area.getText();
                        lastEditTime = LocalDateTime.now();
                        textChanged = true;
                        prevEdit = nextEdit;
                    }
                }
        );
    }

    List<List<Integer>> prevLastState = new ArrayList<List<Integer>>();// keeps thack of states in Table


    class Trigger extends TimerTask {
        public void run() {
            // When no undoable event exists, then ignore the timer event
            if (nextEdit == null || !textChanged) {
                return;
            }
            LocalDateTime now = LocalDateTime.now();
            if (ChronoUnit.MILLIS.between(lastEditTime, now) > WAITTIME) {
                TextProcessor.LineUpdates(area, undoManager);
                TextProcessor.Print(undoManager);

                dataTable.Refresh(undoManager);
                textChanged = false;

            }
        }
    }

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

                area.setText(DEFAULT_TEXT);
                prev = area.getText();
                break;

            case OPEN:

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
                        area.setText(ingest);
                        prev = area.getText();
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
                    out.write(area.getText());
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

            case CLEAR_LAST_EDIT_HISTORY:

                // clear all edits
                this.smartUndoManager.clear();
                updateLastEditView(this.smartUndoManager.lastEdits);
                break;

            case CLEAR_LAST_EDIT_HISTORY_BY_N_STEPS:

                // TODO: ..
                break;

            case CLEAR_LAST_EDIT_HISTORY_BY_ELAPSED_TIME:

                // TODO: ...
                break;

            case REDO:

                // TODO: (OPTIONAL) review, and if possible, add an extra adjustment for multiple edits
                // NOTE: a simple redo would work as this


                this.smartUndoManager.redo();

                // update view
                updateLastEditView(this.smartUndoManager.lastEdits);

                break;

            case UNDO:

                // NOTE: a simple undo would work as this:

                /*
                if (undoManager.canUndo()) {
                    undoManager.undo();
                    next = area.getText();
                    System.out.println("applied undo");
                } else
                    System.out.println("cannot undo");
                 */

                // but, it the same as this with N=1
                //this.smartUndoManager.undoMultipleNToLastEdits(1);
                this.smartUndoManager.undo();

                // update view
                updateLastEditView(this.smartUndoManager.lastEdits);

                break;

            case UNDO_SEQUENTIALLY_BY_N_STEPS:

                int steps;
                try {
                    steps = Integer.parseInt(getUserInput(
                            frame,
                            "To undo latest edits, please specify how many of them below:",
                            "1"
                    ));
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    steps = 0;
                }
                this.smartUndoManager.undoMultipleNToLastEdits(steps);

                // update view
                updateLastEditView(this.smartUndoManager.lastEdits);

                break;

            case UNDO_SEQUENTIALLY_BY_ELAPSED_TIME:

                double seconds;
                try {
                    seconds = Double.parseDouble(getUserInput(
                            frame,
                            "To undo recent edits, please specify a time span (in seconds) below:",
                            "5"
                    ));
                } catch (NumberFormatException ex) {
                    ex.printStackTrace();
                    seconds = 0.0;

                }
                int expectedSteps = this.smartUndoManager.getStepsToUndoInLatestSeconds(seconds);
                this.smartUndoManager.undoMultipleNToLastEdits(expectedSteps);

                // update view
                updateLastEditView(this.smartUndoManager.lastEdits);

                break;

            case UNDO_BY_ANY_ORDER:

                // TODO: implement or remove
                String input = getUserInput(
                        frame,
                        "To undo recent edits, please list them below (e.g. 7,35):",
                        null);
                System.out.println("TODO: implement any-order undo... (user entered:" + input + ")");
                break;

            case VIEW_HIDE_LAST_EDIT_HISTORY:

                if (tableExist) {
                    gridPanel.remove(dataManagementPanel);
                } else {
                    gridPanel.add(dataManagementPanel);
                }
                tableExist = !tableExist;
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

