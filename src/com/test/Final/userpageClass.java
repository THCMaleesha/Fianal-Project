package com.test.Final;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class userpageClass {

    static String email, password;

    public void orderDetails(String cusID, JFrame frame){
        try {
            Connection connection = mysqlClass.getConnection();
            String sqlQuery = "SELECT * FROM `orders_table` WHERE cus_ID = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery);
            preparedStatement.setString(1,cusID);

            ResultSet resultSet = preparedStatement.executeQuery();
            DefaultTableModel table = (DefaultTableModel)table1.getModel();
            table.setRowCount(0);

            while (resultSet.next()){
                Object object[] = {resultSet.getString(1),resultSet.getString(2),resultSet.getString(3)};
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
    private JFrame userframe;

    public userpageClass(String id,String firstName,String lastName,String emailgot){

        userframe = new JFrame("Customer Management Services");
        userframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        userframe.setPreferredSize(new Dimension(600, 500));
        userframe.setResizable(true);

        userframe.add(userPanel);

        userframe.pack();
        userframe.setLocationRelativeTo(null);
        userframe.setVisible(true);

        cusIDlabel.setText(id);
        cusNameLabel.setText(firstName+lastName);
        emailLabel.setText(emailgot);



        CANCELButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                userframe.dispose();
                new loginClass();
            }
        });
        orderHisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                orderDetails(id,frame);

            }
        });
    }

}

