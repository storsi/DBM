

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class Button extends JButton implements ActionListener{
    
    private String text;
    private Panel panel;
    private AggColonna ac;
    private int utilizzo;

    public Button(int width, int height, String text, Panel panel, int utilizzo) {
        this.text = text;
        this.utilizzo = utilizzo;
        this.panel = panel;

        setText(text.toUpperCase());
        setPreferredSize(new Dimension(width, height));
        addActionListener(this);

    }

    public Button(int width, int height, String text, AggColonna ac, Panel panel, int utilizzo) {
        this.ac = ac;
        this.text = text;
        this.utilizzo = utilizzo;
        this.panel = panel;

        setText(text.toUpperCase());
        setPreferredSize(new Dimension(width, height));
        addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (utilizzo) {
            case FinalVariable.CREA_TAB: panel.creaTabella();
            break;
            case FinalVariable.CANC_TAB: panel.mostraTabelle();
            break;
            case FinalVariable.AGG_COLONNA: panel.aggiungiColonnaTab();
            break;
            case FinalVariable.BOTT_TAB: panel.apriTabella(getText());
            break;
            case FinalVariable.BOTT_DB: panel.setDBscelto(text);
            break;
            case FinalVariable.ELIM_NUOVA_COLONNA: panel.eliminaAggiungiColonnaTab(ac);
            break;
            case FinalVariable.AGG_TAB:
            break;
            case FinalVariable.BOTT_AGG_TAB: panel.pagAggiungiTabella();
            break;
            case FinalVariable.BOTT_AGG_DB:
            break;
        }
    }
}
