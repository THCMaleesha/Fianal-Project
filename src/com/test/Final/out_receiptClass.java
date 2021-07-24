package com.test.Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;


import javax.swing.*;

public class out_receiptClass extends frameClass{
    private JFrame frame = null;

    private JLabel receiptLabel;
    private JLabel receiptNoLabel;
    private JLabel cusIDLabel;
    private JLabel cusNameLAbel;
    private JLabel totamountLabel;
    private JLabel paidAmountLabel;
    private JLabel balanceLabel;
    private JLabel cusIDprint;
    private JLabel cusnamePrint;
    private JLabel totAmountPrint;
    private JLabel paidAmountPrint;
    private JLabel balancePrint;
    private JLabel orderlbl;
    private JLabel ordersLabel;
    private JButton cancelButton;
    private JButton CONFIRMPAYMENTButton;
    private JPanel outrecPanel;


    public void getReceipt(){
        try {
            PrintWriter out = new PrintWriter(new FileWriter("Receipt.txt"));
            out.println("\t------------------RECEIPT------------------\n\t\tThank You !!!  Come Again!!!\n");
            out.println("\t"+receiptLabel.getText()+"\t\t--\t"+receiptNoLabel.getText());
            out.println("\t"+cusIDLabel.getText()+"\t\t\t--\t"+cusIDprint.getText());
            out.println("\t"+cusNameLAbel.getText()+"\t\t--\t"+cusnamePrint.getText());
            out.println("\t"+orderlbl.getText()+"\t\t--\t"+ordersLabel.getText());
            out.println("\n");
            out.println("\t"+totamountLabel.getText()+"\t\t--\t"+totAmountPrint.getText());
            out.println("\t"+paidAmountLabel.getText()+"\t\t--\t"+paidAmountPrint.getText());
            out.println("\t"+cusNameLAbel.getText()+"\t\t--\t"+cusnamePrint.getText());
            out.println("\t"+balanceLabel.getText()+"\t\t\t\t--\t"+balancePrint.getText());
            out.flush();
            out.close();

        } catch (IOException e1) {
            System.err.println("Error occurred");
            e1.printStackTrace();
        }
    }


    public out_receiptClass(String cusIDcame, String cusNamee, int tot_amountcame, int paid_amountcame, String receiptNum, String ordID){

        frame = setFrame(outrecPanel,frame);

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
                frame.dispose();
                new adminpageClass();
            }
        });
        CONFIRMPAYMENTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getReceipt();
            }
        });
    }}

