package Pagine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import Oggetti.FinalVariable;

import javax.swing.ImageIcon;

import Utilities.SqLite;

public class Tabella extends JPanel{
    //DATI.LENGTH INDICA LA QUANTITÀ DI DATI DI OGNI RIGA
    //DATI[0].LENGTH INDICA LA QUANTITÀ DI DATI PER OGNI COLONNA

    private Dimension dimCasella, dimCasellaVuota, dimColonna;
    private Casella[][] caselleTabella;
    private String[][] dati;
    private String[] nomeColonne;
    private String nomeTab;
    private SqLite sql;
    private Panel panel;
    private int w, h;
    private JPanel pnl_tabella, pnl_opzioni;
    private JLabel lbl_errore;
    
    public Tabella(String nomeTab, SqLite sql, Panel panel) {
        setLayout(new BorderLayout(0, 20));

        this.nomeColonne = panel.getNomeColonne(nomeTab);
        this.nomeTab = nomeTab;
        this.sql = sql;
        this.panel = panel;

        pnl_tabella = new JPanel();
        pnl_opzioni = new JPanel(FinalVariable.FL_L10_10);
        pnl_opzioni.setPreferredSize(new Dimension(w, 50));
        

        w = (int)(FinalVariable.PANEL_WIDTH * 0.7);
        h = (int)(FinalVariable.PANEL_HEIGHT * 0.7);
        dimCasellaVuota = new Dimension((int)Math.round(h / 8.0) + 5, (int)Math.round(h / 16.0));
        dimCasella = new Dimension((w - (int)dimCasellaVuota.getWidth() - ((nomeColonne.length + 2) * 3)) / nomeColonne.length, (int)Math.round(h / 16.0));
        dimColonna = new Dimension((w - (int)dimCasellaVuota.getWidth() - ((nomeColonne.length + 2) * 3)) / nomeColonne.length, (int)Math.round(h / 10.0));
        
        creaTabella();

        
        lbl_errore = new JLabel();
        lbl_errore.setForeground(Color.RED);

        pnl_opzioni.add(new BottoniCasella(FinalVariable.BTN_INDIETRO, panel, this));
        pnl_opzioni.add(new BottoniCasella(FinalVariable.BTN_ELIMINA_TAB, panel, this));
        pnl_opzioni.add(lbl_errore);

        add(pnl_opzioni, BorderLayout.NORTH);
        add(pnl_tabella, BorderLayout.CENTER);
    }

    private void creaTabella() {
        //Libera il "campo" per l'aggiunta delle celle per la tabella
        pnl_tabella.removeAll();
        pnl_tabella.validate();
        pnl_tabella.repaint();

        pnl_tabella.setLayout(FinalVariable.FL_C3_3);
        pnl_tabella.setBackground(Color.BLACK);

        //Ottenere le colonne della tabella
        nomeColonne = panel.getNomeColonne(nomeTab);

        //Ottenere i dati presenti nelle colonne (dati[0] -> dati di una colonna; dati -> colonne)
        dati = panel.getDataFromTab(nomeTab, nomeColonne);

        //Creazione variabile d'appoggio
        Casella cas;

        //Creazione matrice che conterrà tutte le caselle che comporranno la tabella (stesso pattern dei dati)
        caselleTabella = new Casella[dati[0].length + 1][dati.length + 1];

        //Inserimento delle dimensioni per il JPanel che conterrà le celle
        //pnl_tabella.setPreferredSize(new Dimension(w, (h / 16 + 1) * (caselleTabella.length + 2)));
        pnl_tabella.setPreferredSize(new Dimension(w, 6 + (int)dimColonna.getHeight() + (3 + (int)dimCasella.getHeight()) * (dati[0].length + 1)));
        pnl_tabella.setMaximumSize(new Dimension(w, h));

        //Inserimento del nome delle colonne (+ spazio vuoto iniziale)
        pnl_tabella.add(new Casella(new Dimension((int)(dimCasellaVuota.getWidth()), (int)(dimColonna.getHeight()))));
        for(int i = 1; i < caselleTabella[0].length; i++) {
            pnl_tabella.add(new Casella(nomeColonne[i - 1], getDataTypes(i - 1), dimColonna, FinalVariable.CELLA_COLONNA));
        }

        //Inserimento dei dati (+ bottoni per la gestione delle righe e la loro aggiunta)
        for(int i = 0; i < caselleTabella.length; i++) {
            //I indica la colonna della tabella
            if(i < caselleTabella.length - 1) cas = new Casella(i, dimCasellaVuota, this, FinalVariable.NOT_BTN_AGGIUNGI);
            else cas = new Casella(dati[0].length, dimCasellaVuota, this, FinalVariable.IS_BTN_AGGIUNGI);
            
            caselleTabella[i][0] = cas;
            pnl_tabella.add(cas);
            
            for(int j = 1; j < caselleTabella[i].length; j++) {
                //J indica la riga della tabella
                if(i < caselleTabella.length - 1) cas = new Casella(dati[j - 1][i], dimCasella, FinalVariable.CELLA_DATO);
                else cas = new Casella("", dimCasella, FinalVariable.CELLA_DATO);
                
                caselleTabella[i][j] = cas;
                pnl_tabella.add(cas);
            }
        }

        pnl_tabella.validate();

        //Non ho capito il motivo, ma serve questa riga, come per refreshare il contenuto, sennò non si aggiorna
        cancModificaRiga(0);
    }

