package TextEdit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;

public class TextEditTable extends JTable {
    public TextEditTable(final Object[][] rowData) {
        super(new DefaultTableModel(rowData, columnNames()));
        // disable editing
        setCellSelectionEnabled(false);
        setRequestFocusEnabled(false);
    }

    static String[] columnNames() {
        return new String[]{
                "#",
                "Timestamp",
                "P",
                "EditType",
                "Difference",
                "Set"
        };
    }


    @Override
    public Class<?> getColumnClass(int column) {
        switch (column) {
            case 5:
                return Boolean.class;
            default:
                return String.class;
        }
    }

    @Override
    public boolean isCellSelected(int row, int column) {
        return column == 5;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 5;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        super.setValueAt(aValue, row, column);

        if (column == 5 && aValue.equals(true)) {
            for (int i = 0; i < dataModel.getRowCount(); i++) {
                if (row != i)
                    super.setValueAt(false, i, 5);
            }
        }
    }

    @Override
    public DefaultTableModel getModel() {
        return (DefaultTableModel) super.getModel();
    }

    public Object[] newColumns(int index, LastEdit lastEdit) {
        Object[] columns = new Object[columnNames().length];

        columns[0] = "" + index;
        columns[1] = lastEdit.dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        columns[2] = "" + lastEdit.edit.hashCode();
        columns[3] = lastEdit.edit.getPresentationName();
        columns[4] = lastEdit.difference;
        columns[5] = false;
        return columns;
    }

    public void insertRow(LastEdit lastEdit) {
        DefaultTableModel model = getModel();
        Object[] columns = newColumns(model.getRowCount(), lastEdit);

        model.insertRow(0, columns);
    }

    public void addRow(LastEdit lastEdit) {
        DefaultTableModel model = getModel();
        Object[] columns = newColumns(model.getRowCount(), lastEdit);

        model.addRow(columns);
    }
}
