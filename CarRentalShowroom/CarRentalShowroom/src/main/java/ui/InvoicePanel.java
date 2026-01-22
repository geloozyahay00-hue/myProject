package ui;

import model.Invoice;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;

public class InvoicePanel extends JPanel {

    public InvoicePanel(DashboardPanel dashboard, Invoice invoice) {
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Invoice", SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD, 18f));
        add(title, BorderLayout.NORTH);

        JTextArea area = new JTextArea(invoice.toText());
        area.setEditable(false);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        add(new JScrollPane(area), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton save = new JButton("Save as TXT");
        JButton home = new JButton("Back to Home");
        bottom.add(save);
        bottom.add(home);
        add(bottom, BorderLayout.SOUTH);

        home.addActionListener(e -> dashboard.goHome());

        save.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setSelectedFile(new java.io.File("invoice.txt"));
            int r = fc.showSaveDialog(this);
            if (r != JFileChooser.APPROVE_OPTION) return;

            try (FileWriter fw = new FileWriter(fc.getSelectedFile())) {
                fw.write(invoice.toText());
                JOptionPane.showMessageDialog(this, "Saved successfully!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Save failed: " + ex.getMessage());
            }
        });
    }
}
