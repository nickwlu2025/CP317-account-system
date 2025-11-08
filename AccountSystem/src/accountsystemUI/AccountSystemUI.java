package accountsystemUI;

import accountsystem.*;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AccountSystemUI {

    private List<User> users;
    private JFrame loginFrame;
    private User currentUser;

    public AccountSystemUI() {
        users = Helpers.loadUsers();
        createLoginScreen();
    }

    // ---------- LOGIN / CREATE SCREEN ----------
    private void createLoginScreen() {
        if (loginFrame != null) loginFrame.dispose();

        loginFrame = new JFrame("Account System");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(600, 400);
        loginFrame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        JButton createBtn = new JButton("Create Account");
        createBtn.setPreferredSize(new Dimension(180, 50));
        createBtn.addActionListener(e -> handleCreateAccount());

        JButton loginBtn = new JButton("Login");
        loginBtn.setPreferredSize(new Dimension(180, 50));
        loginBtn.addActionListener(e -> handleLogin());

        gbc.gridx = 0; gbc.gridy = 0; mainPanel.add(createBtn, gbc);
        gbc.gridx = 1; mainPanel.add(loginBtn, gbc);

        loginFrame.add(mainPanel, BorderLayout.CENTER);

        JButton backBtn = new JButton("Back");
        backBtn.setPreferredSize(new Dimension(100, 30));
        backBtn.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(loginFrame,
                    "Are you sure you want to exit?", "Confirm Exit", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) System.exit(0);
        });

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.add(backBtn);

        JButton showBtn = new JButton("Show Users");
        showBtn.setPreferredSize(new Dimension(120, 30));
        showBtn.addActionListener(e -> TestingUI.showFileContents());

        JButton deleteAllBtn = new JButton("Delete All");
        deleteAllBtn.setPreferredSize(new Dimension(120, 30));
        deleteAllBtn.addActionListener(e -> users = TestingUI.deleteAll(users));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.add(showBtn);
        rightPanel.add(deleteAllBtn);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);

        loginFrame.add(bottomPanel, BorderLayout.SOUTH);

        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);
    }

    // ---------- CREATE ACCOUNT ----------
    private void handleCreateAccount() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField(15);
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField);

        panel.add(new JLabel("Email:"));
        JTextField emailField = new JTextField(15);
        panel.add(emailField);

        int result = JOptionPane.showConfirmDialog(loginFrame, panel,
                "Create Account", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();
            String email = emailField.getText().trim();

            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(loginFrame, "All fields are required.");
                return;
            }

            users = AccountManagerUI.createAccount(users, username, password, email);

            boolean success = AccountManagerUI.login(users, username, password);
            if (success) {
                // Find the user object
                for (User user : users) {
                    if (user.getUsername().equals(username)) {
                        currentUser = user;
                        openDashboard(username);
                        break;
                    }
                }
            }
        }
    }

    // ---------- LOGIN ----------
    private void handleLogin() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.add(new JLabel("Username:"));
        JTextField usernameField = new JTextField(15);
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField(15);
        panel.add(passwordField);

        int result = JOptionPane.showConfirmDialog(loginFrame, panel,
                "Login", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            boolean success = AccountManagerUI.login(users, username, password);
            if (success) {
                // Find the user object
                for (User user : users) {
                    if (user.getUsername().equals(username)) {
                        currentUser = user;
                        openDashboard(username);
                        break;
                    }
                }
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid username or password.");
            }
        }
    }

    // ---------- DASHBOARD ----------
    private void openDashboard(String username) {
        loginFrame.dispose();

        JFrame dashFrame = new JFrame("Dashboard - " + username);
        dashFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dashFrame.setSize(900, 650);
        dashFrame.setLayout(new BorderLayout());

        // Top panel with buttons
        JPanel topPanel = new JPanel(new BorderLayout());

        JButton backBtn = new JButton("Back");
        backBtn.setPreferredSize(new Dimension(120, 30));
        backBtn.addActionListener(e -> {
            dashFrame.dispose();
            createLoginScreen();
        });

        JButton preferencesBtn = new JButton("Manage Preferences");
        preferencesBtn.setPreferredSize(new Dimension(160, 30));
        preferencesBtn.addActionListener(e -> {
            PreferencesUI.showPreferencesDialog(currentUser, users, dashFrame);
            // Refresh the product display after preferences are saved
            dashFrame.dispose();
            openDashboard(username);
        });

        JButton deleteBtn = new JButton("Delete Account");
        deleteBtn.setPreferredSize(new Dimension(120, 30));
        deleteBtn.addActionListener(e -> {
            String password = JOptionPane.showInputDialog(dashFrame, "Enter your password:");
            if (password != null) {
                users = AccountManagerUI.deleteAccount(users, username, password);
                dashFrame.dispose();
                createLoginScreen();
            }
        });

        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setPreferredSize(new Dimension(120, 30));
        logoutBtn.addActionListener(e -> {
            dashFrame.dispose();
            createLoginScreen();
        });

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        leftPanel.add(backBtn);
        leftPanel.add(preferencesBtn);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        rightPanel.add(deleteBtn);
        rightPanel.add(logoutBtn);

        topPanel.add(leftPanel, BorderLayout.WEST);
        topPanel.add(rightPanel, BorderLayout.EAST);
        dashFrame.add(topPanel, BorderLayout.NORTH);

        // Center panel - Product display
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Recommended Products for You");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(titleLabel, BorderLayout.NORTH);

        // Get filtered products based on user preferences
        List<Product> products = ProductManager.getFilteredProducts(currentUser);
        
        JPanel productsPanel = new JPanel(new GridLayout(0, 3, 15, 15));
        productsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        if (products.isEmpty()) {
            JLabel noProductsLabel = new JLabel("No products match your preferences. Set preferences to see products!");
            noProductsLabel.setHorizontalAlignment(SwingConstants.CENTER);
            centerPanel.add(noProductsLabel, BorderLayout.CENTER);
        } else {
            for (Product product : products) {
                JPanel productCard = createProductCard(product);
                productsPanel.add(productCard);
            }
            
            JScrollPane scrollPane = new JScrollPane(productsPanel);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            centerPanel.add(scrollPane, BorderLayout.CENTER);
        }
        
        // Show preference info at bottom of center panel
        String prefInfo = currentUser.getPreferences().isEmpty() 
            ? "No preferences set - Showing all products" 
            : "Filtered by: " + currentUser.getPreferences().size() + " categories";
        JLabel prefLabel = new JLabel(prefInfo);
        prefLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        prefLabel.setHorizontalAlignment(SwingConstants.CENTER);
        prefLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        centerPanel.add(prefLabel, BorderLayout.SOUTH);

        dashFrame.add(centerPanel, BorderLayout.CENTER);

        // Bottom testing buttons
        JPanel testingPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton showBtn = new JButton("Show Users");
        showBtn.setPreferredSize(new Dimension(120, 30));
        showBtn.addActionListener(e -> TestingUI.showFileContents());

        JButton deleteAllBtn = new JButton("Delete All");
        deleteAllBtn.setPreferredSize(new Dimension(120, 30));
        deleteAllBtn.addActionListener(e -> users = TestingUI.deleteAll(users));

        testingPanel.add(showBtn);
        testingPanel.add(deleteAllBtn);
        dashFrame.add(testingPanel, BorderLayout.SOUTH);

        dashFrame.setLocationRelativeTo(null);
        dashFrame.setVisible(true);
    }
    
    // Create a product card UI element
    private JPanel createProductCard(Product product) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.GRAY, 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        card.setBackground(Color.WHITE);
        
        JPanel infoPanel = new JPanel(new GridLayout(4, 1, 2, 2));
        infoPanel.setBackground(Color.WHITE);
        
        JLabel nameLabel = new JLabel(product.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel priceLabel = new JLabel("$" + String.format("%.2f", product.getPrice()));
        priceLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        priceLabel.setForeground(new Color(0, 128, 0));
        
        JLabel categoryLabel = new JLabel(product.getCategory().getDisplayName());
        categoryLabel.setFont(new Font("Arial", Font.ITALIC, 11));
        categoryLabel.setForeground(Color.GRAY);
        
        JLabel descLabel = new JLabel(product.getDescription());
        descLabel.setFont(new Font("Arial", Font.PLAIN, 10));
        
        infoPanel.add(nameLabel);
        infoPanel.add(priceLabel);
        infoPanel.add(categoryLabel);
        infoPanel.add(descLabel);
        
        card.add(infoPanel, BorderLayout.CENTER);
        
        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AccountSystemUI::new);
    }
}
