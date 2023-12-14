

import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import Utilities.SqLite;

public class VisualizzaTabella extends JPanel{

    private SqLite sqlite;
    private ArrayList<String> r, colonne;
    private String nomeTab;
    
    public VisualizzaTabella(SqLite sqlite, String nomeTab) {
        this.sqlite = sqlite;
        this.nomeTab = nomeTab;

        setLayout(FinalVariable.FL_C0_0);

        creaTab();

        setVisible(true);
    }

    private void creaTab() {
        colonne = sqlite.getColumns(nomeTab);

        Object[][] data = {
                {"John", "Doe", 25},
                {"Jane", "Smith", 30},
                {"Bob", "Johnson", 22}
        };

        // Column names
        String[] columnNames = {"First Name", "Last Name", "Age"};

        // Create a table model
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        // Create the JTable
        JTable jTable = new JTable(model);

        // Add the table to a scroll pane
        JScrollPane scrollPane = new JScrollPane(jTable);

        // Add the scroll pane to the frame
        add(scrollPane);
    }
}
