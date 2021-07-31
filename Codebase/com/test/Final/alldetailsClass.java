package com.test.Final;

//all details

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//extends class from frameClass

public class alldetailsClass extends frameClass{

    //method to get postponed bills' details
    public void orderDetails(){

        //get all details of the postponed details and show them in a JTable
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT * FROM `postponed_bill_table`";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            table1.setModel(new DefaultTableModel(
                    null,
                    new String[] {"Order Number","Customer ID", "Amount"}
            ));
            while (resultSet.next()){
                String ordNo = resultSet.getString(1);
                String cusid = resultSet.getString(2);
                String Amo = resultSet.getString(3);

                String[] data = {ordNo,cusid,Amo};
                DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
                tableModel.addRow(data);
            }
            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(frame,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }
    }

    //method to get customer details
    public void userDetails(){

        //get all customers' details in the system and show them in a JTable
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT * FROM `customers_table`";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            table1.setModel(new DefaultTableModel(
                    null,
                    new String[] {"Customer ID","First Name", "Last Name","NIC","emailAddress","Contact No.","Address"}
            ));
            while (resultSet.next()){
                String cusid = resultSet.getString(1);
                String fname = resultSet.getString(2);
                String lname = resultSet.getString(3);
                String nic = resultSet.getString(4);
                String email = resultSet.getString(5);
                String ctno = resultSet.getString(7);
                String address = resultSet.getString(8);

                String[] data = {cusid,fname,lname,nic,email,ctno,address};
                DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
                tableModel.addRow(data);
            }
            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(frame,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }

    }

    //method to get items' details
    public void itemsDetails(){

        //get details from the items table & show in a JTable
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT items_table.item_code,items_table.item_name,quantities_table.qty_name,items_table.price_per_1 FROM items_table INNER JOIN quantities_table ON items_table.qty_no = quantities_table.qty_no";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);

            ResultSet resultSet = preparedStatement.executeQuery();

            table1.setModel(new DefaultTableModel(
                    null,
                    new String[] {"Item Code","Item", "Quantity Type","Price per 1"}
            ));
            while (resultSet.next()){
                String itemid = resultSet.getString(1);
                String item = resultSet.getString(2);
                String qty = resultSet.getString(3);
                String price = resultSet.getString(4);

                String[] data = {itemid,item,qty,price};
                DefaultTableModel tableModel = (DefaultTableModel) table1.getModel();
                tableModel.addRow(data);
            }
            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(frame,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }

    }

    //method to get details in to a Excel sheet
    public void toExcel(JTable table, File file){

        //insert JTable data in to a Excel sheet and download it
        try{
            TableModel model = table.getModel();
            FileWriter excel = new FileWriter(file);

            for(int i = 0; i < model.getColumnCount(); i++){
                excel.write(model.getColumnName(i) + "\t");
            }

            excel.write("\n");

            for(int i=0; i< model.getRowCount(); i++) {
                for(int j=0; j < model.getColumnCount(); j++) {
                    excel.write(model.getValueAt(i,j).toString()+"\t");
                }
                excel.write("\n");
            }

            excel.close();

        }catch(IOException e){ System.out.println(e); }
    }


    private JFrame frame = null;
    private JButton USERSButton;
    private JButton ORDERSButton;
    private JButton ITEMSButton;
    private JTable table1;
    private JLabel tableheadLabel;
    private JPanel allPanel;
    private JButton cancelButton;
    private JButton excelButton;

    public alldetailsClass(){

        //set frame
        frame = setFrame(allPanel,frame);

        //show postponed bill details
        ORDERSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tableheadLabel.setText("ORDERS");
                    orderDetails();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        //show customers details
        USERSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tableheadLabel.setText("USERS");
                    userDetails();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        //show items details
        ITEMSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    tableheadLabel.setText("ITEMS");
                    itemsDetails();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });

        //exit from the frame
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        //download the Excel sheet of requested data
        excelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toExcel(table1, new File("Data Table.xls"));
            }
        });
    }
}
