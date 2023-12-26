package Pagine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Oggetti.AggColonna;
import Oggetti.Button;
import Oggetti.FinalVariable;

import java.awt.GridLayout;

import javax.swing.JTextArea;

import Utilities.SqLite;


public class Panel extends JPanel implements DocumentListener{

    private SqLite sql;
    private String dbScelto, nomeTabella;
    private JLabel lbl_titolo, lbl_numColValide;
    private String[] r;
    private ArrayList<AggColonna> arrAc;
    private JPanel pnl_Button;
    private Button btn_creaTab;
    private JTextArea nomeTab;
    private boolean dispositivo = true; //TRUE: FISSO, FALSE: PORTATILE
    
    /**
     * 
     * @param width
     * @param height
     */
    public Panel() {

        arrAc = new ArrayList<AggColonna>();

        lbl_titolo = new JLabel("", SwingConstants.CENTER);
        lbl_titolo.setPreferredSize(new Dimension(FinalVariable.PANEL_WIDTH, 50));

        pnl_Button = new JPanel();
        nomeTabella = "";

        add(lbl_titolo);
        add(pnl_Button);

        setPreferredSize(new Dimension(FinalVariable.PANEL_WIDTH, FinalVariable.PANEL_HEIGHT));
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        mostraDatabases();
    }  

    private void mostraDatabases() {
        String[] dbs = FinalVariable.ALL_DATABASES;
        lbl_titolo.setText("TUTTI I DATABASES");

        for(int i = 0; i < dbs.length; i++) {
            pnl_Button.add(new Button(150, 150, dbs[i].split("\\.")[0], this, FinalVariable.BOTT_DB));
        }

        //Aggiungi database
    }

    public void mostraTabelle() {
        connettiConDB();
        remAll(pnl_Button);
        pnl_Button.setLayout(FinalVariable.FL_C20_20);
        arrAc.clear();

        r = sql.getTables();
        lbl_titolo.setText("TABELLE DEL DATABASE: " + dbScelto.toUpperCase());


        for(int i = 0; i < r.length; i++) {
            pnl_Button.add(new Button(150, 150, r[i], this, FinalVariable.BOTT_TAB));
        }

        pnl_Button.add(new Button(150, 150, "<html>aggiungi<br>tabella</html>", this, FinalVariable.BOTT_AGG_TAB));
    }

    private void connettiConDB() {
        String path = "";

        if(dispositivo) path = FinalVariable.PATH_TO_DB_FISSO;
        else path = FinalVariable.PATH_TO_DB_PORTATILE;

        sql = new SqLite(path  + dbScelto + ".db");
    }

    public void setDBscelto(String dbScelto) {
        this.dbScelto = dbScelto;

        mostraTabelle();
    }

    private void remAll(JPanel panel) {
        panel.removeAll();
        panel.validate();
        panel.repaint();
        System.gc();
    }

    public void pagAggiungiTabella() {
        remAll(pnl_Button);
        lbl_titolo.setText("AGGIUNGI TABELLA");
        pnl_Button.setLayout(new GridLayout(0, 1));
        

        creaPaginaAggiuntaCol();
    }

    private void creaPaginaAggiuntaCol() {
        remAll(pnl_Button);

        add(new CreazioneTabella(dbScelto, this));
    }

    public void setBtnEnable(boolean enable) {
        btn_creaTab.setEnabled(enable);
    }

    public void eliminaAggiungiColonnaTab(AggColonna ac) {
        arrAc.remove(ac);
        pagAggiungiTabella();
    }

    public String[] getTableNames() {
        return sql.getTables();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        nomeTab.setBackground(Color.WHITE);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
    }

    //PAGINA AGGIUNTA COLONNA   

    /**
     * Aggiorna lbl_numColValide che indica il numero di colonne valide rispetto al totale di colonne inserite
     */
    public void aggNumColValide() {
        lbl_numColValide.setText("Colonne valide: " + numColValide() + " / " + arrAc.size());

        if(numColValide() != arrAc.size()) setBtnEnable(false);
    }
    /**
     * Conta il numero di colonne valide
     * @return Numero di colonne valide
     */
    private int numColValide() {
        int num = 0;

        for(int i = 0; i < arrAc.size(); i++) {
            if(arrAc.get(i).isAllOk()) num++;
        }

        return num;
    }

    /**
     * Controlla se il nome della colonne che stiamo inserendo (ac) è già presente o no nella tabella
     * @param aggColonna che si vuole controllare
     * @return
     *<ul style="list-style-type:circle;">
     *  <li>True se esistono già altre colonne con lo stesso nome.</li>
     *  <li>False: se non esistono già altre colonne con lo stesso nome.</li>
     *</ul>  
     * 
     */
    public boolean nomeColEsistente(AggColonna aggColonna) {
        for(int i = 0; i < arrAc.size(); i++) {

            if(arrAc.get(i) != aggColonna && aggColonna.getNome().equals(arrAc.get(i).getNome())) return true;
        }

        return false;
    }

    /**
     * Controlla se altre colonne hanno l'autoincrement attivo (dato che solo una colonna lo può avere)
     * @param aggColonna che si vuole controllare
     * @return True: se non ci sono altri elementi con l'"AUTOINCREMENT" attivo. 
     * False: se ci sono altri elementi con l'AUTOINCREMENT attivo
    */
    public boolean altriAutoIn(AggColonna aggColonna) {
        for(int i = 0; i < arrAc.size(); i++) {
            if(arrAc.get(i) != aggColonna && arrAc.get(i).isAutoIn()) return false;
        }

        return true;
    }


    //Si occupa della gestione e dell'apertura della tabella
    public void apriTabella(String nomeTab) {
        remAll(pnl_Button);

        pnl_Button.setLayout(FinalVariable.FL_C20_20);
        lbl_titolo.setText("TABELLA: " + nomeTab.toUpperCase());
        
        pnl_Button.add(new Tabella(nomeTab, sql, this));
    }

    public String[][] getDataFromTab(String nomeTab, String[] nomeColonne) {
        String query = "SELECT * FROM " + nomeTab;
        String[][] dati = new String[nomeColonne.length][sql.select(query, nomeColonne[0]).length];

        for(int i = 0; i < nomeColonne.length; i++) {
            dati[i] = sql.select(query, nomeColonne[i]);
        }

        return dati;
    }

    public String[] getNomeColonne(String nomeTab) {
        return sql.getColumns(nomeTab);
    }
}
