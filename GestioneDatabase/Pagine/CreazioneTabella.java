package Pagine;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
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
    private ArrayList<AggColonn> arrAc;
    private JPanel pnl_ac, pnl_nometab, pnl_opz;
    private JButton btn_creaTab, btn_canc, btn_agg;
    private Panel panel;
    private boolean uniqueName;
    private JScrollPane sp;
    
    /* public CreazioneTabella(String dbScelto, Panel panel) {
        this.dbScelto = dbScelto;
        this.panel = panel;
        
        arrAc = new ArrayList<AggColonna>();
        arrAc.add(new AggColonna(panel, this));

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
        
        
        pnl_nomeTAb.add(lbl_numColValide);

        for(int i = 0; i < arrAc.size(); i++) {
            pnl_Ac.add(arrAc.get(i));
        }

        btn_creaTab = new Button(100, 50, "CREA", panel, FinalVariable.CREA_TAB);
        btn_creaTab.setEnabled(arrAc.size() == 1 && arrAc.get(0).isAllOk() || arrAc.size() > 1);
        aggNumColValide();


        pnl_opz.add(new Button(100, 50, "<html>AGGIUNGI<br>COLONNA</html>", panel, FinalVariable.AGG_COLONNA, this));
        pnl_opz.add(new Button(100, 50, "CANCELLA", panel, FinalVariable.CANC_TAB));
        pnl_opz.add(btn_creaTab);


        add(pnl_nomeTAb, BorderLayout.NORTH);
        add(pnl_Ac, BorderLayout.CENTER);
        add(pnl_opz, BorderLayout.SOUTH);
    }

    /**
     * Aggiorna lbl_numColValide che indica il numero di colonne valide rispetto al totale di colonne inserite
     
    public void aggNumColValide() {
        lbl_numColValide.setText("Colonne valide: " + numColValide() + " / " + arrAc.size());

        if(numColValide() != arrAc.size())  btn_creaTab.setEnabled(false);
    }

    /**
     * Conta il numero di colonne valide
     * @return Numero di colonne valide
     
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
        if(arrAc.size() == 0 || arrAc.get(arrAc.size() - 1).isAllOk()) {
            AggColonna ac = new AggColonna(panel, this);

            arrAc.add(ac);
            pnl_Ac.add(ac);

            pnl_Ac.repaint();

            btn_creaTab.setEnabled(false);
        }
    } */

    //Costruttore
    public CreazioneTabella(Panel panel) {
        setLayout(new BorderLayout(0, 20));

        this.panel = panel;

        arrAc = new ArrayList<AggColonn>();
        arrAc.add(new AggColonn(this));

        creaPagina();

        //Inserimento dei JPanel pnl_nometab (NORTH), pnl_ac (CENTER), pnl_opz (SOUTH)
        add(pnl_nometab, BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(pnl_opz, BorderLayout.SOUTH);
    }

    //Funzione per creare il tutto
    private void creaPagina() {
        pnl_nometab = new JPanel();
        pnl_ac = new JPanel(FinalVariable.FL_C3_3);
        pnl_opz = new JPanel();

        //Inizializzato il JTextArea e l'ho inserito nel JPanel pnl_nometab
        ta_nomeTab = new JTextArea();
        ta_nomeTab.setPreferredSize(new Dimension(200, 30));
        ta_nomeTab.getDocument().addDocumentListener(this);

        pnl_nometab.add(new JLabel("Nome tabella: "));
        pnl_nometab.add(ta_nomeTab);

        //Inserimento nel JPanel pnl_Ac di tutti gli elementi presenti nell'ArrayList<AggColonna> arrAc
        pnl_ac.setPreferredSize(new Dimension(550, 462));
        pnl_ac.setBackground(Color.BLACK);

        sp = new JScrollPane(pnl_ac, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        for(int i = 0; i < arrAc.size(); i++) {
            pnl_ac.add(arrAc.get(i));
        }

        //Inizializzazione dei pulsanti per la gestione della creazione della tabella + inseriemnto nel JPanel pnl_opz
        btn_agg = new Button(100, 50, "<html>AGGIUNGI<br>COLONNA</html>", this, FinalVariable.AGG_COLONNA);
        btn_canc = new Button(100, 50, "<html>CANCELLA<br>TABELLA</html>", panel, FinalVariable.CANC_TAB);
        btn_creaTab = new Button(100, 50, "<html>AGGIUNGI<br>TABELLA</html>", this, FinalVariable.CREA_TAB);

        pnl_opz.add(btn_agg);
        pnl_opz.add(btn_canc);
        pnl_opz.add(btn_creaTab);
    }

    //Aggiunta della possibilità di inserire una nuova colonna
    public void aggiungiColonna() {
        if(arrAc.get(arrAc.size() - 1).isAllOk()) {
            AggColonn ac = new AggColonn(this);

            arrAc.add(ac);
            pnl_ac.add(ac);
            
            /* int H = ((int)pnl_ac.getHeight() + 153 > 462) ? 462 : (int)pnl_ac.getHeight() + 153;
            

            pnl_ac.setPreferredSize(new Dimension(550, H));
            sp.setPreferredSize(new Dimension(550, H)); */
            pnl_ac.revalidate();
            pnl_ac.repaint();
        }
    }

    //Crea a tutti gli effetti la tabella
    public void creaTabella() {
        String query = "CREATE TABLE IF NOT EXISTS " + ta_nomeTab.getText().toLowerCase() + " (\n";
        String pk = "PRIMARY KEY(";
        int i;
        boolean virgola = false;

        for(i = 0; i < arrAc.size(); i++) {
            query += "\t" + arrAc.get(i).toString();

            if(i < arrAc.size() - 1) query += ",\n";
            
            if(arrAc.get(i).isPK() && !arrAc.get(i).isAutoIn()) {
                if(virgola) pk += ", " + arrAc.get(i).getNome();
                else pk += arrAc.get(i).getNome();
                
                virgola = true;
            }
        }

        if(virgola) query += ",\n\t" + pk + ")\n);";
        else query += "\n);";

        System.out.println(query);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        /* if(isNameUnique()) ta_nomeTab.setBackground(Color.WHITE);
        else ta_nomeTab.setBackground(FinalVariable.ERROR_COLOR); */
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        /* if(isNameUnique()) ta_nomeTab.setBackground(Color.WHITE);
        else ta_nomeTab.setBackground(FinalVariable.ERROR_COLOR); */
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        
    }
}

class AggColonn extends JPanel implements ActionListener, DocumentListener{

    private JTextArea ta;
    private ButtonGroup bg;
    private JRadioButton nullo, text, integer, real, blob, pk, autoIn, notNull, unique;
    private Button btn_elimina;
    private int maxNumCar;
    private JPanel pnl_nome, pnl_tipo, pnl_caratt;
    private Panel panel;
    private boolean nomeOk, isAutoIn;
    private CreazioneTabella ct;
    

    public AggColonn(CreazioneTabella ct) {
        setLayout(new GridLayout(3, 1));
        
        this.ct = ct;

        setUpPnlNome();
        setUpPnlTipo();
        setUpPnlCaratt();

        add(pnl_nome);
        add(pnl_tipo);
        add(pnl_caratt);
    }

    private void setUpPnlNome() {
        pnl_nome = new JPanel(FinalVariable.FL_L10_10);

        ta = new JTextArea();
        ta.setPreferredSize(new Dimension(200, 30));
        btn_elimina = new Button(80, 30, "ELIMINA", ct, FinalVariable.ELIM_NUOVA_COLONNA);
        btn_elimina.addActionListener(this);

        pnl_nome.add(new JLabel("Nome colonna: "));
        pnl_nome.add(ta);
        pnl_nome.add(btn_elimina);
    }

    private void setUpPnlTipo() {
        pnl_tipo = new JPanel(FinalVariable.FL_L10_10);

        bg = new ButtonGroup();

        nullo = new RadioButton("NULLO", this, true, bg);
        text = new RadioButton("TEXT", this, false, bg);
        integer = new RadioButton("INTEGER", this, false, bg);
        real = new RadioButton("REAL", this, false, bg);
        blob = new RadioButton("BLOB", this, false, bg);

        bg.add(nullo);
        bg.add(text);
        bg.add(integer);
        bg.add(real);
        bg.add(blob);

        pnl_tipo.add(new JLabel("Tipo colonna: "));
        pnl_tipo.add(nullo);
        pnl_tipo.add(text);
        pnl_tipo.add(integer);
        pnl_tipo.add(real);
        pnl_tipo.add(blob);
    }

    private void setUpPnlCaratt() {
        pnl_caratt = new JPanel(FinalVariable.FL_L10_10);

        pk = new RadioButton("PRIMARY KEY", this, false);
        autoIn = new RadioButton("AUTOINCREMENT", this, false);
        notNull = new RadioButton("NOT NULL", this, false);
        unique = new RadioButton("UNIQUE", this, false);

        pnl_caratt.add(new JLabel("Carattere colonna: "));
        pnl_caratt.add(pk);
        pnl_caratt.add(autoIn);
        pnl_caratt.add(notNull);
        pnl_caratt.add(unique);
    }

    public boolean isAllOk() {
        return !ta.getText().equals("") && ta.getText() != null;
    }

    public String toString() {
        String risposta = ta.getText() + " " + bg.getSelection().getActionCommand();
        
        if(pk.isSelected() && autoIn.isSelected()) risposta += " PRIMARY KEY AUTOINCREMENT";
        if(notNull.isSelected() && !pk.isSelected()) risposta += " NUT NULL";
        if(unique.isSelected() && !pk.isSelected()) risposta += " UNIQUE";

        return risposta;
    }

    public boolean isPK() {
        return pk.isSelected();
    }

    public boolean isAutoIn() {
        return autoIn.isSelected();
    }

    public String getNome() {
        return ta.getText();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(autoIn.isSelected()) {
            pk.setSelected(true);
            integer.setSelected(true);
        }

        if(pk.isSelected()) {
            notNull.setSelected(true);
            unique.setSelected(true);
        }
    }
}

class RadioButton extends JRadioButton{

    public RadioButton(String text, ActionListener al, boolean selezionato, ButtonGroup bg) {
        setText(text);
        setActionCommand(text);
        addActionListener(al);
        setSelected(selezionato);

        bg.add(this);
    }

    public RadioButton(String text, ActionListener al, boolean selezionato) {
        setText(text);
        setActionCommand(text);
        addActionListener(al);
        setSelected(selezionato);
    }
}
