package com.test.Final;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class adminSettingClass extends frameClass{

    private JFrame frame = null;

    public void updateDetails(char[] passw ,char[] newpass){
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "UPDATE admin_table SET Password = ? Where Password = ? ";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1, String.valueOf(newpass));
            preparedStatement.setString(2, String.valueOf(passw));

            try {
                preparedStatement.execute();
                JOptionPane.showMessageDialog(null,"Congratulations !!!\nPassword Updated.");

            }catch (Exception exception){
                JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
            }
            connection.close();

        }catch (Exception exception){
            JOptionPane.showMessageDialog(null,"Oops !!!\nSomething went Wrong !!!"+exception.getMessage());
        }
    }

    private JPanel adminsetPanel;
    private JPasswordField passwordField1currentPw;
    private JPasswordField passwordField2newPw;
    private JButton updateButton;
    private JLabel updateLable;
    private JLabel currentpwLabel;
    private JLabel newpwLabel;
    private JButton cancelButton;

    public adminSettingClass(){
        frame = setFrame(adminsetPanel,frame);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                char[] current, newpw;

                current = passwordField1currentPw.getPassword();
                newpw = passwordField2newPw.getPassword();


                try {
                    updateDetails(current ,newpw);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

                passwordField1currentPw.setText( " ");
                passwordField2newPw.setText(" ");

            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
    }
}
