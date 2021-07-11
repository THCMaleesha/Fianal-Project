package com.test.Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class adminLoginClass {

    public void adminLogin(char[] password,JFrame msgFrame) {

        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT * FROM `admin_table` WHERE Password = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, String.valueOf(password));

            ResultSet resultSet = preparedStatement.executeQuery();

            int count = 0;
            while (resultSet.next()){
                count += 1;
            }
            if (count == 1){
                JOptionPane.showMessageDialog(msgFrame,"Login Successful !!!");
                adminFrame.dispose();
                new adminpageClass();

            }else{
                JOptionPane.showMessageDialog(msgFrame,"ERROR !!!");
            }
            resultSet.close();
            preparedStatement.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(msgFrame,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
        }
    }

    private JLabel adlgLabel;
    private JPasswordField passwordField1;
    private JButton signinButton;
    private JButton cancelButton;
    private JPanel adlgPanel;
    private JLabel pswLabel;
    private JFrame adminFrame;

    public adminLoginClass() {
        adminFrame = new JFrame("Customer Management Services");
        adminFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        adminFrame.setPreferredSize(new Dimension(600, 500));
        adminFrame.setResizable(true);

        adminFrame.add(adlgPanel);

        adminFrame.pack();
        adminFrame.setLocationRelativeTo(null);
        adminFrame.setVisible(true);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminFrame.dispose();
                new loginClass();
            }
        });
        signinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                char[] password = passwordField1.getPassword();
                JFrame msgframe = new JFrame();

                try {
                    adminLogin(password,msgframe);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });
    }
}
