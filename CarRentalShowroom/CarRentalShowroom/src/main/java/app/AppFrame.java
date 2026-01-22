package app;

import repo.CarInventory;
import repo.Database;
import repo.UserDao;
import ui.DashboardPanel;
import ui.LoginPanel;
import ui.RegisterPanel;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {

    private final CardLayout rootLayout = new CardLayout();
    private final JPanel root = new JPanel(rootLayout);

    private final Database db = new Database("car_rental.db");
    private final UserDao userDao = new UserDao(db);
    private final CarInventory inventory = new CarInventory();

    private final LoginPanel loginPanel;
    private final RegisterPanel registerPanel;
    private final DashboardPanel dashboardPanel;

    public AppFrame() {
        super("Car Rental Showroom");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 620);
        setLocationRelativeTo(null);

        db.init();
        inventory.seedData();

        loginPanel = new LoginPanel(this, userDao);
        registerPanel = new RegisterPanel(this, userDao);
        dashboardPanel = new DashboardPanel(this, inventory);

        root.add(loginPanel, "LOGIN");
        root.add(registerPanel, "REGISTER");
        root.add(dashboardPanel, "DASHBOARD");

        setContentPane(root);
        showLogin();
    }

    public void showLogin() { rootLayout.show(root, "LOGIN"); }
    public void showRegister() { rootLayout.show(root, "REGISTER"); }

    public void showDashboard(int userId, String username) {
        dashboardPanel.setCurrentUser(userId, username);
        rootLayout.show(root, "DASHBOARD");
    }
}
