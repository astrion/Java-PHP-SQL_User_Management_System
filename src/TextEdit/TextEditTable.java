package TextEdit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
                "Line",
                "State",
                "Description",
                "Empty",
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



    public Object[] newColumns(int index, String description, int line, int state) {
        Object[] columns = new Object[columnNames().length];


                columns[0] = "" + index;
                columns[1] = line;
                columns[2] = state;
                columns[3] = description;
                columns[4] = "";
                columns[5] = false;


        return columns;
    }

//    public void insertRow(List<List<String>> undoManager) {
//        DefaultTableModel model = getModel();
//        Object[] columns = newColumns(model.getRowCount(), undoManager);
//
//        model.insertRow(0, columns);
//    }

    List<List<Integer> > all_ij = new ArrayList<List<Integer> >();
    List<Integer> js = new ArrayList<Integer>();



    public void addRow(String description, int line, int state) {
            DefaultTableModel model = getModel();
            Object[] columns = newColumns(model.getRowCount(), description, line, state);
            int locationNum = 0;
            for(int i=0; i<model.getRowCount(); ++i){
                if ((int) model.getValueAt(i, 1) > line)
                    break;
                locationNum += 1;
            }
            System.out.println(locationNum);
            model.insertRow(locationNum,columns);

    }

}

