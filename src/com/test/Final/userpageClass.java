package com.test.Final;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.sql.*;
import java.util.ArrayList;
import java.util.Vector;

public class userpageClass extends frameClass{

    private JFrame frame = null;
    static String ordNo,Amo;

    public void orderDetails(String cusID, JFrame frame){
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT * FROM `orders_table` WHERE cus_ID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,cusID);

            ResultSet resultSet = preparedStatement.executeQuery();

            table1.setModel(new DefaultTableModel(
                    null,
                    new String[] {"Order Number", "Amount"}
            ));
            while (resultSet.next()){
                ordNo = resultSet.getString(1);
                Amo = resultSet.getString(3);

                String data[] = {ordNo,Amo};
                    DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
                    tableModel.addRow(data);
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
                int total = resultSet.getInt(1);
                amountLabel.setText(String.valueOf(total));
            }

            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(frame,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }
    }

    private JPanel userPanel;
    private JLabel cusIDlabel;
    private JLabel cusNameLabel;
    private JLabel emailLabel;
    private JButton settingsButton;
    private JButton buyNowButton;
    private JButton CANCELButton;
    private JLabel totamountLabel;
    private JLabel amountLabel;
    private JButton orderHisButton;
    private JTable table1;
    private JPanel tablePanel;
    private JScrollPane tableScrollPane;
    private JButton paynowButton;

    public userpageClass(String id,String firstName,String lastName,String emailgot){

        frame = setFrame(userPanel,frame);

        cusIDlabel.setText(id);
        cusNameLabel.setText(firstName+ " " +lastName);
        emailLabel.setText(emailgot);


        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new loginClass();
            }
        });
        orderHisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();

                try {
                    orderDetails(id,frame);
                    getTotalamount(id,frame);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new usetSettingsClass();
            }
        });
        paynowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Contact our keepers and pay your bill.\nThank YOU !!! Come Again !!!");
            }
        });
        buyNowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new buynowClass();
            }
        });
    }

}

