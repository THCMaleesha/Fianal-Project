package com.test.Final;

//updating

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//extends the class from frameClass

public class usetSettingsClass extends frameClass{

    private JFrame frame = null;

    //method to update a profile
    public void updateDetails(String ID,String fname,String lname,String nic,String email,char[] passw,String cont,String address){

        //update user details in customers table with given id
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "UPDATE customers_table SET F_name = ?, L_name = ?, NIC = ?, email_Address = ?, Password = ?, Contact_No = ?, Address = ?" + "Where  cus_ID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(8,ID);
            preparedStatement.setString(1,fname);
            preparedStatement.setString(2,lname);
            preparedStatement.setString(3,nic);
            preparedStatement.setString(4,email);
            preparedStatement.setString(5, String.valueOf(passw));
            preparedStatement.setString(6,cont);
            preparedStatement.setString(7,address);

            try {
                preparedStatement.execute();
                JOptionPane.showMessageDialog(null,"Congratulations !!!\nRecords Updated.");

            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
            }
            connection.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
        }
    }

    private JLabel updatingLabel;
    private JLabel addressLabel;
    private JLabel contNoLabel;
    private JLabel pwLabel;
    private JLabel emailLabel;
    private JLabel nicLabel;
    private JLabel lnameLabel;
    private JLabel fnameLabel;
    private JTextField fnameTxt;
    private JPasswordField passwordField1;
    private JButton cancelButton;
    private JButton updateButton;
    private JPanel updatePanel;
    private JTextField lnameTxt;
    private JTextField nicTxt;
    private JTextField emailTxt;
    private JTextField contTxt;
    private JTextField addTxt;
    private JTextField idTxt;


    public usetSettingsClass(){

        //set frame
        frame = setFrame(updatePanel,frame);

        //update details
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String id,fname,lname, nic, email,cont, address;

                //get user given details
                id = idTxt.getText();
                fname = fnameTxt.getText();
                lname =lnameTxt.getText();
                nic = nicTxt.getText();
                email = emailTxt.getText();
                char [] passw = passwordField1.getPassword();
                cont = contTxt.getText();
                address = addTxt.getText();

                try {
                    updateDetails(id,fname,lname,nic,email,passw,cont,address);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                //set text boxes as null again
                fnameTxt.setText("");
                lnameTxt.setText("");
                nicTxt.setText("");
                emailTxt.setText("");
                passwordField1.setText("  ");
                contTxt.setText("");
                addTxt.setText("");
            }
        });

        //exit from the frame
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
             }
        });
    }
}
