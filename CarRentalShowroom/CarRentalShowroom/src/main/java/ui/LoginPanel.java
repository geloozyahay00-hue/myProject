package ui;

import app.AppFrame;
import model.User;
import repo.UserDao;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {

    public LoginPanel(AppFrame frame, UserDao userDao) {
        setLayout(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6,6,6,6);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Car Rental Showroom - Login");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 20f));

        JTextField username = new JTextField(18);
        JPasswordField password = new JPasswordField(18);

        JButton btnLogin = new JButton("Login");
        JButton btnRegister = new JButton("Create Account");

        g.gridx=0; g.gridy=0; g.gridwidth=2; add(title, g);

        g.gridwidth=1;
        g.gridx=0; g.gridy=1; add(new JLabel("Username:"), g);
        g.gridx=1; add(username, g);

        g.gridx=0; g.gridy=2; add(new JLabel("Password:"), g);
        g.gridx=1; add(password, g);

        g.gridx=0; g.gridy=3; add(btnLogin, g);
        g.gridx=1; add(btnRegister, g);

        btnLogin.addActionListener(e -> {
            User u = userDao.login(username.getText(), new String(password.getPassword()));
            if (u == null) {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
                return;
            }
            frame.showDashboard(u.getId(), u.getUsername());
        });

        btnRegister.addActionListener(e -> frame.showRegister());
    }
}
