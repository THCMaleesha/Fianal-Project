package com.test.Final;

import javax.swing.*;
import java.awt.*;

public class frameClass {
    public JFrame frame;

    public JFrame setFrame(JPanel p, JFrame f){

        frame = f;

        frame = new JFrame("Customer Management Services");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 500));
        frame.setResizable(true);

        frame.add(p);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }
}
