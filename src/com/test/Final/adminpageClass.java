package com.test.Final;

import javax.swing.*;
import java.awt.*;

public class adminpageClass {
    private JTextField cusIDTxt;
    private JButton GOButton;
    private JLabel crePayLabel;
    private JLabel cusIDLabel;
    private JLabel cusNameLabel;
    private JLabel totAmountLabel;
    private JLabel paymentLabel;
    private JLabel cusNameShowLabel;
    private JLabel totAmountShowLabel;
    private JTextField addAmountTxt;
    private JButton payButton;
    private JButton addCusButton;
    private JButton settingsButton;
    private JButton cancelButton;
    private JPanel adminPanel;
    private JFrame adminpageFrame;

    public adminpageClass() {
        adminpageFrame =new JFrame("Customer Management Services");
        adminpageFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        adminpageFrame.setPreferredSize(new Dimension(600,500));
        adminpageFrame.setResizable(true);

        adminpageFrame.add(adminPanel);

        adminpageFrame.pack();
        adminpageFrame.setLocationRelativeTo(null);
        adminpageFrame.setVisible(true);
    }
}
