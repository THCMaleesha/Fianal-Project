package com.test.Final;

//item updates

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//extends class from frameClass

public class itemUpdatesClass extends frameClass{

    static String qtyNo;
    private JFrame frame = null;

    //method to get database details into a combo box
    public void setComboBox1items(){

        //get details from the quantity table's quantity name column and set them in a combo box
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT qty_name FROM `quantities_table`";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                comboBox1.addItem(resultSet.getString(1));

            }

            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }
    }

    //method to get database details into a combo box
    public void setComboBox2items(){

        //get details from the items table's item name column and set them in a combo box
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT item_name FROM `items_table`";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                combobox2.addItem(resultSet.getString(1));

            }

            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }
    }

    //method to add a item to store
    public void addItem(String name, String qty, String price){

        //add the item name, then select the quantity type
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT qty_no FROM quantities_table where qty_name = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,qty);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                qtyNo = resultSet.getString(1);
            }
            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }

        //insert item name, quantity type & price into items table
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "INSERT INTO `items_table` (item_code  , item_name, qty_no , price_per_1) VALUES (NULL, ?, ?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,name);
            preparedStatement.setString(2, qtyNo);
            preparedStatement.setString(3,price);

            try {
                preparedStatement.execute();
                JOptionPane.showMessageDialog(null,"Item added Successfully !!!");


            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
            }
            connection.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
        }
    }

    //method to update price
    public void updatePrice(String name, String price){

        //select the item with given item name and update the price
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "UPDATE items_table SET price_per_1 = ? Where  item_name = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,price);
            preparedStatement.setString(2,name);

            try {
                preparedStatement.execute();
                JOptionPane.showMessageDialog(null,"Price Updated !!!");

            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
            }
            connection.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
        }
    }


    private JTextField itemNameTxtadd;
    private JComboBox comboBox1;
    private JButton ADDITEMButton;
    private JButton cancelButton;
    private JButton updateButton;
    private JTextField priceTXTadd;
    private JTextField itemnameTxtupdate;
    private JTextField priceTxtupdate;
    private JPanel itemPanel;
    private JComboBox combobox2;
    private JButton allButton;

    public itemUpdatesClass(){

        //set frame
        frame = setFrame(itemPanel,frame);

        setComboBox1items();
        setComboBox2items();

        //exit from the frame
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        //add a item to store
        ADDITEMButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = itemNameTxtadd.getText();
                String qty = comboBox1.getSelectedItem().toString();
                String price = priceTXTadd.getText();

                try {
                    addItem(name,qty,price);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        //update an item price
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = combobox2.getSelectedItem().toString();
                String price = priceTxtupdate.getText();
                try {
                    updatePrice(name,price);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });

        //get all details of the customers, postponed bills and items
        allButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new alldetailsClass();
            }
        });
    }
}
