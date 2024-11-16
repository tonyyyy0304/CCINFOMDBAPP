package mvc_folder;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {
    private final int COLUMN_WIDTH = 10;

    private JPanel mainPanel;
    
    //Products Table
    private JTextField productId, productName, productPrice,
                productStoreId, stockCount, description;
    private JComboBox<String> productCategories;
    private JCheckBox productR18;

    // Customers Table
    private JTextField customerId, customerFirstName, customerLastName, 
                customerPhoneNumber, customerEmailAddress, 
                customerLotNum, customerStreetName, customerCityName, 
                customerZipCode, customerCountry, 
                customerBirthdateYear, customerBirthdateMonth, customerBirthdateDay;

    // Stores Table
    private JTextField storeId, storeName, 
                storePhoneNumber, storeEmailAddress,
                storeLotNum, storeStreetName, storeCityName,
                storeZipCode, storeCountry;

    // Buttons
    private JButton productAddBtn, productRemoveBtn,
                customerAddBtn, customerRemoveBtn, 
                storeAddBtn, storeRemoveBtn;

    public View() {
        // Set up the frame
        setTitle("Swing Application");
        setResizable(false);
        setSize(1500,800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize components
        init();

        // Add components to the frame
        add(mainPanel);
    }

    public void init() {
        mainPanel = new JPanel(new BorderLayout());
        JTabbedPane tabbedPane = new JTabbedPane();

        // Add tabs
        tabbedPane.addTab("Add Product", productAddPnl());
        tabbedPane.addTab("Remove Product", productRemovePnl());
        tabbedPane.addTab("Add Customer", customerAddPnl());
        tabbedPane.addTab("Remove Customer", customerRemovePnl());
        tabbedPane.addTab("Add Store", storeAddPnl());
        tabbedPane.addTab("Remove Store", storeRemovePnl());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
    }

    private GridBagConstraints setGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        return gbc;
    }

    public JPanel productAddPnl() {
        JPanel panel = new JPanel(new GridBagLayout());
        
        productName = new JTextField(COLUMN_WIDTH);
        productPrice = new JTextField(COLUMN_WIDTH);
        productStoreId = new JTextField(COLUMN_WIDTH);
        stockCount = new JTextField(COLUMN_WIDTH);
        description = new JTextField(COLUMN_WIDTH);

        productCategories = new JComboBox<String>();
        productCategories.addItem("Clothing");
        productCategories.addItem("Electronics");
        productCategories.addItem("Beauty & Personal Care");
        productCategories.addItem("Food & Beverages");
        productCategories.addItem("Toys");
        productCategories.addItem("Applicances");
        productCategories.addItem("Home & Living");
        productCategories.setActionCommand("Product Categories");

        productR18 = new JCheckBox("R18");
        productR18.setActionCommand("R18");
        
        productAddBtn = new JButton("Add Product");
        productAddBtn.setActionCommand("Add Product");

        GridBagConstraints gbc = setGBC();

        // Product Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Product Name:"), gbc);

        gbc.gridwidth = 2;
        gbc.gridx++;
        panel.add(productName, gbc);

        // Product Price
        gbc.gridwidth = 1;
        gbc.gridx += 2;
        panel.add(new JLabel("Price:"), gbc);
        gbc.gridx++;
        panel.add(productPrice, gbc);

        // Store ID
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Store ID:"), gbc);

        gbc.gridwidth = 2;
        gbc.gridx++;
        panel.add(productStoreId, gbc);

        // Stock Count
        gbc.gridwidth = 1;
        gbc.gridx = 3;
        panel.add(new JLabel("Stock Count:"), gbc);

        gbc.gridx++;
        panel.add(stockCount, gbc);

        // Description
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Description:"), gbc);

        gbc.gridwidth = 5;
        gbc.gridx++;
        panel.add(description, gbc);

        // Product Categories
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Product Category:"), gbc);
        gbc.gridx++;
        panel.add(productCategories, gbc);

        // R18
        gbc.gridx++;
        panel.add(productR18, gbc);

        // Add Product Button
        gbc.gridwidth = 7;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(productAddBtn, gbc);

        return panel;
    }

    public JPanel productRemovePnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        productId = new JTextField(COLUMN_WIDTH);

        productRemoveBtn = new JButton("Remove Product");
        productRemoveBtn.setActionCommand("Remove Product");
        
        GridBagConstraints gbc = setGBC();

        // Product ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Product ID:"), gbc);

        gbc.gridwidth = 2;
        gbc.gridx++;
        panel.add(productId, gbc);

        // Remove Product Button
        gbc.gridwidth = 3;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(productRemoveBtn, gbc);

        return panel;
    }

    public JPanel customerAddPnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        customerFirstName = new JTextField(COLUMN_WIDTH);
        customerLastName = new JTextField(COLUMN_WIDTH);
        customerPhoneNumber = new JTextField(COLUMN_WIDTH);
        customerEmailAddress = new JTextField(COLUMN_WIDTH);
        customerLotNum = new JTextField(COLUMN_WIDTH);
        customerStreetName = new JTextField(COLUMN_WIDTH);
        customerCityName = new JTextField(COLUMN_WIDTH);
        customerZipCode = new JTextField(COLUMN_WIDTH);
        customerCountry = new JTextField(COLUMN_WIDTH);
        customerBirthdateYear = new JTextField(COLUMN_WIDTH);
        customerBirthdateMonth = new JTextField(COLUMN_WIDTH);
        customerBirthdateDay = new JTextField(COLUMN_WIDTH);

        customerAddBtn = new JButton("Add Customer");
        customerAddBtn.setActionCommand("Add Customer");

        GridBagConstraints gbc = setGBC();

        // Customer Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("First Name:"), gbc);
        gbc.gridx++;
        panel.add(customerFirstName, gbc);

        gbc.gridx++;
        panel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx++;
        panel.add(customerLastName, gbc);

        // Customer Phone Number
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Phone Number:"), gbc);
        gbc.gridx++;
        panel.add(customerPhoneNumber, gbc);

        // Customer Email Address
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Email Address:"), gbc);
        gbc.gridwidth = 2;
        gbc.gridx++;
        panel.add(customerEmailAddress, gbc);

        // Customer Address
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Lot Number:"), gbc);
        gbc.gridx++;
        panel.add(customerLotNum, gbc);

        gbc.gridx++;
        panel.add(new JLabel("Street Name:"), gbc);
        gbc.gridx++;
        panel.add(customerStreetName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("City Name:"), gbc);
        gbc.gridx++;
        panel.add(customerCityName, gbc);

        gbc.gridx++;
        panel.add(new JLabel("Zip Code:"), gbc);
        gbc.gridx++;
        panel.add(customerZipCode, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Country:"), gbc);
        gbc.gridx++;
        panel.add(customerCountry, gbc);

        // Customer Birthdate
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Birthdate:"), gbc);
        gbc.gridy++;
        panel.add(new JLabel("Year:"), gbc);
        gbc.gridx++;
        panel.add(customerBirthdateYear, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Month:"), gbc);
        gbc.gridx++;
        panel.add(customerBirthdateMonth, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Day:"), gbc);
        gbc.gridx++;
        panel.add(customerBirthdateDay, gbc);

        // Add Customer Button
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(customerAddBtn, gbc);

        return panel;
    }

    public JPanel customerRemovePnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        customerId = new JTextField();

        customerRemoveBtn = new JButton("Remove Customer");
        customerRemoveBtn.setActionCommand("Remove Customer");
        
        GridBagConstraints gbc = setGBC();

        // Customer ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Customer ID:"), gbc);
        gbc.gridx++;
        panel.add(customerId, gbc);

        // Remove Customer Button
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(customerRemoveBtn, gbc);

        return panel;
    }

    public JPanel storeAddPnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        storeName = new JTextField(COLUMN_WIDTH);
        storePhoneNumber = new JTextField(COLUMN_WIDTH);
        storeEmailAddress = new JTextField(COLUMN_WIDTH);
        storeLotNum = new JTextField(COLUMN_WIDTH);
        storeStreetName = new JTextField(COLUMN_WIDTH);
        storeCityName = new JTextField(COLUMN_WIDTH);
        storeZipCode = new JTextField(COLUMN_WIDTH);
        storeCountry = new JTextField(COLUMN_WIDTH);

        storeAddBtn = new JButton("Add Store");
        storeAddBtn.setActionCommand("Add Store");

        GridBagConstraints gbc = setGBC();

        // Store Name
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Store Name:"), gbc);
        gbc.gridwidth = 2;
        gbc.gridx++;
        panel.add(storeName, gbc);

        // Store Contact
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Contact Number:"), gbc);
        gbc.gridx++;
        panel.add(storePhoneNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Email Address:"), gbc);
        gbc.gridwidth = 2;
        gbc.gridx++;
        panel.add(storeEmailAddress, gbc);

        // Store Location
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Lot Number:"), gbc);
        gbc.gridx++;
        panel.add(storeLotNum, gbc);

        gbc.gridx++;
        panel.add(new JLabel("Street Name:"), gbc);
        gbc.gridx++;
        panel.add(storeStreetName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("City Name:"), gbc);
        gbc.gridx++;
        panel.add(storeCityName, gbc);

        gbc.gridx++;
        panel.add(new JLabel("Zip Code:"), gbc);
        gbc.gridx++;
        panel.add(storeZipCode, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Country:"), gbc);
        gbc.gridx++;
        panel.add(storeCountry, gbc);

        // Add Store Button
        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(storeAddBtn, gbc);

        return panel;
    }

    public JPanel storeRemovePnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        storeId = new JTextField(COLUMN_WIDTH);

        storeRemoveBtn = new JButton("Remove Store");
        storeRemoveBtn.setActionCommand("Remove Store");
        
        GridBagConstraints gbc = setGBC();

        // Store ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Store ID:"), gbc);
        gbc.gridx++;
        panel.add(storeId, gbc);

        // Remove Store Button
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(storeRemoveBtn, gbc);

        return panel;
    }

    public void setProductRemoveBtn(ActionListener listener) {
        productRemoveBtn.addActionListener(listener);
    }

    public void setProductAddBtn(ActionListener listener) {
        productAddBtn.addActionListener(listener);
    }

    public void setCustomerAddBtn(ActionListener listener) {
        customerAddBtn.addActionListener(listener);
    }

    public void setCustomerRemoveBtn(ActionListener listener) {
        customerRemoveBtn.addActionListener(listener);
    }

    public String getProductId() {
        return productId.getText();
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getProductStoreId() {
        return productStoreId.getText();
    }

    public String getStockCount() {
        return stockCount.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public void clearFields() {
        productId.setText("");
        productName.setText("");
        productStoreId.setText("");
        stockCount.setText("");
        description.setText("");
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    public void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
}