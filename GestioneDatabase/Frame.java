

import javax.swing.JFrame;

import Pagine.NewPanel;

public class Frame extends JFrame{

    public Frame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        add(new NewPanel());

        pack();
        setLocationRelativeTo(null);

        setVisible(true);
    }
}
