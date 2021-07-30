package com.test.Final;

//receipt

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;

//extends the class from frameClass

public class receiptClass extends frameClass{

    private JFrame frame = null;

    //method to create a new postponed bill with balance of the payment
    public void createbalanceOrder(String cusIDcame,int balance){

        //if the payment create a balance again create a new postponed bill with the balance
        if (balance > 0){
            try {
                Connection connection = mysqlClass.getConnection();
                String sqlQuery = "INSERT INTO `postponed_bill_table` (order_no, cus_ID, Amount) VALUES (NULL, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
                preparedStatement.setString(1,cusIDcame);
                preparedStatement.setString(2, String.valueOf(balance));

                try {
                    preparedStatement.execute();
                    JOptionPane.showMessageDialog(null,"Congratulations !!!\nPayment Successful !!!");

                }catch (Exception exception){
                    JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
                }
                connection.close();

            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
            }
        }

    }

    //method to print receipt
    public void getReceipt(){

        //write details of the receipt in a .txt file and download
        try {
            PrintWriter out = new PrintWriter(new FileWriter("Receipt.txt"));
            out.println("\t\t\t\t------------------RECEIPT------------------\n\t\tThank You !!!  Come Again!!!\n");
            out.println("\t"+receiptLabel.getText()+"\t\t--\t"+receiptNoLabel.getText());
            out.println("\t"+cusIDLabel.getText()+"\t\t--\t"+cusIDprint.getText());
            out.println("\t"+cusNameLAbel.getText()+"\t\t--\t"+cusnamePrint.getText());
            out.println("\t"+orderlbl.getText()+"\t\t--\t"+ordersLabel.getText());
            out.println("\n");
            out.println("\t"+totamountLabel.getText()+"\t\t--\t"+totAmountPrint.getText());
            out.println("\t"+paidAmountLabel.getText()+"\t\t--\t"+paidAmountPrint.getText());
            out.println("\t"+cusNameLAbel.getText()+"\t\t--\t"+cusnamePrint.getText());
            out.println("\t"+balanceLabel.getText()+"\t\t\t--\t"+balancePrint.getText());
            out.flush();
            out.close();

        } catch (IOException e1) {
            System.err.println("Error occurred");
            e1.printStackTrace();
        }
    }

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
    private JButton CONFIRMPAYMENTButton;
    private JButton cancelButton;
    private JLabel ordersLabel;
    private JLabel receiptLabel;
    private JLabel orderlbl;

    public receiptClass(String cusIDcame, String cusNamee, int tot_amountcame, int paid_amountcame, String receiptNum, String ordID){

        //set frame
        frame = setFrame(receiptPanel,frame);

        //set lable text as the receipt details
        receiptNoLabel.setText(receiptNum);
        cusIDprint.setText(cusIDcame);
        cusnamePrint.setText(cusNamee);
        totAmountPrint.setText(String.valueOf(tot_amountcame));
        paidAmountPrint.setText(String.valueOf(paid_amountcame));
        int balance = tot_amountcame - paid_amountcame;
        balancePrint.setText(String.valueOf(balance));
        ordersLabel.setText(ordID);

        //exit from the frame
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new adminpageClass();
            }
        });

        //confirm the payment and download the receipt
        CONFIRMPAYMENTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createbalanceOrder(cusIDcame, balance);
                getReceipt();
            }
        });
}}

