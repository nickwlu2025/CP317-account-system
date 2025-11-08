package accountsystem;

import java.io.*;
import java.util.*;

public class Helpers {

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File("users.csv");

        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line = reader.readLine(); // read first line
                if (line == null) return users; // empty file
                
                // Check if header exists
                if (!line.toLowerCase().startsWith("username")) {
                    // If the first line isn't a header, treat it as a user
                    String[] parts = line.split(",", 4);
                    if (parts.length >= 3) {
                        User user = new User(parts[0].trim(), parts[1].trim(), parts[2].trim());
                        if (parts.length == 4) {
                            user.loadPreferencesFromString(parts[3].trim());
                        }
                        users.add(user);
                    }
                }

                // Now read the rest of the file
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",", 4);
                    if (parts.length >= 3) {
                        User user = new User(parts[0].trim(), parts[1].trim(), parts[2].trim());
                        if (parts.length == 4) {
                            user.loadPreferencesFromString(parts[3].trim());
                        }
                        users.add(user);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading users file: " + e.getMessage());
            }
        }

        return users;
    }

    public static void saveUsers(List<User> users) {
        File file = new File("users.csv");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Write header
            writer.write("Username,Password,Email,Preferences");
            writer.newLine();

            // Write users
            for (User user : users) {
                writer.write(user.getUsername() + "," + 
                           user.getPassword() + "," + 
                           user.getEmail() + "," + 
                           user.getPreferencesAsString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
}
