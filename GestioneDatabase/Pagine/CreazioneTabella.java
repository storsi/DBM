package Pagine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Oggetti.AggColonna;
import Oggetti.Button;
import Oggetti.FinalVariable;

public class CreazioneTabella extends JPanel implements DocumentListener{

    private JLabel lbl_titolo, lbl_numColValide;
    private String dbScelto;
    private JTextArea ta_nomeTab;
    private ArrayList<AggColonna> arrAc;
    private JPanel pnl_Ac, pnl_nomeTAb, pnl_opz;
    private JButton btn_creaTab;
    private Panel panel;
    private boolean uniqueName;
    
    public CreazioneTabella(String dbScelto, Panel panel) {
        this.dbScelto = dbScelto;
        this.panel = panel;
        
        arrAc = new ArrayList<AggColonna>();

        pnl_nomeTAb = new JPanel(FinalVariable.FL_C20_20);
        pnl_Ac = new JPanel();
        pnl_opz = new JPanel(FinalVariable.FL_C20_20);
        
        lbl_numColValide = new JLabel();

        setLayout(new BorderLayout());

        lbl_titolo = new JLabel("CREA UNA NUOVA TABELLA");
        
        creaPaginaAggiuntaCol();
    }

    private void creaPaginaAggiuntaCol() {
        removeAll();

        lbl_titolo.setText("CREA UNA TABELLA PER IL DATABASE: " + dbScelto.toUpperCase());

        pnl_nomeTAb.add(new JLabel("Nome Tabella: ", SwingConstants.CENTER));

        ta_nomeTab = new JTextArea();
        ta_nomeTab.getDocument().addDocumentListener(this);
        ta_nomeTab.setPreferredSize(new Dimension(200, 30));
        pnl_nomeTAb.add(ta_nomeTab);
        
        aggNumColValide();
        pnl_nomeTAb.add(lbl_numColValide);

        for(int i = 0; i < arrAc.size(); i++) {
            pnl_Ac.add(arrAc.get(i));
        }

        btn_creaTab = new Button(100, 50, "CREA", panel, FinalVariable.CREA_TAB);
        btn_creaTab.setEnabled(arrAc.size() == 1 && arrAc.get(0).isAllOk() || arrAc.size() > 1);


        pnl_opz.add(new Button(100, 50, "<html>AGGIUNGI<br>COLONNA</html>", panel, FinalVariable.AGG_COLONNA, this));
        pnl_opz.add(new Button(100, 50, "CANCELLA", panel, FinalVariable.CANC_TAB));
        pnl_opz.add(btn_creaTab);


        add(pnl_nomeTAb, BorderLayout.NORTH);
        add(pnl_Ac, BorderLayout.CENTER);
        add(pnl_opz, BorderLayout.SOUTH);
    }

    /**
     * Aggiorna lbl_numColValide che indica il numero di colonne valide rispetto al totale di colonne inserite
     */
    public void aggNumColValide() {
        lbl_numColValide.setText("Colonne valide: " + numColValide() + " / " + arrAc.size());

        if(numColValide() != arrAc.size())  btn_creaTab.setEnabled(false);
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

    public void tornaIndietro() {
        panel.mostraTabelle();
        panel.remove(this);
    }

    public void creaTabella() {
        boolean okIsOk = false;

        if(uniqueName) {
            for(int i = 0; i < arrAc.size(); i++) {
                if(arrAc.get(i).isPK()) {
                    okIsOk = true;
                    break;
                }
            }

            if(okIsOk) {
                String query = "CREATE TABLE " + ta_nomeTab.getText().toLowerCase() + " (";
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
                
                System.out.println(query);

                tornaIndietro();
            }
        
        } else ta_nomeTab.setBackground(FinalVariable.ERROR_COLOR);
    }

    private boolean isNameUnique() {
        String[] nomiTabelle = panel.getTableNames();

        if(ta_nomeTab.getText().equals("") || ta_nomeTab.getText() == null) {
            uniqueName = false;
            return false;
        }

        for(int i = 0; i < nomiTabelle.length; i++) {
                if(ta_nomeTab.getText().toLowerCase().equals(nomiTabelle[i].toLowerCase())) {
                    uniqueName = false;
                    return false;
                }
        }

        uniqueName = true;
        return true;
    }

    public void aggiungiColonnaTab() {
        if(arrAc.size() == 0 || (arrAc.size() > 0 && arrAc.get(arrAc.size() - 1).isAllOk())) {
            AggColonna ac = new AggColonna(panel, this);

            arrAc.add(ac);
            pnl_Ac.add(ac);

            btn_creaTab.setEnabled(false);
        }
    } 

    @Override
    public void insertUpdate(DocumentEvent e) {
        if(isNameUnique()) ta_nomeTab.setBackground(Color.WHITE);
        else ta_nomeTab.setBackground(FinalVariable.ERROR_COLOR);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if(isNameUnique()) ta_nomeTab.setBackground(Color.WHITE);
        else ta_nomeTab.setBackground(FinalVariable.ERROR_COLOR);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        
    }
}
