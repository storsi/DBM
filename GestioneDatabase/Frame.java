

import javax.swing.JFrame;

public class Frame extends JFrame{

    public Frame(int width, int height) {

        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(new Panel(width, height));

        pack();
        setLocationRelativeTo(null);

        setVisible(true);
    }
}
