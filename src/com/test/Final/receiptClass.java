package com.test.Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton cancelButton;
    private JLabel ordersLabel;
    private JFrame receiptframe;

    public receiptClass(String cusIDcame, String cusNamee, int tot_amountcame, int paid_amountcame, String receiptNum, String ordID){

        receiptframe = new JFrame("Customer Management Services");
        receiptframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        receiptframe.setPreferredSize(new Dimension(600, 500));
        receiptframe.setResizable(true);

        receiptframe.add(receiptPanel);

        receiptframe.pack();
        receiptframe.setLocationRelativeTo(null);
        receiptframe.setVisible(true);

        receiptNoLabel.setText(receiptNum);
        cusIDprint.setText(cusIDcame);
        cusnamePrint.setText(cusNamee);
        totAmountPrint.setText(String.valueOf(tot_amountcame));
        paidAmountPrint.setText(String.valueOf(paid_amountcame));
        int balance = tot_amountcame - paid_amountcame;
        balancePrint.setText(String.valueOf(balance));
        ordersLabel.setText(ordID);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                receiptframe.dispose();
                new adminpageClass();
            }
        });
    }
}
