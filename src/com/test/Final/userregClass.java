package com.test.Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class userregClass extends frameClass{

    private JFrame frame = null;

    public void userRegistration(String first_name, String last_name, String NIC, String email_add, char[] password, String contact_no, String address, JFrame msgFrame) {

        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "INSERT INTO `customers_table` (cus_ID, F_name, L_name, NIC, email_Address, Password, Contact_No, Address) VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,first_name);
            preparedStatement.setString(2,last_name);
            preparedStatement.setString(3,NIC);
            preparedStatement.setString(4,email_add);
            preparedStatement.setString(5, String.valueOf(password));
            preparedStatement.setString(6,contact_no);
            preparedStatement.setString(7,address);

            try {
                preparedStatement.execute();
                JOptionPane.showMessageDialog(msgFrame,"Congratulations !!!\nWelcome to Customer Services.");

            }catch (Exception exception){
                JOptionPane.showMessageDialog(msgFrame,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
            }
            connection.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(msgFrame,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
        }
    }

    private JPanel regPanel;
    private JLabel fnameLabel;
    private JTextField fnameTxt;
    private JTextField lnameTxt;
    private JTextField emailTxt;
    private JTextField contTxt;
    private JTextField addTxt;
    private JPasswordField passwordField1;
    private JLabel lnameLabel;
    private JLabel emailLabel;
    private JLabel passwordLabel;
    private JLabel contLabel;
    private JLabel addLabel;
    private JButton submitButton;
    private JButton cancelButton;
    private JLabel regLabel;
    private JLabel nicLabel;
    private JTextField NICtxt;

    public userregClass() {
        frame = setFrame(regPanel,frame);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new loginClass();
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String first_name,last_name,NIC,email_add,contact_no,address;
                char[] psword;
                JFrame msgFrame = new JFrame();

                first_name = fnameTxt.getText();
                last_name = lnameTxt.getText();
                NIC = NICtxt.getText();
                email_add = emailTxt.getText();
                psword = passwordField1.getPassword();
                contact_no = contTxt.getText();
                address = addTxt.getText();

                try {
                    userRegistration(first_name,last_name,NIC,email_add,psword,contact_no,address,msgFrame);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                fnameTxt.setText("");
                lnameTxt.setText("");
                NICtxt.setText("");
                emailTxt.setText("");
                passwordField1.setText("  ");
                contTxt.setText("");
                addTxt.setText("");

            }
        });
    }
}
