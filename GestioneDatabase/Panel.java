
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridLayout;

import javax.swing.JTextArea;

import Utilities.SqLite;


public class Panel extends JPanel implements DocumentListener{

    private SqLite sql;
    private String dbScelto, nomeTabella;
    private JLabel lbl_titolo, lbl_numColValide;
    private ArrayList<String> r;
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


        for(int i = 0; i < r.size(); i++) {
            System.out.println(r.get(i));
            pnl_Button.add(new Button(150, 150, r.get(i), this, FinalVariable.BOTT_TAB));
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

    public void aggiungiColonnaTab() {
        nomeTabella = nomeTab.getText();
        if(arrAc.size() == 0 || (arrAc.size() > 0 && arrAc.get(arrAc.size() - 1).isAllOk())) {
            arrAc.add(new AggColonna(this));
            pagAggiungiTabella();
            setBtnEnable(false);
        }
    }

    private void creaPaginaAggiuntaCol() {
        remAll(pnl_Button);

        lbl_titolo.setText("CREA UNA TABELLA PER IL DATABASE: " + dbScelto.toUpperCase());
        
        JPanel pnl1 = new JPanel(FinalVariable.FL_C20_20);

        pnl1.add(new JLabel("Nome Tabella: ", SwingConstants.CENTER));

        nomeTab = new JTextArea();
        nomeTab.getDocument().addDocumentListener(this);
        nomeTab.setText(nomeTabella);
        nomeTab.setPreferredSize(new Dimension(200, 30));
        pnl1.add(nomeTab);
        
        lbl_numColValide = new JLabel();
        aggNumColValide();
        pnl1.add(lbl_numColValide);

        pnl_Button.add(pnl1);

        for(int i = 0; i < arrAc.size(); i++) {
            pnl_Button.add(arrAc.get(i));
        }

        JPanel pnl2 = new JPanel(FinalVariable.FL_C20_20);
        btn_creaTab = new Button(100, 50, "CREA", this, FinalVariable.CREA_TAB);
        btn_creaTab.setEnabled(arrAc.size() == 1 && arrAc.get(0).isAllOk() || arrAc.size() > 1);


        pnl2.add(new Button(100, 50, "<html>AGGIUNGI<br>COLONNA</html>", this, FinalVariable.AGG_COLONNA));
        pnl2.add(new Button(100, 50, "CANCELLA", this, FinalVariable.CANC_TAB));
        pnl2.add(btn_creaTab);


        pnl_Button.add(pnl2);
    }

    public void setBtnEnable(boolean enable) {
        btn_creaTab.setEnabled(enable);
    }

    public void eliminaAggiungiColonnaTab(AggColonna ac) {
        arrAc.remove(ac);
        pagAggiungiTabella();
    }

    
    public void creaTabella() {
        r = sql.getTables();
        boolean nomeTabOk = true, pkOk = false;

        if(!nomeTab.getText().equals("") && nomeTab.getText() != null) {
            //controlla che non esista già una tabella con quel nome
            for(int i = 0; i < r.size(); i++) {
                if(nomeTab.getText().toLowerCase().equals(r.get(i).toLowerCase())) nomeTabOk = false;
            }

            //Controlla che ci sia almeno una pk
            for(int i = 0; i < arrAc.size(); i++) {
                if(arrAc.get(i).isPK()) {
                    pkOk = true;
                    break;
                }
            }

            if(nomeTabOk && pkOk) {
            String query = "CREATE TABLE " + nomeTab.getText().toLowerCase() + " (";
            boolean virgola = false;
            String pks = "PRIMARY KEY(";

            for(int i = 0; i < arrAc.size(); i++) {
                if(arrAc.get(i).isAllOk()) {
                    query += arrAc.get(i).toString();
                    if(arrAc.get(i).isPK()) {
                        if(virgola) pks += ",";
                        pks += arrAc.get(i).getNome() + " ";
                        virgola = true;
                    }
                }
            }

            query += pks + "));";
            sql.addTab(query);

            System.out.println(query);
        }

        mostraTabelle();
        } else {
            nomeTab.setBackground(FinalVariable.ERROR_COLOR);
        }

        
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

        System.out.println("A" + nomeTab);
        
        pnl_Button.add(new Tabella(nomeTab, sql, this));
    }

    public String[][] getDataFromTab(String nomeTab) {
        String query = "SELECT * FROM " + nomeTab;
        String[][] dati = new String[r.size()][sql.queryToDB(query, r.get(0)).size()];

        for(int i = 0; i < r.size(); i++) {
            dati[i] = sql.queryToDB(query, r.get(i)).toArray(new String[0]);
        }

        return dati;
    }

    public ArrayList<String> getNomeColonne(String nomeTab) {
        return sql.getColumns(nomeTab);
    }
}
