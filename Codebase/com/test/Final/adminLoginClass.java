package com.test.Final;

//Admin's login page

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//extends the class from frameClass for set the frame

public class adminLoginClass extends frameClass{


    //method for authenticate login
    public void adminLogin(char[] password,JFrame msgFrame) {

        //select the details with user given email & password, if that details in the database give the access for login
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
                frame.dispose();
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

    private JFrame frame = null;
    private JLabel adlgLabel;
    private JPasswordField passwordField1;
    private JButton signinButton;
    private JButton cancelButton;
    private JPanel adlgPanel;
    private JLabel pswLabel;
    private JButton settingButton;

    public adminLoginClass() {

        //set the frame
        frame = setFrame(adlgPanel,frame);

        //exit from the frame
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new loginClass();
            }
        });

        //sign in to the system
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

        //update the password
        settingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new adminSettingClass();
            }
        });
    }
}
