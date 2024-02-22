package Oggetti;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TextArea extends JScrollPane implements DocumentListener{

    public static final int V_SEMPRE = JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, V_SE_NECESSARIO = JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, V_MAI = JScrollPane.VERTICAL_SCROLLBAR_NEVER; 
    public static final int H_SEMPRE = JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS, H_SE_NECESSARIO = JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED, H_MAI = JScrollPane.HORIZONTAL_SCROLLBAR_NEVER; 

    private int maxWidth, maxHeight, minWidth, minHeight, actualWidth, actualHeight, caratteri;
    private JTextArea ta;

    public TextArea(float actualWidth, Font font) {
        maxWidth = maxHeight = minHeight = minWidth = -1;
        caratteri = 0;
        ta = new JTextArea();

        actualHeight = font.getSize() / 2 + 40;
        this.actualWidth = (int)actualWidth;
        ta.setMargin(new Insets(10, 10, 10, 10));
        ta.setFont(font);
        ta.getDocument().addDocumentListener(this);

        setViewportView(ta);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        setSize();
    }

    private void setSize() {
        if(minWidth > 0 && actualWidth < minWidth) actualWidth = minWidth;
        if(minHeight > 0 && actualHeight < minHeight) actualHeight = minHeight;
        if(maxWidth > 0 && actualWidth > maxWidth) actualWidth = maxWidth;
        if(maxHeight > 0 && actualHeight > maxHeight) actualHeight = maxHeight;

        setPreferredSize(new Dimension(actualWidth, actualHeight));
        ta.setPreferredSize(new Dimension((caratteri + 1) * FinalVariable.CHARACTER_WIDTH, actualHeight));
    }

    public void maxWidth(int maxWidth) {
        this.maxWidth = maxWidth;

        setSize();
    }

    public void minWidth(int minWidth) {
        this.minWidth = minWidth;

        setSize();
    }

    public void maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;

        setSize();
    }

    public void minHeight(int minHeight) {
        this.minHeight = minHeight;

        setSize();
    }

    public void mandaACapoRiga(boolean scelta) {
        ta.setLineWrap(scelta);
    }

    public void mandaACapoParola(boolean scelta) {
        ta.setWrapStyleWord(scelta);
    }

    public void setScrollPaneVerticalPolicy(int verticalPolicy) {
        setVerticalScrollBarPolicy(verticalPolicy);
    }

    public void setScrollPaneHorizontalPolicy(int horizontalPolicy) {
        setHorizontalScrollBarPolicy(horizontalPolicy);
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        caratteri++;

        setSize();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        if(caratteri > 0) caratteri--;

        setSize();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        
    }
}
