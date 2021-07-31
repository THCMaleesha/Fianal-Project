package com.test.Final;

//admin page

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//extends the class from frameClass

public class adminpageClass extends frameClass{

    private JFrame frame = null;


    //create static variables for use in the whole class

    static int total = 0;
    static String ordId;
    static String cusId,cusName,receiptNumber;
    static int totAmount,paid_mount;
    static int out_receipt_no = 0;


    //method for create receipt
    public void createReceipt(String cus_ID, JFrame frame, String ord_ID, int paid_amount) {


        //insert data into the receipt table
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

    //method for delete postponed orders' bills
    public void delteOrder(String cus_ID, JFrame frame){

        //delete paid bill details from the postponed bill table
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "DELETE FROM `postponed_bill_table` WHERE `postponed_bill_table`.`cus_ID` = ?";

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

    //method for get receipt number
    public void getReceiptNum(JFrame frame, String ord_ID) {

        //get the receipt number from receipt table
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

    //method to calculate total amount
    public void getTotalamount(String cusID, JFrame frame) {

        //get the SUM value of amount column in the postponed bill table with a specific customer id
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = " SELECT SUM(Amount) AS \"Total\" FROM `postponed_bill_table` WHERE cus_ID = ?";

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

    //method to search postponed orders bills
    public void searchOrders(String cusID, JFrame frame) {

        //get customer name from the customers table & get order id from the postponed bill table
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT customers_table.F_name, customers_table.L_name,postponed_bill_table.order_no FROM customers_table INNER JOIN postponed_bill_table ON customers_table.cus_ID = postponed_bill_table.cus_ID where postponed_bill_table.cus_ID = ?";

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
    private JButton allButton;
    private JButton outpayment;

    public adminpageClass() {

        //set frame
        frame = setFrame(adminPanel,frame);

        //add customer to the system
        addCusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new userregClass();
            }
        });

        //search postponed bill details of a customer
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

        //to clear showed details
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cusIDTxt.setText(" ");
                cusNameShowLabel.setText( "xxxxxxxxxxxxxxxxxxxxxxxxx" );
                totAmountShowLabel.setText( "xxxxxxxxxxxxxxxxxxxxxxxxx" );
            }
        });

        //exit from the frame
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new loginClass();
            }
        });

        //to create a payment with a receipt
        payButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

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

        //update password
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new adminSettingClass();
            }
        });

        //create an order for a customer
        createOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new buynowClass();
            }
        });

        //add items to the store
        itemsetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new itemUpdatesClass();
            }
        });

        //get all database details
        allButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new alldetailsClass();
            }
        });

        //to create a payment of non postponed bills
        outpayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                out_receipt_no+=1;
                paid_mount = Integer.parseInt(addAmountTxt.getText());
                new out_receiptClass("NONE","OUT CUSTOMER",paid_mount,paid_mount,String.valueOf(out_receipt_no),"NONE");
            }
        });
    }
}
