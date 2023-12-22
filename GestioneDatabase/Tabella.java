import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

import Utilities.SqLite;

public class Tabella extends JPanel{
    //DATI.LENGTH INDICA LA QUANTITÀ DI DATI DI OGNI RIGA
    //DATI[0].LENGTH INDICA LA QUANTITÀ DI DATI PER OGNI COLONNA

    private Dimension dimCasella, dimCasellaVuota;
    private Casella[][] caselleTabella;
    private String[][] dati;
    private ArrayList<String> nomeColonne;
    private String nomeTab;
    private SqLite sql;
    private Panel panel;
    
    public Tabella(String nomeTab, SqLite sql, Panel panel) {
        this.nomeColonne = panel.getNomeColonne(nomeTab);
        this.nomeTab = nomeTab;
        this.sql = sql;
        this.panel = panel;

        int w = (int)(FinalVariable.PANEL_WIDTH * 0.8);
        int h = (int)(FinalVariable.PANEL_HEIGHT * 0.8);

        dimCasellaVuota = new Dimension(h / 8 + 5, h / 16);
        dimCasella = new Dimension((w - (int)dimCasellaVuota.getWidth() - ((nomeColonne.size() + 2) * 3)) / nomeColonne.size(), h / 16);
        
        creaTabella();

        setPreferredSize(new Dimension(w, (h / 16 + 4) * (dati.length + 1)));
        setMaximumSize(new Dimension(w, h));
        setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
        setBackground(Color.BLACK);
    }

    private void creaTabella() {
        removeAll();
        revalidate();

        dati = panel.getDataFromTab(nomeTab, nomeColonne);
        Casella cas;

        caselleTabella = new Casella[dati[0].length + 1][dati.length + 1];

        add(new Casella(dimCasellaVuota));
        for(int i = 0; i < nomeColonne.size(); i++) {
            add(new Casella(nomeColonne.get(i), dimCasella, FinalVariable.CELLA_COLONNA));
        }

        //Aggiunta dati
        for(int i = 0; i < dati[0].length + 1; i++) {
            
            if(i < dati[0].length) cas = new Casella(i, dimCasellaVuota, this, FinalVariable.NOT_BTN_AGGIUNGI);
            else cas = new Casella(dati[0].length, dimCasellaVuota, this, FinalVariable.IS_BTN_AGGIUNGI);
            
            caselleTabella[i][0] = cas;
            add(cas);
            
            for(int j = 1; j < dati.length + 1; j++) {
                
                if(i < dati[0].length) cas = new Casella(dati[j - 1][i], dimCasella, FinalVariable.CELLA_DATO);
                else cas = new Casella("", dimCasella, FinalVariable.CELLA_DATO);
                
                caselleTabella[i][j] = cas;
                add(cas);
            }
        }
    }