    private String getDataTypes(int riga) {
        String colonna = nomeColonne[riga], risultato = "(";

        if(sql.isPk(nomeTab, colonna)) risultato += "PRIMARY KEY, ";
        if(sql.isAutoIn(nomeTab, colonna)) risultato += "AUTOINCREMENT, ";
        risultato += sql.getDataTypes(nomeTab)[riga] + ")";

        return risultato;
    }

    public void modificaRiga(int numeroRiga) {

        caselleTabella[numeroRiga][0].modifica();
        for(int i = 1; i < caselleTabella[0].length; i++) {
            if(!sql.isAutoIn(nomeTab, nomeColonne[i - 1])) caselleTabella[numeroRiga][i].modifica();
        }

        for(int i = 0; i < caselleTabella.length; i++) {
            if(i != numeroRiga) caselleTabella[i][0].disabilita(true);
        }
    }

    public void cancModificaRiga(int numeroRiga) {
        for(int i = 0; i < caselleTabella[0].length; i++) {
            if(numeroRiga == dati[0].length) caselleTabella[numeroRiga][i].rigaAgg();
            else caselleTabella[numeroRiga][i].visualizza();
        }

        for(int i = 0; i < caselleTabella.length; i++) {
            if(i != numeroRiga) caselleTabella[i][0].disabilita(false);
        }
    }

    public void prosegui(int numeroRiga) {
        if(numeroRiga == dati[0].length) aggiungiRiga(numeroRiga);
        else aggiornaRiga(numeroRiga);
    }

    public void eliminaRiga(int numeroRiga) {
        String query = "DELETE FROM " + nomeTab + " WHERE ";
        String[] dataTypes = sql.getDataTypes(nomeTab);

        for(int i = 0; i < nomeColonne.length; i++) {
            if(dataTypes[i].equals("TEXT")) query += nomeColonne[i] + " = \'" + caselleTabella[numeroRiga][i + 1].getValoreLbl() + "\'";
            else query += nomeColonne[i] + " = " + caselleTabella[numeroRiga][i + 1].getValoreLbl();

            if(i < nomeColonne.length - 1) query += " AND ";
            else query += ";";
        }

        controlloErrori(sql.delete(query));
        creaTabella();
    }

    public void aggiornaRiga(int numeroRiga) {
        String query = "UPDATE " + nomeTab.toLowerCase() + " SET ";
        String[] dataTypes = sql.getDataTypes(nomeTab);

        for(int i = 0; i < nomeColonne.length; i++) {
            if(dataTypes[i].equals("TEXT")) query += nomeColonne[i] + " = \'" + caselleTabella[numeroRiga][i + 1].getValoreTa() + "\'";
            else query += nomeColonne[i] + " = " + caselleTabella[numeroRiga][i + 1].getValoreTa();

            if(i < nomeColonne.length - 1) query += ", ";
            else query += " ";
        }

        query += "WHERE ";

        for(int i = 0; i < nomeColonne.length; i++) {
            if(dataTypes[i].equals("TEXT")) query += nomeColonne[i] + " = \'" + caselleTabella[numeroRiga][i + 1].getValoreLbl() + "\'";
            else query += nomeColonne[i] + " = " + caselleTabella[numeroRiga][i + 1].getValoreLbl();

            if(i < nomeColonne.length - 1) query += " AND ";
            else query += ";";
        }

        controlloErrori(sql.update(query));

        creaTabella();
    }

