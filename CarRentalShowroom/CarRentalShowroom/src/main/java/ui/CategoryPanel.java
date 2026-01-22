package ui;

import model.Car;
import model.Invoice;
import repo.CarInventory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CategoryPanel extends JPanel {

    public CategoryPanel(DashboardPanel dashboard, CarInventory.Category category) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Category: " + category.getName(), SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        String[] cols = {"Code", "Brand", "Model", "Year", "Price/Day"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        for (Car c : category.getCars()) {
            model.addRow(new Object[]{c.getCode(), c.getBrand(), c.getModel(), c.getYear(), c.getPricePerDay()});
        }

        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(6,6,6,6);
        g.fill = GridBagConstraints.HORIZONTAL;

        JLabel cheapest = new JLabel();
        JLabel expensive = new JLabel();

        Car ch = category.cheapest();
        Car ex = category.mostExpensive();
        cheapest.setText("Cheapest: " + (ch == null ? "-" : ch.displayName() + " $" + ch.getPricePerDay()+"/day"));
        expensive.setText("Most Expensive: " + (ex == null ? "-" : ex.displayName() + " $" + ex.getPricePerDay()+"/day"));

        JTextField days = new JTextField("1", 5);
        JCheckBox insurance = new JCheckBox("Insurance (+$25/day)");
        JCheckBox gps = new JCheckBox("GPS (+$10/day)");

        JButton rent = new JButton("Rent & Generate Invoice");
        JButton back = new JButton("Back");

        g.gridx=0; g.gridy=0; g.gridwidth=2; bottom.add(cheapest, g);
        g.gridy=1; bottom.add(expensive, g);

        g.gridwidth=1;
        g.gridx=0; g.gridy=2; bottom.add(new JLabel("Days:"), g);
        g.gridx=1; bottom.add(days, g);

        g.gridx=0; g.gridy=3; bottom.add(insurance, g);
        g.gridx=1; bottom.add(gps, g);

        g.gridx=0; g.gridy=4; bottom.add(rent, g);
        g.gridx=1; bottom.add(back, g);

        add(bottom, BorderLayout.SOUTH);

        back.addActionListener(e -> dashboard.goHome());

        rent.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Please select a car first.");
                return;
            }

            int d;
            try { d = Integer.parseInt(days.getText().trim()); }
            catch (Exception exx) {
                JOptionPane.showMessageDialog(this, "Days must be a number.");
                return;
            }
            if (d <= 0) {
                JOptionPane.showMessageDialog(this, "Days must be >= 1.");
                return;
            }

            String code = String.valueOf(model.getValueAt(row, 0));
            Car selected = null;
            for (Car c : category.getCars()) {
                if (c.getCode().equals(code)) { selected = c; break; }
            }
            if (selected == null) return;

            Invoice inv = new Invoice(
                    dashboard.getCurrentUserId(),
                    dashboard.getCurrentUsername(),
                    selected,
                    d,
                    insurance.isSelected(),
                    gps.isSelected()
            );

            dashboard.openInvoice(new InvoicePanel(dashboard, inv));
        });
    }
}
