package accountsystemUI;

import accountsystem.*;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PreferencesUI {
    
    public static void showPreferencesDialog(User user, List<User> users, Component parent) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parent), 
                                     "Manage Preferences", true);
        dialog.setSize(500, 450);
        dialog.setLayout(new BorderLayout(10, 10));
        
        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Select Your Product Preferences");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);
        dialog.add(titlePanel, BorderLayout.NORTH);
        
        // Checkboxes panel
        JPanel checkboxPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        checkboxPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        Map<ProductCategory, JCheckBox> checkboxMap = new HashMap<>();
        
        for (ProductCategory category : ProductCategory.values()) {
            JCheckBox checkbox = new JCheckBox(category.getDisplayName());
            checkbox.setSelected(user.hasPreference(category));
            checkboxMap.put(category, checkbox);
            checkboxPanel.add(checkbox);
        }
        
        JScrollPane scrollPane = new JScrollPane(checkboxPanel);
        dialog.add(scrollPane, BorderLayout.CENTER);
        
        // Info label
        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("Products matching your preferences will appear on your homepage");
        infoLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        infoPanel.add(infoLabel);
        dialog.add(infoPanel, BorderLayout.SOUTH);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        
        JButton saveBtn = new JButton("Save Preferences");
        saveBtn.setPreferredSize(new Dimension(150, 35));
        saveBtn.addActionListener(e -> {
            // Update user preferences
            user.getPreferences().clear();
            for (Map.Entry<ProductCategory, JCheckBox> entry : checkboxMap.entrySet()) {
                if (entry.getValue().isSelected()) {
                    user.addPreference(entry.getKey());
                }
            }
            
            // Save to file
            Helpers.saveUsers(users);
            JOptionPane.showMessageDialog(dialog, 
                "Preferences saved successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });
        
        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setPreferredSize(new Dimension(150, 35));
        cancelBtn.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveBtn);
        buttonPanel.add(cancelBtn);
        
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(infoPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.add(southPanel, BorderLayout.SOUTH);
        
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }
}
