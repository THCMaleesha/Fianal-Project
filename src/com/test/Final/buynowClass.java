package com.test.Final;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class buynowClass extends frameClass{

    private JFrame frame = null;

    static int price, qtyAmount;
    static String item,qty;

    public void setComboBox1items(){
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT item_name FROM `items_table`";

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

    public void setComboBox2items(){
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT qty_name FROM `quantities_table`";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                ComboBox2.addItem(resultSet.getString(1));

            }

            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }
    }

    public void createPrice(String item, int amt){
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT price_per_1 FROM items_table where item_name = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,item);
            //preparedStatement.setString(2,qunty);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                price = resultSet.getInt(1);
                price *= amt;

                textField1.setText(String.valueOf(price));
            }
            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }
    }

    public void addtoCart(String item, int amt,int Pri){
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "INSERT INTO `order_table` (count, item, quantity, price) VALUES (NULL, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,item);
            preparedStatement.setString(2, String.valueOf(amt));
            preparedStatement.setString(3, String.valueOf(Pri));

            try {
                preparedStatement.execute();

            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
            }
            preparedStatement.close();
            connection.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
        }

        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT * FROM `order_table`";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            billTable.setModel(new DefaultTableModel(
                    null,
                    new String[] {"Number", " Item", "Quantity", "Price" }
            ));

            int item_no = 1;


            while (resultSet.next()){
                String itemName = resultSet.getString(2);
                String amount = resultSet.getString(3);
                String pri = resultSet.getString(4);

                String data[] = {String.valueOf(item_no),itemName,amount,pri};
                DefaultTableModel tableModel = (DefaultTableModel) billTable.getModel();
                tableModel.addRow(data);
                item_no++;
            }
            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(billTable,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }

        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = " SELECT SUM(price) AS \"Total\" FROM `order_table`";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int total = resultSet.getInt(1);
                totamountTxt.setText(String.valueOf(total));
            }

            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }
}

    public void addtoOrders(String id,int price){
         try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "INSERT INTO `postponed_bill_table` (order_no , cus_ID, Amount) VALUES (NULL, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,id);
            preparedStatement.setString(2, String.valueOf(price));

            try {
                preparedStatement.execute();

            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
            }
            connection.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
        }
    }

    public void deleteNeworder(){
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "DELETE FROM order_table";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            try {
                preparedStatement.execute();

            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
            }
            connection.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
        }
    }


    private JLabel itemLabel;
    private JLabel quantityLabel;
    private JComboBox comboBox1;
    private JPanel buyPanel;
    private JLabel createLabel;
    private JButton GOButton;
    private JTextField textField1;
    private JButton ADDButton;
    private JTable billTable;
    private JButton CANCELButton;
    private JButton ADDTOORDERSButton;
    private JLabel priceLabel;
    private JLabel totamountLabel;
    private JLabel cusidLabel;
    private JLabel orderLabel;
    private JPanel tablePane;
    private JScrollPane scrlPane;
    private JTextField qtyTxt;
    private JComboBox ComboBox2;
    private JTextField totamountTxt;
    private JTextField idTxt;
    private JButton paynowButton;
    private JButton printButton;


    public buynowClass() {

        frame = setFrame(buyPanel,frame);

        setComboBox1items();
        setComboBox2items();


        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        GOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                item = comboBox1.getSelectedItem().toString();
                qty = ComboBox2.getSelectedItem().toString();
                qtyAmount = Integer.parseInt(qtyTxt.getText());

                try {
                    createPrice(item,qtyAmount);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        ADDButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addtoCart(item,qtyAmount,price);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        ADDTOORDERSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idTxt.getText();
                String totalAmount = totamountTxt.getText();
                try {
                    addtoOrders(id, Integer.parseInt(totalAmount));
                    deleteNeworder();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        paynowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Contact our keepers and pay your bill.\nThank YOU !!! Come Again !!!");
                deleteNeworder();
            }
        });
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
