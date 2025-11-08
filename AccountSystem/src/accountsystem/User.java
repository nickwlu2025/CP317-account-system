package accountsystem;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String username;
    private String password;
    private String email;
    private String shippingAddress;
    private Set<ProductCategory> preferences;

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.shippingAddress = "";
        this.preferences = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getShippingAddress() {
        return shippingAddress;
    }
    
    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
    
    public Set<ProductCategory> getPreferences() {
        return preferences;
    }
    
    public void addPreference(ProductCategory category) {
        preferences.add(category);
    }
    
    public void removePreference(ProductCategory category) {
        preferences.remove(category);
    }
    
    public void setPreferences(Set<ProductCategory> preferences) {
        this.preferences = preferences;
    }
    
    public boolean hasPreference(ProductCategory category) {
        return preferences.contains(category);
    }
    
    // Convert preferences to CSV-friendly string
    public String getPreferencesAsString() {
        if (preferences.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (ProductCategory cat : preferences) {
            sb.append(cat.name()).append(";");
        }
        return sb.substring(0, sb.length() - 1); // Remove trailing semicolon
    }
    
    // Load preferences from CSV string
    public void loadPreferencesFromString(String prefsString) {
        preferences.clear();
        if (prefsString == null || prefsString.trim().isEmpty()) {
            return;
        }
        String[] parts = prefsString.split(";");
        for (String part : parts) {
            ProductCategory cat = ProductCategory.fromString(part.trim());
            if (cat != null) {
                preferences.add(cat);
            }
        }
    }
}
    
    public void setShippingAddress(String shippingAddress) {
    	this.shippingAddress = shippingAddress;
    }
    
}

