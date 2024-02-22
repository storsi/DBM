package Pagine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Oggetti.FinalVariable;
import Oggetti.TextArea;

import java.awt.BorderLayout;

public class LoginPage extends JPanel{

    private JPanel pnl_logIn, pnl_elements;
    private TextArea ta_mail, ta_pass;
    private JButton btn_prosegui;
    private JLabel lbl_titolo, lbl_descrizione;
    private int sectionWidth, sectionHeight;
    
    public LoginPage() {
        setLayout(FinalVariable.FL_R40_40);
        setPreferredSize(new Dimension(FinalVariable.PANEL_WIDTH, FinalVariable.PANEL_HEIGHT));

        setPanel();
    }

    private void setPanel() {
        pnl_logIn = new JPanel(new BorderLayout(0, 150));
        sectionWidth = (int)(FinalVariable.PANEL_WIDTH / 2.5);
        sectionHeight = (int)(FinalVariable.PANEL_HEIGHT / 1.2);
        pnl_logIn.setPreferredSize(new Dimension(sectionWidth, sectionHeight));

        lbl_titolo = new JLabel("BENVENUTO!", SwingConstants.CENTER);
        lbl_titolo.setFont(new Font("Courier", Font.PLAIN, 40));
        lbl_titolo.setPreferredSize(new Dimension(sectionWidth, 40));

        lbl_descrizione = new JLabel("Inserisci le credenziali", SwingConstants.CENTER);
        lbl_descrizione.setFont(new Font("Courier", Font.PLAIN, 20));
        lbl_descrizione.setPreferredSize(new Dimension(sectionWidth, 20));

        Font f = new Font("Courier", Font.PLAIN, 20); 
        

        ta_mail = new TextArea(sectionWidth * 0.8f, f);
        ta_mail.setHorizontalScrollBarPolicy(TextArea.H_SE_NECESSARIO);
        ta_mail.setVerticalScrollBarPolicy(TextArea.V_MAI);

        ta_pass = new TextArea(sectionWidth * 0.8f, f);
        ta_pass.setHorizontalScrollBarPolicy(TextArea.H_SE_NECESSARIO);
        ta_pass.setVerticalScrollBarPolicy(TextArea.V_MAI);

        btn_prosegui = new JButton("Prosegui");

        pnl_elements = new JPanel(FinalVariable.FL_C20_20);

        pnl_elements.add(lbl_titolo, BorderLayout.CENTER);
        pnl_elements.add(lbl_descrizione, BorderLayout.CENTER);
        pnl_elements.add(FinalVariable.hSpace100, BorderLayout.CENTER);
        pnl_elements.add(ta_mail, BorderLayout.CENTER);
        pnl_elements.add(ta_pass, BorderLayout.CENTER);
        pnl_elements.add(btn_prosegui, BorderLayout.CENTER);

        pnl_logIn.add(FinalVariable.hSpace100, BorderLayout.NORTH);
        pnl_logIn.add(pnl_elements, BorderLayout.CENTER);

        add(pnl_logIn);
    }
}
