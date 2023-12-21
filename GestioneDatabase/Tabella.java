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

public class Tabella extends JPanel{
    //DATI.LENGTH INDICA LA QUANTITÀ DI DATI DI OGNI RIGA
    //DATI[0].LENGTH INDICA LA QUANTITÀ DI DATI PER OGNI COLONNA

    private Dimension dimCasella, dimCasellaVuota;
    private Casella[][] caselleTabella;
    
    public Tabella(ArrayList<String> nomeColonne, String[][] dati) {

        int w = (int)(FinalVariable.PANEL_WIDTH * 0.8), h = (int)(FinalVariable.PANEL_HEIGHT * 0.8);

        setPreferredSize(new Dimension(w, h / 16 * dati.length + (4 * dati.length + 1)));
        setMaximumSize(new Dimension(w, h));
        setLayout(new FlowLayout(FlowLayout.CENTER, 3, 3));
        setBackground(Color.BLACK);

        dimCasellaVuota = new Dimension(h / 8 + 5, h / 16);
        dimCasella = new Dimension((w - (int)dimCasellaVuota.getWidth() - ((nomeColonne.size() + 2) * 3)) / nomeColonne.size(), h / 16);
        caselleTabella = new Casella[dati[0].length][dati.length];
        Casella cas;
        
        //Aggiunta dei nomi delle colonne
        add(new Casella(dimCasellaVuota));
        for(int i = 0; i < nomeColonne.size(); i++) {
            add(new Casella(nomeColonne.get(i), dimCasella, FinalVariable.CELLA_COLONNA));
        }

        //Aggiunta dati
        for(int i = 0; i < dati[0].length; i++) {
            add(new Casella(i, dimCasellaVuota, this));
            for(int j = 0; j < dati.length; j++) {
                cas = new Casella(dati[j][i], dimCasella, FinalVariable.CELLA_DATO);
                add(cas);
                caselleTabella[i][j] = cas;
            }
        }
    }

    public void modificaRiga(int numeroRiga) {

        for(int i = 0; i < caselleTabella[0].length; i++) {
            caselleTabella[numeroRiga][i].modifica();
        }
    }
}

class Casella extends JPanel{

    private JLabel lbl;
    private JTextArea ta;
    private Dimension dim;
    private JButton btn_el, btn_mod;
    private String valore;

    public Casella(String valore, Dimension dim, Color backColor) {
        this.dim = dim;
        this.valore = valore;

        setPreferredSize(dim);
        setLayout(FinalVariable.FL_C2_2);

        creaTa(valore, backColor);
        creaLbl(valore, backColor);

        add(lbl);
    }

    public Casella(int numRiga, Dimension dim, Tabella tabella) {
        setPreferredSize(dim);
        Dimension btnDim = new Dimension(25, 25);

        btn_el = new BottoniCasella(numRiga, FinalVariable.BTN_ELIMINA, tabella, btnDim);
        btn_mod = new BottoniCasella(numRiga, FinalVariable.BTN_MODIFICA, tabella, btnDim);

        add(btn_el);
        add(btn_mod);
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

    private void creaTa(String valore, Color backColor) {
        ta = new JTextArea(valore.toUpperCase());
        ta.setPreferredSize(dim);
        ta.setFont(new Font("Courier", Font.PLAIN, 20));
        ta.setBackground(backColor);
    }

    public void visualizza() {
        remove(ta);
        revalidate();
        lbl.setText(valore);
        add(lbl);
    }

    public void modifica() {
        remove(lbl);
        revalidate();
        ta.setText(valore);
        add(ta);
    }
}

class BottoniCasella extends JButton implements ActionListener{

    private int numeroRiga, tipologia;
    private Tabella tabella;

    public BottoniCasella(int numeroRiga, int tipologia, Tabella tabella, Dimension dim) {
        this.numeroRiga = numeroRiga;
        this.tabella = tabella;
        this.tipologia = tipologia;

        setPreferredSize(dim);
        addActionListener(this);
        String pathToIcon;

        if(tipologia == FinalVariable.BTN_ELIMINA) pathToIcon = "./Icons/trashIcon.png";
        else pathToIcon = "./Icons/modifyIcon.png";  
        
        ImageIcon icon = new ImageIcon(pathToIcon);
        Image img = icon.getImage().getScaledInstance((int)dim.getWidth(), (int)dim.getHeight(), Image.SCALE_SMOOTH);

        setIcon(new ImageIcon(img));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(tipologia == FinalVariable.BTN_MODIFICA) tabella.modificaRiga(numeroRiga);
    }
}
