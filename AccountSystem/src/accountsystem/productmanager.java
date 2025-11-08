package accountsystem;

import java.util.*;

public class ProductManager {
    
    // Sample products for demonstration
    private static List<Product> allProducts = new ArrayList<>();
    
    static {
    
        allProducts.add(new Product("Laptop", ProductCategory.ELECTRONICS, 999.99, "High-performance laptop"));
        allProducts.add(new Product("Smartphone", ProductCategory.ELECTRONICS, 699.99, "Latest smartphone"));
        allProducts.add(new Product("Headphones", ProductCategory.ELECTRONICS, 149.99, "Noise-cancelling headphones"));
        
        allProducts.add(new Product("T-Shirt", ProductCategory.CLOTHING, 19.99, "Cotton t-shirt"));
        allProducts.add(new Product("Jeans", ProductCategory.CLOTHING, 49.99, "Denim jeans"));
        allProducts.add(new Product("Jacket", ProductCategory.CLOTHING, 89.99, "Winter jacket"));
        
        allProducts.add(new Product("Novel", ProductCategory.BOOKS, 14.99, "Bestselling novel"));
        allProducts.add(new Product("Cookbook", ProductCategory.BOOKS, 24.99, "Recipe collection"));
        allProducts.add(new Product("Magazine", ProductCategory.BOOKS, 5.99, "Monthly magazine"));
        
        allProducts.add(new Product("Garden Tools", ProductCategory.HOME_GARDEN, 39.99, "Gardening kit"));
        allProducts.add(new Product("Lamp", ProductCategory.HOME_GARDEN, 29.99, "Desk lamp"));
        allProducts.add(new Product("Plant Pot", ProductCategory.HOME_GARDEN, 12.99, "Ceramic pot"));
        
        allProducts.add(new Product("Basketball", ProductCategory.SPORTS_OUTDOORS, 24.99, "Official size basketball"));
        allProducts.add(new Product("Tent", ProductCategory.SPORTS_OUTDOORS, 149.99, "4-person camping tent"));
        allProducts.add(new Product("Yoga Mat", ProductCategory.SPORTS_OUTDOORS, 19.99, "Exercise mat"));
        
        allProducts.add(new Product("Board Game", ProductCategory.TOYS_GAMES, 34.99, "Family board game"));
        allProducts.add(new Product("Puzzle", ProductCategory.TOYS_GAMES, 15.99, "1000-piece puzzle"));
        allProducts.add(new Product("Action Figure", ProductCategory.TOYS_GAMES, 19.99, "Collectible figure"));
        
        allProducts.add(new Product("Coffee Beans", ProductCategory.FOOD_BEVERAGE, 12.99, "Premium coffee"));
        allProducts.add(new Product("Tea Set", ProductCategory.FOOD_BEVERAGE, 29.99, "Variety tea collection"));
        allProducts.add(new Product("Snack Box", ProductCategory.FOOD_BEVERAGE, 24.99, "Assorted snacks"));
        
        allProducts.add(new Product("Shampoo", ProductCategory.HEALTH_BEAUTY, 8.99, "Hair care product"));
        allProducts.add(new Product("Moisturizer", ProductCategory.HEALTH_BEAUTY, 22.99, "Face moisturizer"));
        allProducts.add(new Product("Vitamins", ProductCategory.HEALTH_BEAUTY, 18.99, "Daily supplements"));
        
        
        allProducts.add(new Product("Floor Mats", ProductCategory.AUTOMOTIVE, 34.99, "All-weather mats"));
        
        allProducts.add(new Product("Dog Food", ProductCategory.PET_SUPPLIES, 39.99, "Premium dog food"));
        allProducts.add(new Product("Cat Toy", ProductCategory.PET_SUPPLIES, 9.99, "Interactive toy"));
        allProducts.add(new Product("Dog Bed", ProductCategory.PET_SUPPLIES, 44.99, "Comfortable pet bed"));
    }
    
    // Get all products
    public static List<Product> getAllProducts() {
        return new ArrayList<>(allProducts);
    }
    
    // Get products filtered by user preferences
    public static List<Product> getFilteredProducts(User user) {
        Set<ProductCategory> preferences = user.getPreferences();
        
        // If no preferences set, return all products
        if (preferences.isEmpty()) {
            return getAllProducts();
        }
        
        // Filter products based on preferences
        List<Product> filtered = new ArrayList<>();
        for (Product product : allProducts) {
            if (preferences.contains(product.getCategory())) {
                filtered.add(product);
            }
        }
        
        return filtered;
    }
    
    // Get products by specific category
    public static List<Product> getProductsByCategory(ProductCategory category) {
        List<Product> filtered = new ArrayList<>();
        for (Product product : allProducts) {
            if (product.getCategory() == category) {
                filtered.add(product);
            }
        }
        return filtered;
    }
}
