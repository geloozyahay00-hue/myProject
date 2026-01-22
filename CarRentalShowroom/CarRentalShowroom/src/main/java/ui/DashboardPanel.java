package ui;

import app.AppFrame;
import repo.CarInventory;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {

    private final AppFrame frame;
    private final CarInventory inventory;

    private int currentUserId;
    private String currentUsername;

    private final CardLayout contentLayout = new CardLayout();
    private final JPanel content = new JPanel(contentLayout);

    private final JPanel home = new JPanel(new BorderLayout());
    private final JLabel welcome = new JLabel("Welcome", SwingConstants.CENTER);

    public DashboardPanel(AppFrame frame, CarInventory inventory) {
        this.frame = frame;
        this.inventory = inventory;

        setLayout(new BorderLayout());

        JPanel top = new JPanel(new BorderLayout());
        JButton logout = new JButton("Logout");
        top.add(logout, BorderLayout.EAST);

        welcome.setFont(welcome.getFont().deriveFont(Font.BOLD, 18f));
        top.add(welcome, BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);

        buildHome();

        content.add(home, "HOME");
        add(content, BorderLayout.CENTER);

        logout.addActionListener(e -> frame.showLogin());
    }

    private void buildHome() {
        JPanel grid = new JPanel(new GridLayout(2, 2, 12, 12));
        grid.setBorder(BorderFactory.createEmptyBorder(18,18,18,18));

        for (CarInventory.Category c : inventory.getCategories()) {
            JButton b = new JButton(c.getName());
            b.setFont(b.getFont().deriveFont(Font.BOLD, 16f));
            b.addActionListener(e -> openCategory(c.getName()));
            grid.add(b);
        }

        home.add(new JLabel("Choose Category", SwingConstants.CENTER), BorderLayout.NORTH);
        home.add(grid, BorderLayout.CENTER);
    }

    private void openCategory(String categoryName) {
        CategoryPanel panel = new CategoryPanel(this, inventory.getByName(categoryName));
        content.add(panel, "CATEGORY_" + categoryName);
        contentLayout.show(content, "CATEGORY_" + categoryName);
    }

    public void openInvoice(InvoicePanel invoicePanel) {
        content.add(invoicePanel, "INVOICE");
        contentLayout.show(content, "INVOICE");
    }

    public void goHome() {
        contentLayout.show(content, "HOME");
    }

    public void setCurrentUser(int userId, String username) {
        this.currentUserId = userId;
        this.currentUsername = username;
        welcome.setText("Welcome, " + username + " (ID: " + userId + ")");
        goHome();
    }

    public int getCurrentUserId() { return currentUserId; }
    public String getCurrentUsername() { return currentUsername; }
}
