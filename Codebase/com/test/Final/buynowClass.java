package com.test.Final;

//order creation

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Logger;

//extends class from frameClass

public class buynowClass extends frameClass{

    private JFrame frame = null;

    //create static variables for use in whole class
    static int price, qtyAmount;
    static String item,qty;

    //method to get database details into a combo box
    public void setComboBox1items(){

        //get details from the items table's item name column and set them in a combo box
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

    //method to get database details into a combo box
    public void setComboBox2items(){

        //get details from the quantity table's quantity name column and set them in a combo box
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

    //method to create price
    public void createPrice(String item, int amt){

        //with the selected item name, quantity type & given quantity , get the price from the items table and create net amount
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

    //method to crate invoice
    public void addtoCart(String item, int amt,int Pri){

        /*insert in to order table, that item name,quantity and amount of the requested item
        add those details in a JTable as the invoice*/
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

    //method to create a postponed bill
    public void addtoOrders(String id,int price){

        //created invoice add to postponed bills table
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

    //method to delete the invoice
    public void deleteNeworder(){

        //delete the created invoice
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

    //method to print the invoice
    public void printinvoice(){

        //write the JTable data in a .txt file and download
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("invoice.txt"));
            PrintWriter fileWriter = new PrintWriter(bufferedWriter);

            for (int i = 0; i < billTable.getRowCount() + 1; i++) {
                String headers = String.valueOf(billTable.getColumnName(i));
                fileWriter.print(headers);
                fileWriter.print("\t");
            }

            fileWriter.println("");
            for (int i = 0; i < billTable.getRowCount(); ++i) {
                for (int j = 0; j < billTable.getColumnCount(); ++j) {
                    String s = billTable.getValueAt(i, j).toString();
                    fileWriter.print(s);
                    fileWriter.print("\t\t");
                }
                fileWriter.println("");
            }
            fileWriter.close();
            JOptionPane.showMessageDialog(null, "Success. File saved to invoice.txt");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Failure");

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

        //set frame
        frame = setFrame(buyPanel,frame);

        setComboBox1items();
        setComboBox2items();

        //exit from frame
        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        //get the price of a item
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

        //add to invoice
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

        //crete a postponed bill
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

        //pay the bill
        paynowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Contact our keepers and pay your bill.\nThank YOU !!! Come Again !!!");
                deleteNeworder();
            }
        });

        //print the invoice
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printinvoice();
            }
        });
    }
}
