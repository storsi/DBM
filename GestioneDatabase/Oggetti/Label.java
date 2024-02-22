package Oggetti;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Label extends JScrollPane{
    
    public static final int CENTER = SwingConstants.CENTER, LEFT = SwingConstants.LEFT, RIGHT = SwingConstants.RIGHT; 

    private int maxWidth, maxHeight, minWidth, minHeight, actualWidth, actualHeight, caratteri;
    private JLabel lbl;
    private String text;

    public Label(float actualWidth, float actualHeight, Font font, String text) {
        maxWidth = maxHeight = minHeight = minWidth = -1;
        caratteri = 0;
        this.text = text;
        lbl = new JLabel();

        actualHeight = font.getSize() / 2 + 40;
        this.actualWidth = (int)actualWidth;
        lbl.setFont(font);

        setViewportView(lbl);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        setSize();
    }

    private void setSize() {
        String[] parole = text.split(" ");
        int characterPerLine = actualWidth / FinalVariable.CHARACTER_WIDTH, caratteri = 0, righe = 1;

        for(int i = 0; i < parole.length; i++) {
            caratteri += parole[i].length();

            if(caratteri > characterPerLine) {
                caratteri -= parole[i].length();
                righe++;
                
            }
        }
    }

    public void setHorizontalAlignment(int alignment) {
        lbl.setHorizontalAlignment(alignment);
    }
}