    public void aggiungiRiga(int numeroRiga) {
        String query = "INSERT INTO " + nomeTab + " (";
        String[] dataTypes = sql.getDataTypes(nomeTab);

        for(int i = 0; i < nomeColonne.length; i++) {
            query += nomeColonne[i];

            if(i < nomeColonne.length - 1) query += " , ";
            else query += ")";
        }

        query += " VALUES (";

        for(int i = 0; i < nomeColonne.length; i++) {
            if(dataTypes[i].equals("TEXT")) query += "\'" + caselleTabella[numeroRiga][i + 1].getValoreTa() + "\'";
            else query += caselleTabella[numeroRiga][i + 1].getValoreTa();

            if(i < nomeColonne.length - 1) query += " , ";
            else query += ");";
        }

        controlloErrori(sql.insert(query));
        creaTabella();
    }

    public void dropTable() {
        sql.dropTable(nomeTab);
        panel.mostraTabelle();
    }

    private void controlloErrori(int valore) {
        switch (valore) {
            case SqLite.GENERAL_EXCEPTION:
                lbl_errore.setText("ERRORE NELL'ESECUZIONE");
                break;
            case SqLite.INTEGRITY_EXCEPTION: 
                lbl_errore.setText("ERRORE CAUSATO DALLA VIOLAZIONE DEI VINCOLI DI INTEGRITÀ");
                break;
            case SqLite.SYNTAX_EXCEPTION:
                lbl_errore.setText("ERRORE DI SINTASSI DELLA QUERY");
                break;
            default: 
                lbl_errore.setText("");
                break;
        }
    }
}

class Casella extends JPanel{

    private JLabel lbl, lbl_info;
    private JTextArea ta;
    private Dimension dim;
    private JButton btn_el, btn_mod, btn_ok, btn_canc, btn_agg;

    public Casella(String valore, Dimension dim, Color backColor) {
        this.dim = dim;

        setPreferredSize(dim);
        setLayout(FinalVariable.FL_C2_2);

        creaTa(valore);
        lbl = creaLbl(valore, dim, new Font("Courier", Font.PLAIN, 20), backColor);

        add(lbl);
        add(ta);
    }

    public Casella(String valore, String dataTypes, Dimension dim, Color backColor) {
        this.dim = dim;

        setPreferredSize(dim);
        setLayout(FinalVariable.FL_L0_0);

        lbl = creaLbl(valore, new Dimension((int)(dim.getWidth()), (int)(dim.getHeight() * 0.5)), new Font("Courier", Font.PLAIN, 20), backColor);
        lbl_info = creaLbl(dataTypes, new Dimension((int)(dim.getWidth()), (int)(dim.getHeight() * 0.5)), new Font("Courier", Font.PLAIN, 15), backColor);

        add(lbl);
        add(lbl_info);
    }

    public Casella(int numRiga, Dimension dim, Tabella tabella, boolean aggiungi) {
        setPreferredSize(dim);
        Dimension btnDim = new Dimension(23, 23);

        btn_el = new BottoniCasella(numRiga, FinalVariable.BTN_ELIMINA, tabella, btnDim);
        btn_mod = new BottoniCasella(numRiga, FinalVariable.BTN_MODIFICA, tabella, btnDim);
        btn_ok = new BottoniCasella(numRiga, FinalVariable.BTN_OK, tabella, btnDim);
        btn_canc = new BottoniCasella(numRiga, FinalVariable.BTN_CANC, tabella, btnDim);
        btn_agg = new BottoniCasella(numRiga, FinalVariable.BTN_AGGIUNGI, tabella, btnDim);

        btn_el.setVisible(!aggiungi);
        btn_mod.setVisible(!aggiungi);
        btn_ok.setVisible(!aggiungi);
        btn_canc.setVisible(!aggiungi);
        btn_agg.setVisible(aggiungi);

        add(btn_el);
        add(btn_mod);
        add(btn_canc);
        add(btn_ok);
        add(btn_agg);
    }

    public Casella(Dimension dim) {
        setPreferredSize(dim);
    }

