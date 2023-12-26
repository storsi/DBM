package Oggetti;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Pagine.CreazioneTabella;
import Pagine.Panel;

public class AggColonna extends JPanel implements DocumentListener, ActionListener{

    private JTextArea ta;
    private ButtonGroup bg;
    private JRadioButton nullo, text, integer, real, blob, pk, autoIn, notNull;
    private Button btn_elimina;
    private int maxNumCar;
    private JPanel pnl_nome, pnl_tipo, pnl_caratt;
    private Panel panel;
    private boolean nomeOk, isAutoIn;
    private CreazioneTabella ct;
    
    public AggColonna(Panel panel, CreazioneTabella ct) {
        setLayout(new GridLayout(3, 1));

        maxNumCar = FinalVariable.MAX_LENGTH;
        this.panel = panel;
        nomeOk = true;
        isAutoIn = false;
        this.ct = ct;

        setPnlNome();
        setPnlTipo();
        setPnlCaratt();

        add(pnl_nome);
        add(pnl_tipo);
        add(pnl_caratt);
    }

    private void setPnlNome() {
        pnl_nome = new JPanel(FinalVariable.FL_L10_10);

        ta = new JTextArea();
        ta.setPreferredSize(new Dimension(150, 20));
        ta.getDocument().addDocumentListener(this);

        btn_elimina = new Button(100, 20, "ELIMINA", this, panel, FinalVariable.ELIM_NUOVA_COLONNA, ct);

        pnl_nome.add(new JLabel("Nome: "));
        pnl_nome.add(ta);
        pnl_nome.add(btn_elimina);
    }

    private void setPnlTipo() {
        pnl_tipo = new JPanel(FinalVariable.FL_L10_10);
        bg = new ButtonGroup();

        nullo = new RadioButton("NULLO", this, true, bg);
        text = new RadioButton("TEXT", this, false, bg);
        integer = new RadioButton("INTEGER", this, false, bg);
        real = new RadioButton("REAL", this, false, bg);
        blob = new RadioButton("BLOB", this, false, bg);

        pnl_tipo.add(new JLabel("Tipo: "));
        pnl_tipo.add(nullo);
        pnl_tipo.add(text);
        pnl_tipo.add(integer);
        pnl_tipo.add(real);
        pnl_tipo.add(blob);
    }

    private void setPnlCaratt() {
        pnl_caratt = new JPanel(FinalVariable.FL_L10_10);

        pk = new RadioButton("PRIMARY KEY", this, false);
        autoIn = new RadioButton("AUTOINCREMENT", this, false);
        notNull = new RadioButton("NOT NULL", this, false);

        pnl_caratt.add(new JLabel("Caratteristiche: "));
        pnl_caratt.add(pk);
        pnl_caratt.add(autoIn);
        pnl_caratt.add(notNull);
    }

    public boolean isPK() {
        return pk.isSelected();
    }

    public boolean isAutoIn() {
        return autoIn.isSelected();
    }

    public boolean isAllOk() {
        return nomeOk && !ta.getText().equals("");
    }

    public String getNome() {
        return ta.getText();
    }

    @Override
    public String toString() {

        String result = ta.getText() + " " + bg.getSelection().getActionCommand();
        if(pk.isSelected()) result += " UNIQUE ";
        if(notNull.isSelected() && !pk.isSelected()) result += " NOT NULL";

        result += ", ";

        return result;
    }

    private void checkTextArea() {
        if(panel.nomeColEsistente(this)) nomeOk = false;
        else nomeOk = true;

        if(!nomeOk) ta.setBackground(FinalVariable.ERROR_COLOR);
        else ta.setBackground(Color.WHITE);

        panel.aggNumColValide();
    }

    //Document listener

    @Override
    public void insertUpdate(DocumentEvent e) {
        panel.setBtnEnable(true);
        if(maxNumCar <= 0) {
            SwingUtilities.invokeLater(() -> {
                String t = ta.getText();
                ta.setText(t.substring(0, FinalVariable.MAX_LENGTH));
            });
        }
        else maxNumCar--;

        checkTextArea();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        maxNumCar++;
        if(maxNumCar == FinalVariable.MAX_LENGTH) panel.setBtnEnable(false);

        checkTextArea();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        
    }

    //Action listener

    @Override
    public void actionPerformed(ActionEvent e) {

        if(autoIn.isSelected() && !isAutoIn) {
            if(!panel.altriAutoIn(this)) autoIn.setSelected(false);
            else {
                isAutoIn = true;
                pk.setSelected(true);
            }
        }
        if(autoIn.isSelected()) {
            integer.setSelected(true);
            pk.setSelected(true);
        }
        else isAutoIn = false;
        if(pk.isSelected() || autoIn.isSelected()) notNull.setSelected(true);
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
