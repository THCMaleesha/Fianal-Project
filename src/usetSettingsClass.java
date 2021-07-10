import javax.swing.*;
import java.awt.*;

public class usetSettingsClass {
    private JLabel updatingLabel;
    private JLabel addressLabel;
    private JLabel contNoLabel;
    private JLabel pwLabel;
    private JLabel emailLabel;
    private JLabel nicLabel;
    private JLabel lnameLabel;
    private JLabel fnameLabel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton cancelButton;
    private JButton updateButton;
    private JPanel updatePanel;
    private JFrame frame;

    public usetSettingsClass(){
        frame = new JFrame("Customer Management Services");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600, 500));
        frame.setResizable(true);

        frame.add(updatePanel);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
