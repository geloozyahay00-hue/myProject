package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Invoice {
    private final int userId;
    private final String username;
    private final Car car;
    private final int days;

    private final boolean insurance;
    private final boolean gps;

    private final double base;
    private final double addOns;
    private final double vat;
    private final double total;

    private final LocalDateTime createdAt = LocalDateTime.now();

    public Invoice(int userId, String username, Car car, int days, boolean insurance, boolean gps) {
        this.userId = userId;
        this.username = username;
        this.car = car;
        this.days = days;
        this.insurance = insurance;
        this.gps = gps;

        this.base = car.getPricePerDay() * days;

        double insuranceCost = insurance ? (25.0 * days) : 0.0;
        double gpsCost = gps ? (10.0 * days) : 0.0;
        this.addOns = insuranceCost + gpsCost;

        double subTotal = base + addOns;
        this.vat = subTotal * 0.15; // 15% VAT
        this.total = subTotal + vat;
    }

    public double getTotal() { return total; }

    public String toText() {
        String dt = createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        StringBuilder sb = new StringBuilder();
        sb.append("=== CAR RENTAL INVOICE ===\n");
        sb.append("Date: ").append(dt).append("\n");
        sb.append("Customer ID: ").append(userId).append("\n");
        sb.append("Customer: ").append(username).append("\n\n");

        sb.append("Car: ").append(car.displayName()).append("\n");
        sb.append("Car Code: ").append(car.getCode()).append("\n");
        sb.append("Category: ").append(car.getCategory()).append("\n");
        sb.append("Days: ").append(days).append("\n");
        sb.append("Price/Day: ").append(String.format("%.2f", car.getPricePerDay())).append("\n\n");

        sb.append("Base: ").append(String.format("%.2f", base)).append("\n");
        sb.append("Add-ons:\n");
        sb.append("  Insurance: ").append(insurance ? "YES" : "NO").append("\n");
        sb.append("  GPS: ").append(gps ? "YES" : "NO").append("\n");
        sb.append("Add-ons Total: ").append(String.format("%.2f", addOns)).append("\n");
        sb.append("VAT (15%): ").append(String.format("%.2f", vat)).append("\n");
        sb.append("-------------------------\n");
        sb.append("TOTAL: ").append(String.format("%.2f", total)).append("\n");
        sb.append("=========================\n");
        return sb.toString();
    }
}
