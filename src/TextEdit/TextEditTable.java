package TextEdit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

enum Column {
    Line, State, Text, Select;

    public int Index() {
        switch (this) {
            case Line:
                return 0;
            case State:
                return 1;
            case Text:
                return 2;
            case Select:
                return 3;
            default:
                return -1;
        }
    }
}

public class TextEditTable extends JTable {
    public TextEditTable(final Object[][] rowData) {
        super(new DefaultTableModel(rowData, columnNames()));
        setRequestFocusEnabled(false);

        restyleUI();
    }

    // RadioButton restyling from Checkbox to RadioButton
    private void restyleUI() {
        TableColumn forgetTableColumn = getColumnModel().getColumn(Column.Select.Index());
        forgetTableColumn.setCellEditor(new RadioButtonCellEditorRenderer());
        forgetTableColumn.setCellRenderer(new RadioButtonCellEditorRenderer());
    }

    static String[] columnNames() {
        return new String[]{
                "Line",
                "State",
                "Text",
                "Select"
        };
    }

    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 0:
                return Integer.class;
            case 1:
                return Integer.class;
            case 3:
                return Boolean.class;
            default:
                return String.class;
        }
    }

    // disable editing, the editing is only allowed for set selection
    @Override
    public boolean isCellEditable(int row, int column) {
        return column == Column.Select.Index();
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        super.setValueAt(aValue, row, column);
        int lineNumSelected = (int) dataModel.getValueAt(row, Column.Line.Index());
        if (column == Column.Select.Index() && aValue.equals(true)) {
            for (int i = 0; i < dataModel.getRowCount(); i++) {
                int lineNumCurrent = (int) dataModel.getValueAt(i, Column.Line.Index());
                if ((row != i) && (lineNumCurrent == lineNumSelected))
                    super.setValueAt(false, i, Column.Select.Index());
            }
        }
    }

    @Override
    public DefaultTableModel getModel() {
        return (DefaultTableModel) super.getModel();
    }


    public Object[] newColumns(int index, String text, int line, int state) {
        Object[] columns = new Object[columnNames().length];

        columns[0] = line;
        columns[1] = state;
        columns[2] = text;
        columns[3] = false;

        return columns;
    }

    public void addRow(String description, int line, int state) {
        DefaultTableModel model = getModel();
        Object[] columns = newColumns(model.getRowCount(), description, line, state);
        int locationNum = 0;
        for (int i = 0; i < model.getRowCount(); ++i) {
            if ((int) model.getValueAt(i, Column.Line.Index()) > line)
                break;
            locationNum += 1;
        }
        System.out.println(locationNum);
        model.insertRow(locationNum, columns);

    }


    // add rows
    public void Refresh(List<List<String>> editHistory) {
        DefaultTableModel model = new DefaultTableModel(new Object[][]{}, columnNames());
        setModel(model);
        restyleUI();
        for (int i = 0; i < editHistory.size(); i++) {
            List<String> lineEditHistory = editHistory.get(i);
            for (int j = 0; j < lineEditHistory.size(); j++) {
                String textAtState = lineEditHistory.get(j);
                addRow(textAtState, i, j);
            }
        }
    }

}

// define class to support RadioButton updates
class RadioButtonCellEditorRenderer extends AbstractCellEditor implements TableCellRenderer, TableCellEditor, ActionListener {

    private JRadioButton radioButton;

    public RadioButtonCellEditorRenderer() {
        this.radioButton = new JRadioButton();
        radioButton.addActionListener(this);
        radioButton.setOpaque(false);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        radioButton.setSelected(Boolean.TRUE.equals(value));
        return radioButton;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        radioButton.setSelected(Boolean.TRUE.equals(value));
        return radioButton;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        stopCellEditing();
    }

    @Override
    public Object getCellEditorValue() {
        return radioButton.isSelected();
    }
}
