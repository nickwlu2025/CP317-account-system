package accountsystem;

public class Product {
    private String name;
    private ProductCategory category;
    private double price;
    private String description;

    public Product(String name, ProductCategory category, double price, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return name + " - $" + String.format("%.2f", price) + " (" + category.getDisplayName() + ")";
    }
}
