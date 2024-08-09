package sweet;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Color;
import javax.swing.BorderFactory;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MyApplication {

    private static ArrayList<User> userList = new ArrayList<>();
    private static ArrayList<StoreOwner> storeOwnerList = new ArrayList<>();
    private static ArrayList<Admin> adminList = new ArrayList<>();
    private static ArrayList<MaterialSupplier> materialSupplierList = new ArrayList<>();

    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JComboBox<String> roleComboBox;
    private JButton signUpButton;
    private JButton signInButton;
    private JTextArea outputArea;
    
    private String imagePath;

    public MyApplication() {
        loadUserData();  
        frame = new JFrame("Sweet Management System");
        frame.setSize(400, 350);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        JLabel roleLabel = new JLabel("Select your role:");
        roleLabel.setBounds(20, 20, 150, 25);
        frame.add(roleLabel);

        String[] roles = {"User", "Store Owner", "Admin", "Material Supplier"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setBounds(180, 20, 200, 25);
        frame.add(roleComboBox);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 60, 150, 25);
        frame.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(180, 60, 200, 25);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 100, 150, 25);
        frame.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(180, 100, 200, 25);
        frame.add(passwordField);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(100, 140, 100, 25);
        frame.add(signUpButton);

        signInButton = new JButton("Sign In");
        signInButton.setBounds(220, 140, 100, 25);
        frame.add(signInButton);

        outputArea = new JTextArea();
        outputArea.setBounds(20, 180, 350, 70);
        outputArea.setEditable(false);
        frame.add(outputArea);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSignUpFrame();
            }
        });

        signInButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSignIn();
            }
        });

        frame.setVisible(true);
    }

    private void openSignUpFrame() {
        JFrame signUpFrame = new JFrame("Sign Up");
        signUpFrame.setSize(400, 400);
        signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signUpFrame.setLayout(null);

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JTextField emailField = new JTextField();
        JTextField countryField = new JTextField();
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"User", "Store Owner", "Admin", "Material Supplier"});

        addCommonComponents(signUpFrame, usernameField, passwordField, emailField, countryField, roleComboBox);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(150, 220, 100, 25);
        signUpFrame.add(signUpButton);

        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                String email = emailField.getText();
                String country = countryField.getText();
                String role = (String) roleComboBox.getSelectedItem();

                if (username.isEmpty() || password.isEmpty() || email.isEmpty() || country.isEmpty()) {
                    JOptionPane.showMessageDialog(signUpFrame, "All fields must be filled.");
                    return;
                }

                boolean userExists = false;

                switch (role) {
                    case "User":
                        userExists = userList.stream().anyMatch(u -> u.getName().equals(username));
                        if (userExists) {
                            JOptionPane.showMessageDialog(signUpFrame, "User already exists.");
                        } else {
                            userList.add(new User(username, password, email, country));
                            saveUserData();
                            JOptionPane.showMessageDialog(signUpFrame, "User registered successfully.");
                        }
                        break;
                    case "Store Owner":
                        userExists = storeOwnerList.stream().anyMatch(u -> u.getUsername().equals(username));
                        if (userExists) {
                            JOptionPane.showMessageDialog(signUpFrame, "Store Owner already exists.");
                        } else {
                            storeOwnerList.add(new StoreOwner(username, password, email, country));
                            saveUserData();
                            JOptionPane.showMessageDialog(signUpFrame, "Store Owner registered successfully.");
                        }
                        break;
                    case "Admin":
                        userExists = adminList.stream().anyMatch(u -> u.getUsername().equals(username));
                        if (userExists) {
                            JOptionPane.showMessageDialog(signUpFrame, "Admin already exists.");
                        } else {
                            adminList.add(new Admin(username, password, email, country));
                            saveUserData();
                            JOptionPane.showMessageDialog(signUpFrame, "Admin registered successfully.");
                        }
                        break;
                    case "Material Supplier":
                        userExists = materialSupplierList.stream().anyMatch(u -> u.getUsername().equals(username));
                        if (userExists) {
                            JOptionPane.showMessageDialog(signUpFrame, "Material Supplier already exists.");
                        } else {
                            materialSupplierList.add(new MaterialSupplier(username, password, email, country));
                            saveUserData();
                            JOptionPane.showMessageDialog(signUpFrame, "Material Supplier registered successfully.");
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(signUpFrame, "Invalid role selected.");
                        break;
                }

                signUpFrame.dispose();
            }
        });

        signUpFrame.setVisible(true);
    }

    private void handleSignIn() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String role = (String) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty()) {
            outputArea.setText("Username and password cannot be empty.");
            return;
        }

        boolean userExists = false;

        switch (role) {
            case "User":
                userExists = userList.stream().anyMatch(u -> u.getName().equals(username) && u.getPassword().equals(password));
                break;
            case "Store Owner":
                userExists = storeOwnerList.stream().anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
                break;
            case "Admin":
                userExists = adminList.stream().anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
                break;
            case "Material Supplier":
                userExists = materialSupplierList.stream().anyMatch(u -> u.getUsername().equals(username) && u.getPassword().equals(password));
                break;
            default:
                outputArea.setText("Invalid role selected.");
                return;
        }

        if (userExists) {
            openRoleSpecificFrame(role, username);
        } else {
            outputArea.setText("Account not found. Please check your credentials.");
        }
    }

    private void openRoleSpecificFrame(String role, String username) {
        JFrame roleFrame = new JFrame(role + " Dashboard");
        roleFrame.setSize(600, 800); // حجم مبدئي
        roleFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        roleFrame.setLayout(null);

        JLabel titleLabel = new JLabel("Welcome " + username, SwingConstants.CENTER);
        titleLabel.setBounds(0, 20, 600, 25);
        roleFrame.add(titleLabel);

        if (role.equals("User")) {
            JButton settingsButton = new JButton("Settings");
            settingsButton.setBounds(50, 50, 100, 25);
            roleFrame.add(settingsButton);

            JButton postButton = new JButton("Post and Share");
            postButton.setBounds(160, 50, 150, 25);
            roleFrame.add(postButton);

            JButton buyFromStoresButton = new JButton("Buy from Stores");
            buyFromStoresButton.setBounds(320, 50, 150, 25);
            roleFrame.add(buyFromStoresButton);
            
            JButton browseButton = new JButton("Browse All Content");
            browseButton.setBounds(480, 50, 150, 25);
            roleFrame.add(browseButton);

            JPanel imagePanel = new JPanel();
            imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.Y_AXIS));
            JScrollPane scrollPane = new JScrollPane(imagePanel);
            scrollPane.setBounds(50, 100, 500, 600);
            roleFrame.add(scrollPane);

            loadPosts(username, imagePanel); // Load posts for the user

            postButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openPostFrame(username, imagePanel, roleFrame);
                }
            });

            settingsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openSettingsFrame(username);
                }
            });

            buyFromStoresButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openBuyFromStoresFrame();
                }
            });

            browseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openBrowseAllContentFrame();
                }
            });
        } else if (role.equals("Store Owner")) {
            JButton addProductButton = new JButton("Add Product");
            addProductButton.setBounds(50, 50, 150, 25);
            roleFrame.add(addProductButton);

            JPanel productPanel = new JPanel();
            productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
            JScrollPane scrollPane = new JScrollPane(productPanel);
            scrollPane.setBounds(50, 100, 500, 600);
            roleFrame.add(scrollPane);

            addProductButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    openAddProductFrame(username, productPanel);
                }
            });
        }

        roleFrame.setVisible(true);
    }

    ///////////////////////////////////////////////////////////////////////
    
    private void openBrowseAllContentFrame() {
        JFrame browseFrame = new JFrame("Browse All Content");
        browseFrame.setSize(800, 600); 
        browseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        browseFrame.setLayout(null);

        JLabel titleLabel = new JLabel("All Available Content", SwingConstants.CENTER);
        titleLabel.setBounds(0, 20, 800, 25);
        browseFrame.add(titleLabel);

        JLabel searchLabel = new JLabel("Search:");
        searchLabel.setBounds(50, 60, 100, 25);
        browseFrame.add(searchLabel);

        JTextField searchField = new JTextField();
        searchField.setBounds(150, 60, 200, 25);
        browseFrame.add(searchField);

        JLabel filterLabel = new JLabel("Filter by Dietary Needs:");
        filterLabel.setBounds(50, 100, 150, 25);
        browseFrame.add(filterLabel);

        JComboBox<String> dietaryFilterComboBox = new JComboBox<>(new String[] {"All", "Gluten-Free", "Nut-Free", "Dairy-Free"});
        dietaryFilterComboBox.setBounds(200, 100, 250, 25);
        browseFrame.add(dietaryFilterComboBox);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(470, 60, 100, 25);
        browseFrame.add(searchButton);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBounds(50, 140, 700, 400);
        browseFrame.add(scrollPane);

        // Load content initially
        loadAllContent(contentPanel, "", "All");

        // Add action listener to the search button
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchQuery = searchField.getText();
                String dietaryNeeds = (String) dietaryFilterComboBox.getSelectedItem();
                loadAllContent(contentPanel, searchQuery, dietaryNeeds);
            }
        });

        browseFrame.setVisible(true);
    }

    private void loadAllContent(JPanel panel, String searchQuery, String dietaryNeeds) {
        panel.removeAll(); // Clear existing content

        try (BufferedReader reader = new BufferedReader(new FileReader("content.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length == 3) { // نحتاج 3 أجزاء فقط: username, imagePath, description
                    String username = parts[0];
                    String imagePath = parts[1];
                    String description = parts[2];

                    // تحقق من تطابق النص المدخل مع وصف الحلوى
                    boolean matchesSearchQuery = description.toLowerCase().contains(searchQuery.toLowerCase());

                    // تحقق من تطابق معايير الحمية (إذا كانت هناك معايير محددة في الوصف، أضف تحقق هنا)
                    boolean matchesDietaryNeeds = dietaryNeeds.equals("All") || description.toLowerCase().contains(dietaryNeeds.toLowerCase());

                    // تحقق من تطابق جميع المعايير
                    if (matchesSearchQuery && matchesDietaryNeeds) {
                        JPanel contentItemPanel = new JPanel();
                        contentItemPanel.setLayout(new BoxLayout(contentItemPanel, BoxLayout.Y_AXIS));
                        contentItemPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // فاصل حول كل عنصر

                        // عرض اسم المستخدم
                        JLabel usernameLabel = new JLabel("User: " + username);
                        contentItemPanel.add(usernameLabel);

                        // عرض اسم الحلو (إذا كان هناك حقل خاص لاسم الحلو، أضفه هنا. حاليًا نستخدم الوصف كاسم الحلو)
                        JLabel sweetNameLabel = new JLabel("Sweet Name: " + description);
                        contentItemPanel.add(sweetNameLabel);

                        // عرض الصورة
                        ImageIcon imageIcon = new ImageIcon(imagePath);
                        Image image = imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH); // حجم أصغر
                        JLabel imageLabel = new JLabel(new ImageIcon(image));
                        contentItemPanel.add(imageLabel);

                        // عرض الوصف
                        JLabel descriptionLabel = new JLabel("Description: " + description);
                        contentItemPanel.add(descriptionLabel);

                        // إضافة المحتوى إلى اللوحة
                        panel.add(contentItemPanel);
                        panel.add(Box.createRigidArea(new Dimension(0, 20))); // فاصل بين العناصر
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception
        }

        panel.revalidate(); // Refresh the panel to show new content
        panel.repaint();
    }


//////////////////////////////////////////////////////////////////////////////////
    private void openAddProductFrame(String storeOwnerName, JPanel productPanel) {
        JFrame addProductFrame = new JFrame("Add Product");
        addProductFrame.setSize(400, 400); 
        addProductFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addProductFrame.setLayout(null);

        JLabel nameLabel = new JLabel("Product Name:");
        nameLabel.setBounds(30, 30, 100, 25);
        addProductFrame.add(nameLabel);

        JTextField nameField = new JTextField();
        nameField.setBounds(150, 30, 200, 25);
        addProductFrame.add(nameField);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(30, 70, 100, 25);
        addProductFrame.add(priceLabel);

        JTextField priceField = new JTextField();
        priceField.setBounds(150, 70, 200, 25);
        addProductFrame.add(priceField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(30, 110, 100, 25);
        addProductFrame.add(descriptionLabel);

        JTextField descriptionField = new JTextField();
        descriptionField.setBounds(150, 110, 200, 25);
        addProductFrame.add(descriptionField);

        JLabel imageLabel = new JLabel("Image:");
        imageLabel.setBounds(30, 150, 100, 25);
        addProductFrame.add(imageLabel);

        JButton chooseImageButton = new JButton("Choose Image");
        chooseImageButton.setBounds(150, 150, 150, 25);
        addProductFrame.add(chooseImageButton);

        JLabel imagePreview = new JLabel();
        imagePreview.setBounds(150, 180, 200, 100); // مساحة عرض الصورة
        addProductFrame.add(imagePreview);

        JButton saveButton = new JButton("Save Product");
        saveButton.setBounds(150, 300, 150, 25);
        addProductFrame.add(saveButton);

        final String[] imagePath = {null};

        chooseImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePath[0] = selectedFile.getAbsolutePath();
                    ImageIcon imageIcon = new ImageIcon(imagePath[0]);
                    Image image = imageIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                    imagePreview.setIcon(new ImageIcon(image));
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = nameField.getText();
                String productPrice = priceField.getText();
                String productDescription = descriptionField.getText();

                if (!productName.isEmpty() && !productPrice.isEmpty() && !productDescription.isEmpty() && imagePath[0] != null) {
                    saveProduct(storeOwnerName, productName, productPrice, productDescription, imagePath[0]);

                    // Refresh the productPanel to display the new product
                    loadStoreOwnerProducts(storeOwnerName, productPanel);

                    addProductFrame.dispose(); // إغلاق النافذة بعد الحفظ
                } else {
                    JOptionPane.showMessageDialog(addProductFrame, "Please fill in all fields and choose an image.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        addProductFrame.setVisible(true);
    }

/////////////////////////////////////////////////////////////////////////////////
    private void saveProduct(String storeOwnerName, String productName, String productPrice, String productDescription, String imagePath) {
        try {
            FileWriter fw = new FileWriter("products.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(storeOwnerName + "|" + productName + "|" + productPrice + "|" + productDescription + "|" + imagePath);
            bw.newLine();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
///////////////////////////////////////////////////////////
    private void loadStoreOwnerProducts(String storeOwnerName, JPanel productPanel) {
        productPanel.removeAll();          

        try {
            FileReader fr = new FileReader("products.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] productDetails = line.split("\\|");
                if (productDetails[0].equals(storeOwnerName)) {
                    JPanel productItemPanel = new JPanel();
                    productItemPanel.setLayout(new BoxLayout(productItemPanel, BoxLayout.Y_AXIS));
                    productItemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    // عرض الصورة
                    if (productDetails.length > 4) {
                        ImageIcon imageIcon = new ImageIcon(productDetails[4]);
                        Image image = imageIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                        JLabel imageLabel = new JLabel(new ImageIcon(image));
                        productItemPanel.add(imageLabel);
                    }

                    // عرض الاسم والسعر
                    JLabel nameLabel = new JLabel("Name: " + productDetails[1]);
                    productItemPanel.add(nameLabel);
                    
                    JLabel priceLabel = new JLabel("Price: " + productDetails[2]);
                    productItemPanel.add(priceLabel);

                    // عرض الوصف
                    JLabel descriptionLabel = new JLabel("Description: " + productDetails[3]);
                    productItemPanel.add(descriptionLabel);

                    // إضافة المنتج إلى اللوحة الرئيسية
                    productPanel.add(productItemPanel);
                }
            }
            br.close();
            productPanel.revalidate(); // تحديث اللوحة لعرض المنتجات الجديدة
            productPanel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    ///////////////////////////////////////////////////////////

    private void openBuyFromStoresFrame() {
        JFrame buyFromStoresFrame = new JFrame("Buy from Stores");
        buyFromStoresFrame.setSize(800, 800);
        buyFromStoresFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        buyFromStoresFrame.setLayout(null);

        JLabel selectStoreLabel = new JLabel("Select Store Owner:");
        selectStoreLabel.setBounds(50, 30, 150, 25);
        buyFromStoresFrame.add(selectStoreLabel);

        JComboBox<String> storeOwnerComboBox = new JComboBox<>();
        storeOwnerComboBox.setBounds(200, 30, 250, 25);
        buyFromStoresFrame.add(storeOwnerComboBox);

        JLabel searchLabel = new JLabel("Search Products:");
        searchLabel.setBounds(50, 70, 150, 25);
        buyFromStoresFrame.add(searchLabel);

        JTextField searchField = new JTextField();
        searchField.setBounds(200, 70, 250, 25);
        buyFromStoresFrame.add(searchField);

        JLabel filterLabel = new JLabel("Filter by Dietary Needs:");
        filterLabel.setBounds(50, 110, 150, 25);
        buyFromStoresFrame.add(filterLabel);

        JComboBox<String> dietaryFilterComboBox = new JComboBox<>(new String[] {"All", "Gluten-Free", "Nut-Free", "Dairy-Free"});
        dietaryFilterComboBox.setBounds(200, 110, 250, 25);
        buyFromStoresFrame.add(dietaryFilterComboBox);

        JPanel productPanel = new JPanel();
        productPanel.setLayout(new BoxLayout(productPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(productPanel);
        scrollPane.setBounds(50, 150, 700, 500);
        buyFromStoresFrame.add(scrollPane);

        JButton loadProductsButton = new JButton("Load Products");
        loadProductsButton.setBounds(50, 660, 150, 25);
        buyFromStoresFrame.add(loadProductsButton);

        JButton viewCartButton = new JButton("View Cart");
        viewCartButton.setBounds(220, 660, 150, 25);
        buyFromStoresFrame.add(viewCartButton);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setBounds(390, 660, 150, 25);
        buyFromStoresFrame.add(checkoutButton);

        List<String[]> cart = new ArrayList<>();

        loadStoreOwners(storeOwnerComboBox);

        loadProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productPanel.removeAll();
                String selectedOwner = (String) storeOwnerComboBox.getSelectedItem();
                String searchQuery = searchField.getText().toLowerCase();
                String dietaryFilter = (String) dietaryFilterComboBox.getSelectedItem();

                if (selectedOwner != null) {
                    loadProductsForStoreOwner(selectedOwner, searchQuery, dietaryFilter, productPanel, cart);
                }
            }
        });

        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder cartContents = new StringBuilder();
                double total = 0;
                for (String[] item : cart) {
                    cartContents.append(item[1]).append(" - $").append(item[2]).append("\n");
                    total += Double.parseDouble(item[2]);
                }
                JOptionPane.showMessageDialog(buyFromStoresFrame, "Cart contents:\n" + cartContents.toString() + "\nTotal: $" + total, "Cart", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double total = cart.stream().mapToDouble(item -> Double.parseDouble(item[2])).sum();
                
                String selectedOwner = (String) storeOwnerComboBox.getSelectedItem();
                StringBuilder report = new StringBuilder();
                report.append("Purchase Report\n");
                
                report.append("Store Owner: ").append(selectedOwner).append("\n");
                report.append("Purchases:\n");

                for (String[] item : cart) {
                    report.append(" - ").append(item[1]).append(" - $").append(item[2]).append("\n");
                }
                report.append("Total Amount: $").append(total).append("\n");

                // Save report to file
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("purchase_report.txt", true))) {
                    writer.write(report.toString());
                    writer.newLine();
                    writer.flush();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                // Display report to user
                JOptionPane.showMessageDialog(buyFromStoresFrame, report.toString(), "Purchase Report", JOptionPane.INFORMATION_MESSAGE);
                
                // Optionally clear the cart or redirect to payment gateway here
                cart.clear(); // Clear cart after checkout if necessary
            }
        });

        buyFromStoresFrame.setVisible(true);
    }
    ///////////////////////////////////////////////////////////

    private void loadProductsForStoreOwner(String storeOwnerName, String searchQuery, String dietaryFilter, JPanel productPanel, List<String[]> cart) {
        try {
            FileReader fr = new FileReader("products.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] productDetails = line.split("\\|");
                if (productDetails.length >= 5 && productDetails[0].equals(storeOwnerName)) {
                    String productName = productDetails[1].toLowerCase();
                    String productDescription = productDetails[3].toLowerCase();
                    String productDietaryInfo = productDetails.length > 5 ? productDetails[5].toLowerCase() : "";

                    boolean matchesQuery = searchQuery.isEmpty() || productName.contains(searchQuery) || productDescription.contains(searchQuery);
                    boolean matchesFilter = dietaryFilter.equals("All") || dietaryFilter.equals(productDietaryInfo);

                    if (matchesQuery && matchesFilter) {
                        JPanel productItemPanel = new JPanel();
                        productItemPanel.setLayout(new BoxLayout(productItemPanel, BoxLayout.Y_AXIS));
                        productItemPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        
                        ImageIcon imageIcon = new ImageIcon(productDetails[4]);
                        Image image = imageIcon.getImage().getScaledInstance(200, 100, Image.SCALE_SMOOTH);
                        JLabel imageLabel = new JLabel(new ImageIcon(image));
                        productItemPanel.add(imageLabel);
                        
                        JLabel nameLabel = new JLabel("Name: " + productDetails[1]);
                        productItemPanel.add(nameLabel);
                        
                        JLabel priceLabel = new JLabel("Price: " + productDetails[2]);
                        productItemPanel.add(priceLabel);
                        
                        JLabel descriptionLabel = new JLabel("Description: " + productDetails[3]);
                        productItemPanel.add(descriptionLabel);
                        
                        JButton addToCartButton = new JButton("Add to Cart");
                        addToCartButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                cart.add(productDetails);
                                JOptionPane.showMessageDialog(productPanel, "Product added to cart.", "Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                        });
                        productItemPanel.add(addToCartButton);
                        
                        productPanel.add(productItemPanel);
                    }
                }
            }
            br.close();
            productPanel.revalidate();
            productPanel.repaint();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/////////////////////////////////////////////////
    private void loadStoreOwners(JComboBox<String> storeOwnerComboBox) {
        try {
            FileReader fr = new FileReader("users.txt");
            BufferedReader br = new BufferedReader(fr);
            String line;
            while ((line = br.readLine()) != null) {
                String[] userDetails = line.split("\\|");
                if (userDetails.length > 0 && userDetails[0].equals("StoreOwner")) {
                    storeOwnerComboBox.addItem(userDetails[1]); // Assuming the name is the second field
                }
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

  
/////////////////////////////////////////////////////////

   
    
    
    
    
    

    private void openPostFrame(String username, JPanel imagePanel, JFrame roleFrame) {
        JFrame postFrame = new JFrame("Post and Share");
        postFrame.setSize(600, 500);
        postFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        postFrame.setLayout(null);

        JLabel titleLabel = new JLabel("Post your content", SwingConstants.CENTER);
        titleLabel.setBounds(0, 20, 600, 25);
        postFrame.add(titleLabel);

        JLabel imageTitleLabel = new JLabel("Image Title:");
        imageTitleLabel.setBounds(20, 60, 100, 25);
        postFrame.add(imageTitleLabel);

        JTextField imageTitleField = new JTextField();
        imageTitleField.setBounds(130, 60, 400, 25);
        postFrame.add(imageTitleField);

        JLabel descriptionLabel = new JLabel("Description:");
        descriptionLabel.setBounds(20, 100, 100, 25);
        postFrame.add(descriptionLabel);

        JTextArea descriptionArea = new JTextArea();
        descriptionArea.setBounds(130, 100, 400, 100);
        postFrame.add(descriptionArea);

        JLabel imageLabel = new JLabel("Upload Image:");
        imageLabel.setBounds(20, 220, 100, 25);
        postFrame.add(imageLabel);

        JButton uploadButton = new JButton("Choose Image");
        uploadButton.setBounds(130, 220, 150, 25);
        postFrame.add(uploadButton);

        JButton postButton = new JButton("Post");
        postButton.setBounds(250, 420, 100, 25);
        postFrame.add(postButton);

        final String[] imagePath = {null};

        uploadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    imagePath[0] = selectedFile.getAbsolutePath();
                    JOptionPane.showMessageDialog(postFrame, "Image selected: " + imagePath[0]);
                }
            }
        });

        postButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imagePath[0] != null) {
                    JLabel imageDisplay = new JLabel();
                    ImageIcon imageIcon = new ImageIcon(imagePath[0]);
                    Image image = imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                    imageDisplay.setIcon(new ImageIcon(image));

                    JLabel imageDescription = new JLabel(descriptionArea.getText());

                    JButton deleteButton = new JButton("Delete");
                    deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            imagePanel.remove(imageDisplay);
                            imagePanel.remove(imageDescription);
                            imagePanel.remove(deleteButton);
                            imagePanel.revalidate();
                            imagePanel.repaint();
                            deletePost(username, imagePath[0], descriptionArea.getText());
                        }
                    });

                    imagePanel.add(imageDisplay);
                    imagePanel.add(imageDescription);
                    imagePanel.add(deleteButton);

                    savePost(username, imagePath[0], descriptionArea.getText());

                    roleFrame.setSize(roleFrame.getWidth(), roleFrame.getHeight() + 200);
                    roleFrame.revalidate();
                    roleFrame.repaint();

                    postFrame.dispose();
                } else {
                    JOptionPane.showMessageDialog(postFrame, "Please select an image to post.");
                }
            }
        });

        postFrame.setVisible(true);
    }



////////////////////////////////////////////////////


    private void openSettingsFrame(String username) {
        JFrame settingsFrame = new JFrame("Account Settings");
        settingsFrame.setSize(400, 300);
        settingsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        settingsFrame.setLayout(null);

        JLabel usernameLabel = new JLabel("New Username:");
        usernameLabel.setBounds(50, 50, 100, 25);
        settingsFrame.add(usernameLabel);

        JTextField usernameField = new JTextField(username);
        usernameField.setBounds(150, 50, 200, 25);
        settingsFrame.add(usernameField);

        JLabel passwordLabel = new JLabel("New Password:");
        passwordLabel.setBounds(50, 100, 100, 25);
        settingsFrame.add(passwordLabel);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(150, 100, 200, 25);
        settingsFrame.add(passwordField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 150, 100, 25);
        settingsFrame.add(emailLabel);

        JTextField emailField = new JTextField();
        emailField.setBounds(150, 150, 200, 25);
        settingsFrame.add(emailField);

        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setBounds(50, 200, 100, 25);
        settingsFrame.add(countryLabel);

        JTextField countryField = new JTextField();
        countryField.setBounds(150, 200, 200, 25);
        settingsFrame.add(countryField);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setBounds(150, 240, 120, 25);
        settingsFrame.add(saveButton);

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = usernameField.getText();
                String newPassword = new String(passwordField.getPassword());
                String newEmail = emailField.getText();
                String newCountry = countryField.getText();

                if (newUsername.isEmpty() || newPassword.isEmpty() || newEmail.isEmpty() || newCountry.isEmpty()) {
                    JOptionPane.showMessageDialog(settingsFrame, "All fields must be filled.");
                    return;
                }

                User user = userList.stream().filter(u -> u.getName().equals(username)).findFirst().orElse(null);
                if (user != null) {
                    user.setName(newUsername);
                    user.setPassword(newPassword);
                    user.setEmail(newEmail);
                    user.setCountry(newCountry);
                    saveUserData();  // Save updated data to file
                    JOptionPane.showMessageDialog(settingsFrame, "User details updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(settingsFrame, "User not found.");
                }

                settingsFrame.dispose();
            }
        });

        settingsFrame.setVisible(true);
    }
    //////////////////////////////////////////////////
    
    private void loadPosts(String username, JPanel imagePanel) {
        imagePanel.removeAll(); // أزل كل المكونات الحالية قبل تحميل جديدة
        try {
            BufferedReader reader = new BufferedReader(new FileReader(username + "_posts.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                String imagePath = parts[0];
                String description = parts[1];

                JLabel imageDisplay = new JLabel();
                ImageIcon imageIcon = new ImageIcon(imagePath);
                Image image = imageIcon.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                imageDisplay.setIcon(new ImageIcon(image));

                JLabel imageDescription = new JLabel(description);

                JButton deleteButton = new JButton("Delete");
                deleteButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        imagePanel.remove(imageDisplay);
                        imagePanel.remove(imageDescription);
                        imagePanel.remove(deleteButton);
                        imagePanel.revalidate();
                        imagePanel.repaint();
                        deletePost(username, imagePath, description);
                    }
                });

                imagePanel.add(imageDisplay);
                imagePanel.add(imageDescription);
                imagePanel.add(deleteButton);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    
 


    
   

    ///////////////////////////////////////////
    
    
    
    private void addCommonComponents(JFrame frame, JTextField usernameField, JPasswordField passwordField, JTextField emailField, JTextField countryField, JComboBox<String> roleComboBox) {
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(20, 20, 150, 25);
        frame.add(usernameLabel);

        usernameField.setBounds(180, 20, 200, 25);
        frame.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(20, 60, 150, 25);
        frame.add(passwordLabel);

        passwordField.setBounds(180, 60, 200, 25);
        frame.add(passwordField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(20, 100, 150, 25);
        frame.add(emailLabel);

        emailField.setBounds(180, 100, 200, 25);
        frame.add(emailField);

        JLabel countryLabel = new JLabel("Country:");
        countryLabel.setBounds(20, 140, 150, 25);
        frame.add(countryLabel);

        countryField.setBounds(180, 140, 200, 25);
        frame.add(countryField);

        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setBounds(20, 180, 150, 25);
        frame.add(roleLabel);

        roleComboBox.setBounds(180, 180, 200, 25);
        frame.add(roleComboBox);
    }
    
    //////////////////////////////////

    private void saveUserData() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("users.txt"))) {
            for (User user : userList) {
                writer.println("User|" + user.getName() + "|" + user.getPassword() + "|" + user.getEmail() + "|" + user.getCountry());
            }
            for (StoreOwner storeOwner : storeOwnerList) {
                writer.println("StoreOwner|" + storeOwner.getUsername() + "|" + storeOwner.getPassword() + "|" + storeOwner.getEmail() + "|" + storeOwner.getCountry());
            }
            for (Admin admin : adminList) {
                writer.println("Admin|" + admin.getUsername() + "|" + admin.getPassword() + "|" + admin.getEmail() + "|" + admin.getCountry());
            }
            for (MaterialSupplier materialSupplier : materialSupplierList) {
                writer.println("MaterialSupplier|" + materialSupplier.getUsername() + "|" + materialSupplier.getPassword() + "|" + materialSupplier.getEmail() + "|" + materialSupplier.getCountry());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    ////////////////////////////
    private void savePost(String username, String imagePath, String description) {
        try {
            // حفظ في ملف باسم المستخدم
            BufferedWriter userFileWriter = new BufferedWriter(new FileWriter(username + "_posts.txt", true));
            userFileWriter.write(imagePath + "|" + description);
            userFileWriter.newLine();
            userFileWriter.close();
            
            // حفظ في ملف posts.txt
            BufferedWriter postsFileWriter = new BufferedWriter(new FileWriter("content.txt", true));
            postsFileWriter.write(username + "|" + imagePath + "|" + description);
            postsFileWriter.newLine();
            postsFileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    //////////////////////////////////
    private void deletePost(String username, String imagePath, String description) {
        try {
            File inputFile = new File(username + "_posts.txt");
            File tempFile = new File(username + "_posts_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(imagePath + "|" + description)) {
                    writer.write(line);
                    writer.newLine();
                }
            }
            reader.close();
            writer.close();

            if (!inputFile.delete()) {
                System.out.println("Could not delete file");
            }
            if (!tempFile.renameTo(inputFile)) {
                System.out.println("Could not rename file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /////////////////////////
    
   

    private void loadUserData() {
        try (Scanner scanner = new Scanner(new File("users.txt"))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split("\\|");
                switch (data[0]) {
                    case "User":
                        userList.add(new User(data[1], data[2], data[3], data[4]));
                        break;
                    case "StoreOwner":
                        storeOwnerList.add(new StoreOwner(data[1], data[2], data[3], data[4]));
                        break;
                    case "Admin":
                        adminList.add(new Admin(data[1], data[2], data[3], data[4]));
                        break;
                    case "MaterialSupplier":
                        materialSupplierList.add(new MaterialSupplier(data[1], data[2], data[3], data[4]));
                        break;
                }
            }
        } catch (FileNotFoundException e) {
            
        }
    }

    public static void main(String[] args) {
        new MyApplication();
    }
}