    private JLabel creaLbl(String valore, Dimension dim, Font font, Color backColor) {
        JLabel lbl = new JLabel(valore.toUpperCase(), SwingConstants.CENTER);
        lbl.setVerticalAlignment(SwingConstants.TOP);
        lbl.setPreferredSize(dim);
        lbl.setFont(font);
        lbl.setOpaque(true);
        lbl.setBackground(backColor);
        

        return lbl;
    }

    private void creaTa(String valore) {
        ta = new JTextArea(valore.toUpperCase());
        ta.setPreferredSize(dim);
        ta.setFont(new Font("Courier", Font.PLAIN, 20));
        ta.setBackground(FinalVariable.CELLA_MODIFICA);
    }

    public void visualizza() {
        if(btn_el != null) {
            btn_canc.setVisible(false);
            btn_ok.setVisible(false);

            btn_el.setVisible(true);
            btn_mod.setVisible(true);
        } else {
            ta.setVisible(false);
            lbl.setVisible(true);
        }
    }

    public void modifica() {
        if(btn_el != null) {
            btn_canc.setVisible(true);
            btn_ok.setVisible(true);

            btn_el.setVisible(false);
            btn_mod.setVisible(false);
        } else {
            ta.setVisible(true);
            lbl.setVisible(false);
        }
    }

    public void disabilita(boolean disabilita) {
        btn_el.setEnabled(!disabilita);
        btn_mod.setEnabled(!disabilita);
        btn_agg.setEnabled(!disabilita);
    }

    public void rigaAgg() {
        if(btn_el != null) {
            btn_canc.setVisible(false);
            btn_ok.setVisible(false);

            btn_agg.setVisible(true);
        } else {
            ta.setVisible(false);
            lbl.setVisible(true);
        }
    }

    public String getValoreTa() {
        return ta.getText();
    }

    public String getValoreLbl() {
        return lbl.getText();
    }
}

class BottoniCasella extends JButton implements ActionListener{

    private int numeroRiga, tipologia;
    private Tabella tabella;
    private Dimension dim;
    private Panel panel;

    public BottoniCasella(int numeroRiga, int tipologia, Tabella tabella, Dimension dim) {
        this.numeroRiga = numeroRiga;
        this.tabella = tabella;
        this.tipologia = tipologia;
        this.dim = dim;

        aggiungiIcona();

        setPreferredSize(dim);
        addActionListener(this);
    }

    public BottoniCasella(int tipologia, Panel panel, Tabella tabella) {
        this.panel = panel;
        this.tipologia = tipologia;
        this.tabella = tabella;

        dim = new Dimension(40, 40);

        aggiungiIcona();

        setPreferredSize(dim);
        addActionListener(this);
    }

    private void aggiungiIcona() {
        String pathToIcon;

        switch (tipologia) {
            case FinalVariable.BTN_ELIMINA:  
            case FinalVariable.BTN_ELIMINA_TAB: pathToIcon = "./Icons/trashIcon.png"; 
            break;
            case FinalVariable.BTN_MODIFICA: pathToIcon = "./Icons/modifyIcon.png";
            break;
            case FinalVariable.BTN_CANC: pathToIcon = "./Icons/cancIcon.png";
            break;
            case FinalVariable.BTN_AGGIUNGI: pathToIcon = "./Icons/addIcon.png";
            break;
            case FinalVariable.BTN_INDIETRO: pathToIcon = "./Icons/backIcon.png";
            break;
            //DEFAULT = btn_ok
            default: pathToIcon = "./Icons/okIcon.png";
            break;
        }

        ImageIcon icon = new ImageIcon(pathToIcon);
        Image img = icon.getImage().getScaledInstance((int)dim.getWidth() - 3, (int)dim.getHeight() - 3, Image.SCALE_SMOOTH);

        setIcon(new ImageIcon(img));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (tipologia) {
            case FinalVariable.BTN_ELIMINA: tabella.eliminaRiga(numeroRiga);
            break;
            case FinalVariable.BTN_MODIFICA: tabella.modificaRiga(numeroRiga);
            break;
            case FinalVariable.BTN_CANC: tabella.cancModificaRiga(numeroRiga);
            break;
            case FinalVariable.BTN_AGGIUNGI: tabella.modificaRiga(numeroRiga);
            break;
            case FinalVariable.BTN_INDIETRO: panel.mostraTabelle();
            break;
            case FinalVariable.BTN_ELIMINA_TAB: tabella.dropTable();
            break;
            //DEFAULT = btn_ok
            default: tabella.prosegui(numeroRiga);
            break;
        }
    }
}
