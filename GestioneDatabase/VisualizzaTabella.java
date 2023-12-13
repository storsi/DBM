

import java.util.ArrayList;

import javax.swing.JPanel;

import Utilities.SqLite;

public class VisualizzaTabella extends JPanel{

    private SqLite sqlite;
    private ArrayList<String> r, colonne;
    private String nomeTab;
    
    public VisualizzaTabella(SqLite sqlite, String nomeTab) {
        this.sqlite = sqlite;
        this.nomeTab = nomeTab;

        setLayout(FinalVariable.FL_C0_0);
    }

    private void creaTab() {
        colonne = sqlite.getColumns(nomeTab);

        
    }
}