    public void modificaRiga(int numeroRiga) {

        caselleTabella[numeroRiga][0].modifica();
        for(int i = 1; i < caselleTabella[0].length; i++) {
            if(!sql.isAutoIn(nomeTab, nomeColonne.get(i - 1))) caselleTabella[numeroRiga][i].modifica();
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
    }

    public void eliminaRiga(int numeroRiga) {
        String query = "DELETE FROM " + nomeTab + " WHERE ";
        String[] dataTypes = sql.getDataTypes(nomeTab).toArray(new String[0]);

        for(int i = 0; i < nomeColonne.size(); i++) {
            if(dataTypes[i].equals("TEXT")) query += nomeColonne.get(i) + " = \'" + caselleTabella[numeroRiga][i + 1].getValoreLbl() + "\'";
            else query += nomeColonne.get(i) + " = " + caselleTabella[numeroRiga][i + 1].getValoreLbl();

            if(i < nomeColonne.size() - 1) query += " AND ";
            else query += " ";
        }

        System.out.println(query);
        //sql.
        
        creaTabella();
    }

    public void aggiornaRiga(int numeroRiga) {
        if(numeroRiga == dati[0].length) {
            aggiungiRiga(numeroRiga);
        } else {
            String query = "UPDATE " + nomeTab.toLowerCase() + " SET ";
            String[] dataTypes = sql.getDataTypes(nomeTab).toArray(new String[0]);

            for(int i = 0; i < nomeColonne.size(); i++) {
                if(dataTypes[i].equals("TEXT")) query += nomeColonne.get(i) + " = \'" + caselleTabella[numeroRiga][i + 1].getValoreTa() + "\'";
                else query += nomeColonne.get(i) + " = " + caselleTabella[numeroRiga][i + 1].getValoreTa();

                if(i < nomeColonne.size() - 1) query += ", ";
                else query += " ";
            }

            query += "WHERE ";

            for(int i = 0; i < nomeColonne.size(); i++) {
                if(dataTypes[i].equals("TEXT")) query += nomeColonne.get(i) + " = \'" + caselleTabella[numeroRiga][i + 1].getValoreLbl() + "\'";
                else query += nomeColonne.get(i) + " = " + caselleTabella[numeroRiga][i + 1].getValoreLbl();

                if(i < nomeColonne.size() - 1) query += " AND ";
                else query += " ";
            }

            System.out.println(query);
            //sql.addToDB(query);
        }
        creaTabella();
    }

    public void aggiungiRiga(int numeroRiga) {
        String query = "INSERT INTO " + nomeTab + " (";
        String[] dataTypes = sql.getDataTypes(nomeTab).toArray(new String[0]);

        for(int i = 0; i < nomeColonne.size(); i++) {
            query += nomeColonne.get(i);

            if(i < nomeColonne.size() - 1) query += " , ";
            else query += ")";
        }

        query += " VALUES (";

        for(int i = 0; i < nomeColonne.size(); i++) {
            if(dataTypes[i].equals("TEXT")) query += "\'" + caselleTabella[numeroRiga][i + 1].getValoreTa() + "\'";
            else query += caselleTabella[numeroRiga][i + 1].getValoreTa();

            if(i < nomeColonne.size() - 1) query += " , ";
            else query += ")";
        }

        System.out.println(query);
        //sql.addToDB(query);
    }
}

class Casella extends JPanel{

    private JLabel lbl;
    private JTextArea ta;
    private Dimension dim;
    private JButton btn_el, btn_mod, btn_ok, btn_canc, btn_agg;
    private String valore;

    public Casella(String valore, Dimension dim, Color backColor) {
        this.dim = dim;
        this.valore = valore;

        setPreferredSize(dim);
        setLayout(FinalVariable.FL_C2_2);

        creaTa(valore);
        creaLbl(valore, backColor);

        add(lbl);
        add(ta);
    }

    public Casella(int numRiga, Dimension dim, Tabella tabella, boolean aggiungi) {
        setPreferredSize(dim);
        Dimension btnDim = new Dimension(25, 25);

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

    private void creaLbl(String valore, Color backColor) {
        lbl = new JLabel(valore.toUpperCase(), SwingConstants.CENTER);
        lbl.setVerticalAlignment(SwingConstants.TOP);
        lbl.setPreferredSize(dim);
        lbl.setFont(new Font("Courier", Font.PLAIN, 20));
        lbl.setOpaque(true);
        lbl.setBackground(backColor);
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

    public BottoniCasella(int numeroRiga, int tipologia, Tabella tabella, Dimension dim) {
        this.numeroRiga = numeroRiga;
        this.tabella = tabella;
        this.tipologia = tipologia;
        this.dim = dim;

        setPreferredSize(dim);
        addActionListener(this);
        
        String pathToIcon;

        switch (tipologia) {
            case FinalVariable.BTN_ELIMINA: pathToIcon = "./Icons/trashIcon.png";                
            break;
            case FinalVariable.BTN_MODIFICA: pathToIcon = "./Icons/modifyIcon.png";
            break;
            case FinalVariable.BTN_CANC: pathToIcon = "./Icons/cancIcon.png";
            break;
            case FinalVariable.BTN_AGGIUNGI: pathToIcon = "./Icons/addIcon.png";
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
            //DEFAULT = btn_ok
            default: tabella.aggiornaRiga(numeroRiga);
            break;
        }
    }
}
