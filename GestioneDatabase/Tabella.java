import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Tabella extends JPanel{

    private Dimension dimCasella;
    
    public Tabella(ArrayList<String> nomeColonne, String[][] dati) {

        int w = (int)(FinalVariable.PANEL_WIDTH * 0.8), h = (int)(FinalVariable.PANEL_HEIGHT * 0.8);

        setPreferredSize(new Dimension(w, h));
        setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        setBackground(Color.BLACK);

        dimCasella = new Dimension((w - ((nomeColonne.size() + 1) * 5)) / nomeColonne.size(), h / 15);
        
        //Aggiunta dei nomi delle colonne
        for(int i = 0; i < nomeColonne.size(); i++) {
            add(new Casella(nomeColonne.get(i), false, dimCasella, FinalVariable.CELLA_COLONNA));
        }

        //Aggiunta dati
        for(int i = 0; i < dati.length; i++) {
            for(int j = 0; j < dati[i].length; j++) {
                add(new Casella(dati[i][j], false, dimCasella, FinalVariable.CELLA_DATO));
            }
        }
    }
}

class Casella extends JPanel{

    private JLabel lbl;
    private JTextArea ta;
    private Dimension dim;

    public Casella(String valore, boolean modifica, Dimension dim, Color backColor) {
        this.dim = dim;

        setPreferredSize(dim);

        if(modifica) creaTa(valore, backColor);
        else creaLbl(valore, backColor);
    }

    private void creaLbl(String valore, Color backColor) {
        lbl = new JLabel(valore.toUpperCase(), SwingConstants.CENTER);
        lbl.setVerticalAlignment(SwingConstants.TOP);
        lbl.setPreferredSize(dim);
        lbl.setFont(new Font("Courier", Font.PLAIN, 20));
        lbl.setOpaque(true);
        lbl.setBackground(backColor);
        

        add(lbl);
    }

    private void creaTa(String valore, Color backColor) {
        ta = new JTextArea(valore.toUpperCase());
        ta.setPreferredSize(dim);
        ta.setFont(new Font("Courier", Font.PLAIN, 20));
        ta.setBackground(backColor);

        add(ta);
    }
}
