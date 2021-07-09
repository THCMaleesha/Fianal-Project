package com.test.Final;

import javax.swing.*;
import java.awt.*;

public class receiptClass {
    private JPanel receiptPanel;
    private JLabel cusIDLabel;
    private JLabel receiptNoLabel;
    private JLabel cusNameLAbel;
    private JLabel totamountLabel;
    private JLabel paidAmountLabel;
    private JLabel balanceLabel;
    private JLabel cusIDprint;
    private JLabel cusnamePrint;
    private JLabel totAmountPrint;
    private JLabel paidAmountPrint;
    private JLabel balancePrint;
    private JButton PRINTButton;
    private JFrame receiptframe;

    public receiptClass(){
        receiptframe = new JFrame("Customer Management Services");
        receiptframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        receiptframe.setPreferredSize(new Dimension(600, 500));
        receiptframe.setResizable(true);

        receiptframe.add(receiptPanel);

        receiptframe.pack();
        receiptframe.setLocationRelativeTo(null);
        receiptframe.setVisible(true);
    }
}
