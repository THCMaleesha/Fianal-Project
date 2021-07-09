package com.test.Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class loginClass {

    public void userLogin(String email, char[] password,JFrame msgFrame) {

        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT * FROM `customer-table` WHERE email_Address = ? and Password = ?";

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
                loginFrame.dispose();
                new userpageClass();



            }else{
                JOptionPane.showMessageDialog(msgFrame,"ERROR !!!");
            }
            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(msgFrame,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
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
