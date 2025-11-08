package accountsystem;

public enum ProductCategory {
    ELECTRONICS("Electronics"),
    CLOTHING("Clothing"),
    BOOKS("Books"),
    HOME_GARDEN("Home & Garden"),
    SPORTS_OUTDOORS("Sports & Outdoors"),
    TOYS_GAMES("Toys & Games"),
    FOOD_BEVERAGE("Food & Beverage"),
    HEALTH_BEAUTY("Health & Beauty"),
    AUTOMOTIVE("Automotive"),
    PET_SUPPLIES("Pet Supplies");

    private final String displayName;

    ProductCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }

    // Helper method to convert from string (for CSV loading)
    public static ProductCategory fromString(String text) {
        for (ProductCategory category : ProductCategory.values()) {
            if (category.name().equalsIgnoreCase(text)) {
                return category;
            }
        }
        return null;
    }
}