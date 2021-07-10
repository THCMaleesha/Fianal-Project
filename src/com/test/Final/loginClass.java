package com.test.Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class loginClass {

    static String id,firstName,lastName,emailgot;

    public void userLogin(String email, char[] password,JFrame msgFrame) {

        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT * FROM `customers_table` WHERE email_Address = ? and Password = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2, String.valueOf(password));

            ResultSet resultSet = preparedStatement.executeQuery();

            int count = 0;
            while (resultSet.next()){
                count += 1;
            }
            if (count == 1){
                JOptionPane.showMessageDialog(msgFrame,"Login Successful !!!");
                userDetails(email,password,msgFrame);
                loginFrame.dispose();
                new userpageClass(id,firstName,lastName,emailgot);

            }else{
                JOptionPane.showMessageDialog(msgFrame,"ERROR !!!");
            }

            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(msgFrame,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }
    }

    public void userDetails(String email, char[] password,JFrame msgFrame) {

        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT * FROM `customers_table` WHERE email_Address = ? and Password = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,email);
            preparedStatement.setString(2, String.valueOf(password));

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                id = resultSet.getString(1);
                firstName = resultSet.getString(2);
                lastName = resultSet.getString(3);
                emailgot = resultSet.getString(5);
            }

            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(msgFrame,"Oops !!!\nSomething went Wrong !!!\n"+exception.getMessage());
        }
    }

    private JPanel loginPanel;
    private JLabel loginLabel;
    private JButton signupButton;
    private JButton signinButton;
    private JTextField emailTxt;
    private JButton cancelButton;
    private JButton adminButton;
    private JPasswordField passwordPasswordField;
    private final JFrame loginFrame;

    public loginClass() {
        loginFrame = new JFrame("Customer Management Services");
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginFrame.setPreferredSize(new Dimension(600, 500));
        loginFrame.setResizable(true);

        loginFrame.add(loginPanel);

        loginFrame.pack();
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                new userregClass();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                loginFrame.dispose();
            }
        });
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginFrame.dispose();
                new adminLoginClass();
            }
        });
        signinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String email = emailTxt.getText();
                char[] password = passwordPasswordField.getPassword();
                JFrame msgframe = new JFrame();

                try {
                    userLogin(email,password,msgframe);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });
    }
}