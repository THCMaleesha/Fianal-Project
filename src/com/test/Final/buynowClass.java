package com.test.Final;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class buynowClass {

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

    public void createPrice(String item, String qunty, int amt){
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT items_table.price_per_1 FROM items_table INNER JOIN quantities_table ON items_table.qty_no = quantities_table.qty_no where items_table.item_name = ? and quantities_table.qty_name = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,item);
            preparedStatement.setString(2,qunty);

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
            String sqlQuery = "INSERT INTO `neworder_table` (count, item, quantity, price) VALUES (NULL, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,item);
            preparedStatement.setString(2, String.valueOf(amt));
            preparedStatement.setString(3, String.valueOf(Pri));

            try {
                preparedStatement.execute();

            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
            }
            connection.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
        }

        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT * FROM `neworder_table`";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            billTable.setModel(new DefaultTableModel(
                    null,
                    new String[] {"Number", " Item", "Quantity", "Price" }
            ));

            while (resultSet.next()){
                String item_no = resultSet.getString(1);
                String itemName = resultSet.getString(2);
                String amount = resultSet.getString(3);
                String pri = resultSet.getString(4);

                String data[] = {item_no,itemName,amount,pri};
                DefaultTableModel tableModel = (DefaultTableModel) billTable.getModel();
                tableModel.addRow(data);
            }
            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(billTable,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }

        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = " SELECT SUM(price) AS \"Total\" FROM `neworder_table`";

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
    private JButton clearButton;
    private JTextField totamountTxt;
    private JFrame buyframe;

    public buynowClass() {
        buyframe = new JFrame("Customer Management Services");
        buyframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        buyframe.setPreferredSize(new Dimension(600, 500));
        buyframe.setResizable(true);

        buyframe.add(buyPanel);

        buyframe.pack();
        buyframe.setLocationRelativeTo(null);
        buyframe.setVisible(true);

        setComboBox1items();
        setComboBox2items();


        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buyframe.dispose();
            }
        });

        GOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                item = comboBox1.getSelectedItem().toString();
                qty = ComboBox2.getSelectedItem().toString();
                qtyAmount = Integer.parseInt(qtyTxt.getText());

                try {
                    createPrice(item,qty,qtyAmount);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                qtyTxt.setText(" ");
                textField1.setText(" ");
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
    }
}
