package Pagine;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JButton;

import Oggetti.FinalVariable;

import Utilities.SqLite;

public class NewPanel extends JPanel{
    
    private JPanel pnl_left, pnl_right;
    private SqLite sqlite;
    private boolean percorsoDesiderato = true; //TRUE FISSO, FALSE PORTATILE
    private String pathToDB;

    public NewPanel() {
        setPreferredSize(new Dimension(FinalVariable.PANEL_WIDTH, FinalVariable.PANEL_HEIGHT));
        setLayout(FinalVariable.FL_L0_0);

        if(percorsoDesiderato) pathToDB = FinalVariable.PATH_TO_DB_FISSO;
        else pathToDB = FinalVariable.PATH_TO_DB_PORTATILE;

        setPaneLeft();
    }

    private void setPaneLeft() {
        pnl_left = new JPanel(FinalVariable.FL_C2_2);
        pnl_left.setPreferredSize(new Dimension(FinalVariable.PANEL_WIDTH / 5, FinalVariable.PANEL_HEIGHT));
        pnl_left.setBackground(Color.RED);

        //add(new LoginPage());

        sqlite = new SqLite(pathToDB + "databases.db");

        String[] colonne = sqlite.getColumns(sqlite.getTables()[0]), 
        nomi = sqlite.select("SELECT " + colonne[0] + " FROM Database", colonne[0]);

        String[] tabelle = sqlite.getTables();

        for(int i = 0; i < tabelle.length; i++) {
            pnl_left.add(new JButton(tabelle[i]));
            System.out.println("Ciao");
        }

        add(pnl_left);
    }

    private void controllaAccount() {

    }
}
