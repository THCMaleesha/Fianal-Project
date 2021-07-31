package com.test.Final;

//create a class for set frames in the system

import javax.swing.*;
import java.awt.*;

public class frameClass {

    public JFrame frame;

    //create a method to set the frame with passed panel and frame

    public JFrame setFrame(JPanel p, JFrame f){

        frame = f;

        frame = new JFrame("Customer Management Services");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1200, 750));
        frame.setResizable(true);

        frame.add(p);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        return frame;
    }
}
