package model;

public class Car {
    private final String code;
    private final String brand;
    private final String model;
    private final int year;
    private final double pricePerDay;
    private final String category; // Small, Medium, Large, VIP

    public Car(String code, String brand, String model, int year, double pricePerDay, String category) {
        this.code = code;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.pricePerDay = pricePerDay;
        this.category = category;
    }

    public String getCode() { return code; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public int getYear() { return year; }
    public double getPricePerDay() { return pricePerDay; }
    public String getCategory() { return category; }

    public String displayName() {
        return brand + " " + model + " (" + year + ")";
    }
}
