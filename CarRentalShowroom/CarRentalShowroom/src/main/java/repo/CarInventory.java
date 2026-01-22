package repo;

import ds.*;
import model.Car;

public class CarInventory {

    public static class Category {
        private final String name;
        private final IndexableList<Car> cars;

        public Category(String name, IndexableList<Car> cars) {
            this.name = name;
            this.cars = cars;
        }

        public String getName() { return name; }
        public IndexableList<Car> getCars() { return cars; }

        public Car cheapest() {
            if (cars.size() == 0) return null;
            Car best = cars.get(0);
            for (Car c : cars) if (c.getPricePerDay() < best.getPricePerDay()) best = c;
            return best;
        }

        public Car mostExpensive() {
            if (cars.size() == 0) return null;
            Car best = cars.get(0);
            for (Car c : cars) if (c.getPricePerDay() > best.getPricePerDay()) best = c;
            return best;
        }
    }

    // ARRAY for categories
    private final Category[] categories = new Category[] {
            new Category("Small", new SinglyLinkedList<>()),
            new Category("Medium", new SinglyLinkedList<>()),
            new Category("Large", new CircularLinkedList<>()),
            new Category("VIP", new DoublyLinkedList<>())
    };

    public Category[] getCategories() { return categories; }

    public Category getByName(String name) {
        for (Category c : categories) if (c.getName().equalsIgnoreCase(name)) return c;
        return null;
    }

    public void seedData() {
        // Small (3-8 cars)
        getByName("Small").getCars().addLast(new Car("S-101","Toyota","Yaris",2022, 28, "Small"));
        getByName("Small").getCars().addLast(new Car("S-102","Hyundai","i10",2023, 24, "Small"));
        getByName("Small").getCars().addLast(new Car("S-103","Kia","Picanto",2022, 26, "Small"));

        // Medium
        getByName("Medium").getCars().addLast(new Car("M-201","Toyota","Corolla",2023, 38, "Medium"));
        getByName("Medium").getCars().addLast(new Car("M-202","Hyundai","Elantra",2023, 40, "Medium"));
        getByName("Medium").getCars().addLast(new Car("M-203","Nissan","Sentra",2022, 36, "Medium"));
        getByName("Medium").getCars().addLast(new Car("M-204","Honda","Civic",2023, 45, "Medium"));

        // Large (Circular)
        getByName("Large").getCars().addLast(new Car("L-301","Toyota","Camry",2023, 55, "Large"));
        getByName("Large").getCars().addLast(new Car("L-302","Nissan","Altima",2023, 52, "Large"));
        getByName("Large").getCars().addLast(new Car("L-303","Ford","Taurus",2022, 58, "Large"));
        getByName("Large").getCars().addLast(new Car("L-304","Chevrolet","Malibu",2022, 54, "Large"));

        // VIP (Doubly)
        getByName("VIP").getCars().addLast(new Car("V-901","Mercedes","E-Class",2024, 180, "VIP"));
        getByName("VIP").getCars().addLast(new Car("V-902","BMW","5 Series",2024, 190, "VIP"));
        getByName("VIP").getCars().addLast(new Car("V-903","Mercedes","S-Class",2024, 320, "VIP"));
    }
}
