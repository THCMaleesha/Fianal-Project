package com.test.Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class adminpageClass extends frameClass{

    private JFrame frame = null;

    static int total = 0;
    static String ordId;
    static String cusId,cusName,receiptNumber;

    public void createReceipt(String cus_ID, JFrame frame, String ord_ID, int paid_amount) {

        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "INSERT INTO `receipt_table` (receipt_no, order_no, cus_ID, paid_amount) VALUES (NULL, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,ord_ID);
            preparedStatement.setString(2,cus_ID);
            preparedStatement.setString(3, String.valueOf(paid_amount));

            try {
                preparedStatement.execute();
                JOptionPane.showMessageDialog(frame,"Congratulations !!!\nPayment Successful !!!");

            }catch (Exception exception){
                JOptionPane.showMessageDialog(frame,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
            }
            connection.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(frame,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
        }
    }

    public void delteOrder(String cus_ID, JFrame frame){
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "DELETE FROM `orders_table` WHERE `orders_table`.`cus_ID` = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,cus_ID);

            try {
                preparedStatement.execute();
                JOptionPane.showMessageDialog(frame,"Congratulations !!!\nPayment Successful !!!");

            }catch (Exception exception){
                JOptionPane.showMessageDialog(frame,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
            }
            connection.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(frame,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
        }
    }

    public void getReceiptNum(JFrame frame, String ord_ID) {

        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT receipt_no FROM receipt_table where order_no = ?";


            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,ord_ID);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                receiptNumber = resultSet.getString(1);
            }

            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(frame,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }
    }

    public void getTotalamount(String cusID, JFrame frame) {

        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = " SELECT SUM(Amount) AS \"Total\" FROM `orders_table` WHERE cus_ID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,cusID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                total = resultSet.getInt(1);
                totAmountShowLabel.setText(String.valueOf(total));
                }

            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(frame,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }
    }

    public void searchOrders(String cusID, JFrame frame) {

        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT customers_table.F_name, customers_table.L_name,orders_table.order_no FROM customers_table INNER JOIN orders_table ON customers_table.cus_ID = orders_table.cus_ID where orders_table.cus_ID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,cusID);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                cusNameShowLabel.setText(resultSet.getString(1) + " " + resultSet.getString(2));
                ordId = resultSet.getString(3);
            }

            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(frame,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }
    }

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
    private JButton clearButton;
    private JButton createOrderButton;
    private JButton itemsetButton;

    public adminpageClass() {
        frame = setFrame(adminPanel,frame);

        addCusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new userregClass();
            }
        });
        GOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cusId = cusIDTxt.getText();
                JFrame msgframe = new JFrame();

                try {
                    searchOrders(cusId, msgframe);
                    getTotalamount(cusId, msgframe);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cusIDTxt.setText(" ");
                cusNameShowLabel.setText( "xxxxxxxxxxxxxxxxxxxxxxxxx" );
                totAmountShowLabel.setText( "xxxxxxxxxxxxxxxxxxxxxxxxx" );
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new loginClass();
            }
        });
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int totAmount,paid_mount;
                cusId = cusIDTxt.getText();
                cusName = cusNameShowLabel.getText();
                totAmount = total;
                paid_mount = Integer.parseInt(addAmountTxt.getText());
                JFrame msgframe = new JFrame();

                try {
                    createReceipt(cusId, msgframe, ordId,paid_mount);
                    delteOrder(cusId, msgframe);
                    getReceiptNum(msgframe, ordId);
                    new receiptClass(cusId,cusName,totAmount,paid_mount,receiptNumber,ordId);
                    frame.dispose();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new adminSettingClass();
            }
        });
        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new buynowClass();
            }
        });
        itemsetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new itemUpdatesClass();
            }
        });
    }
}
