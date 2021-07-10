package com.test.Final;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class userpageClass {
    private JPanel userPanel;
    private JLabel cusIDlabel;
    private JLabel cusNameLabel;
    private JLabel emailLabel;
    private JTable table1;
    private JButton settingsButton;
    private JButton buyNowButton;
    private JButton CANCELButton;
    private JLabel totamountLabel;
    private JLabel amountLabel;
    private JButton orderHisButton;
    private JScrollPane tablePanel;
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
    }

}

