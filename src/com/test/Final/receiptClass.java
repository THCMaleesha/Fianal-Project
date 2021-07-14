package com.test.Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class receiptClass extends frameClass{

    private JFrame frame = null;

    public void createbalanceOrder(String cusIDcame,int balance){
        if (balance > 0){
            try {
                Connection connection = mysqlClass.getConnection();
                String sqlQuery = "INSERT INTO `orders_table` (order_no, cus_ID, Amount) VALUES (NULL, ?, ?)";

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

    public receiptClass(String cusIDcame, String cusNamee, int tot_amountcame, int paid_amountcame, String receiptNum, String ordID){

        frame = setFrame(receiptPanel,frame);

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
                createbalanceOrder(cusIDcame, balance);
            }
        });
    }
}
