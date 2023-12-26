

import javax.swing.JFrame;

import Pagine.Panel;

public class Frame extends JFrame{

    public Frame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(new Panel());

        pack();
        setLocationRelativeTo(null);

        setVisible(true);
    }
}
