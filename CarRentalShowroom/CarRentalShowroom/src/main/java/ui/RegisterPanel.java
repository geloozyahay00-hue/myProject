package ui;

import app.AppFrame;
import repo.UserDao;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {

    public RegisterPanel(AppFrame frame, UserDao userDao) {
        setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6,6,6,6);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create Account");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));

        JTextField id = new JTextField(18);
        JTextField username = new JTextField(18);
        JPasswordField password = new JPasswordField(18);

        JButton btnCreate = new JButton("Register");
        JButton btnBack = new JButton("Back");

        g.gridx=0; g.gridy=0; g.gridwidth=2; add(title, g);
        g.gridwidth=1;

        g.gridx=0; g.gridy=1; add(new JLabel("ID (number):"), g);
        g.gridx=1; add(id, g);

        g.gridx=0; g.gridy=2; add(new JLabel("Username:"), g);
        g.gridx=1; add(username, g);

        g.gridx=0; g.gridy=3; add(new JLabel("Password:"), g);
        g.gridx=1; add(password, g);

        g.gridx=0; g.gridy=4; add(btnCreate, g);
        g.gridx=1; add(btnBack, g);

        btnCreate.addActionListener(e -> {
            int uid;
            try { uid = Integer.parseInt(id.getText().trim()); }
            catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "ID must be a number.");
                return;
            }

            boolean ok = userDao.register(uid, username.getText(), new String(password.getPassword()));
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Registration failed (ID or Username may already exist).");
                return;
            }
            JOptionPane.showMessageDialog(this, "Account created successfully.");
            frame.showLogin();
        });

        btnBack.addActionListener(e -> frame.showLogin());
    }
}
