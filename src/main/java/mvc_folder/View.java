package main.java.mvc_folder;

import javax.swing.*;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.table.TableColumn;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Properties;

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
                customerZipCode, customerCountry;
    private JDatePickerImpl customerBirthdate;

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
        setTitle("Online Shoppping System");
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
        productAddBtn = new JButton("Add Product");
        productRemoveBtn = new JButton("Remove Product");
        customerAddBtn = new JButton("Add Customer");
        customerRemoveBtn = new JButton("Remove Customer");
        storeAddBtn = new JButton("Add Store");
        storeRemoveBtn = new JButton("Remove Store");

        JTabbedPane mainTabbedPane = new JTabbedPane();

        // Records Management Panel
        JPanel recordsManagementPanel = new JPanel(new BorderLayout());
        JTabbedPane recordsManagementTabbedPane = new JTabbedPane();

        // Products Panel
        JPanel productsPanel = new JPanel(new BorderLayout());
        JTabbedPane productsTabbedPane = new JTabbedPane();
        productsTabbedPane.addTab("Product Records", productRecordsPnl());
        productsTabbedPane.addTab("Add Product", productAddPnl());
        productsTabbedPane.addTab("Remove Product", productRemovePnl());
        productsPanel.add(productsTabbedPane, BorderLayout.CENTER);

        // Customers Panel
        JPanel customersPanel = new JPanel(new BorderLayout());
        JTabbedPane customersTabbedPane = new JTabbedPane();
        customersTabbedPane.addTab("Customer Records", customerRecordsPnl());
        customersTabbedPane.addTab("Stores Customers Bought From", storesCustomersBoughtFromPnl());
        customersTabbedPane.addTab("Add Customer", customerAddPnl());
        customersTabbedPane.addTab("Remove Customer", customerRemovePnl());
        customersPanel.add(customersTabbedPane, BorderLayout.CENTER);

        // Stores Panel
        JPanel storesPanel = new JPanel(new BorderLayout());
        JTabbedPane storesTabbedPane = new JTabbedPane();
        storesTabbedPane.addTab("Store Records", storeRecordsPnl());
        storesTabbedPane.addTab("Add Store", storeAddPnl());
        storesTabbedPane.addTab("Remove Store", storeRemovePnl());
        storesPanel.add(storesTabbedPane, BorderLayout.CENTER);

        // Logistics Companies Panel
        JPanel logisticsPanel = new JPanel(new BorderLayout());
        logisticsPanel.add(logisticsPnl(), BorderLayout.CENTER);

        recordsManagementTabbedPane.addTab("Products", productsPanel);
        recordsManagementTabbedPane.addTab("Customers", customersPanel);
        recordsManagementTabbedPane.addTab("Stores", storesPanel);
        recordsManagementTabbedPane.addTab("Logistics Companies", logisticsPanel);
        recordsManagementPanel.add(recordsManagementTabbedPane, BorderLayout.CENTER);

        // Transactions Panel
        JPanel transactionsPanel = new JPanel(new BorderLayout());
        JTabbedPane transactionsTabbedPane = new JTabbedPane();
        // TODO: Add transactions panels
        transactionsTabbedPane.addTab("Place Order", placeOrderPnl());
        transactionsPanel.add(transactionsTabbedPane, BorderLayout.CENTER);

        // Reports Panel
        JPanel reportsPanel = new JPanel(new BorderLayout());
        JTabbedPane reportsTabbedPane = new JTabbedPane();
        reportsTabbedPane.addTab("Customer Statistics", customerStatsPnl());
        reportsTabbedPane.addTab("Product Sales", productSalesPnl());
        reportsTabbedPane.addTab("Security Reports", securityReportsPnl());
        reportsTabbedPane.addTab("Affinity of Customer to Store", affinityPnl());
        reportsPanel.add(reportsTabbedPane, BorderLayout.CENTER);

        mainTabbedPane.addTab("Records Management", recordsManagementPanel);
        mainTabbedPane.addTab("Transactions", transactionsPanel);
        mainTabbedPane.addTab("Reports", reportsPanel);
        mainPanel.add(mainTabbedPane, BorderLayout.CENTER);
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

    public JPanel productRecordsPnl() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = setGBC();

        String[] columnNames = {"Product ID", "Product Name", "Price", "Store Name", "Stock Count", "Description", "Category", "R18"};
        Object[][] data = {};

        try {
            data = Model.getProductRecords();
        } catch (SQLException e) {
            showError("Failed to retrieve product records: " + e.getMessage());
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

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

        customerAddBtn = new JButton("Add Customer");
        customerAddBtn.setActionCommand("Add Customer");

        SqlDateModel model = new SqlDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        customerBirthdate = new JDatePickerImpl(datePanel, new DateLabelFormatter());

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
        gbc.gridwidth = 2;
        gbc.gridx++;
        panel.add(customerBirthdate, gbc);

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

    public JPanel customerRecordsPnl() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = setGBC();

        String[] columnNames = {"Customer ID", "First Name", "Last Name", "Phone Number", "Email Address", "Birthdate", "Address", "Status", "Registration Date"};
        Object[][] data = {};

        try {
            data = Model.getCustomerRecords();
        } catch (SQLException e) {
            showError("Failed to retrieve customer records: " + e.getMessage());
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

        return panel;
    }

    public JPanel storesCustomersBoughtFromPnl() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = setGBC();

        String[] columnNames = {"Customer Name", "Store Name"};
        Object[][] data = {};

        try {
            data = Model.getStoresCustomersBoughtFrom();
        } catch (SQLException e) {
            showError("Failed to retrieve stores customer bought from: " + e.getMessage());
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPane, gbc);

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

    public JPanel storeRecordsPnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        return panel;
    }

    public JPanel logisticsPnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        return panel;
    }

    public JPanel placeOrderPnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        return panel;
    }

    public JPanel customerStatsPnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        return panel;
    }

    public JPanel productSalesPnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        return panel;
    }

    public JPanel securityReportsPnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        return panel;
    }

    public JPanel affinityPnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        return panel;
    }

    private void adjustColumnWidths(JTable table) {
        for (int column = 0; column < table.getColumnCount(); column++) {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = 50; // Minimum width
            for (int row = 0; row < table.getRowCount(); row++) {
                Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
                preferredWidth = Math.max(comp.getPreferredSize().width + 1, preferredWidth);
            }
            tableColumn.setPreferredWidth(preferredWidth);
        }
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

    public void setStoreAddBtn(ActionListener listener) {
        storeAddBtn.addActionListener(listener);
    }

    public void setStoreRemoveBtn(ActionListener listener) {
        storeRemoveBtn.addActionListener(listener);
    }

    public Integer getProductId() {
        return Integer.parseInt(productId.getText());
    }

    public String getProductName() {
        return productName.getText();
    }

    public String getProductPrice() {
        return productPrice.getText();
    }

    public String getProductStoreId() {
        return productStoreId.getText();
    }

    public Integer getStockCount() {
        return Integer.parseInt(stockCount.getText());
    }

    public String getDescription() {
        return description.getText();
    }

    public String getSelectedProductCategory() {
        return (String) productCategories.getSelectedItem();
    }

    public boolean isProductR18() {
        return productR18.isSelected();
    }
    
    public String getStoreName() {
        return storeName.getText();
    }
    
    public String getStorePhoneNumber() {
        return storePhoneNumber.getText();
    }
    
    public String getStoreEmailAddress() {
        return storeEmailAddress.getText();
    }
    
    public String getStoreLotNum() {
        return storeLotNum.getText();
    }
    
    public String getStoreStreetName() {
        return storeStreetName.getText();
    }
    
    public String getStoreCityName() {
        return storeCityName.getText();
    }
    
    public String getStoreZipCode() {
        return storeZipCode.getText();
    }
    
    public String getStoreCountry() {
        return storeCountry.getText();
    }

    public String getCustomerId() {
        return customerId.getText();
    }

    public String getCustomerFirstName() {
        return customerFirstName.getText();
    }

    public String getCustomerLastName() {
        return customerLastName.getText();
    }

    public String getCustomerPhoneNumber() {
        return customerPhoneNumber.getText();
    }

    public String getCustomerEmailAddress() {
        return customerEmailAddress.getText();
    }

    public String getCustomerLotNum() {
        return customerLotNum.getText();
    }

    public String getCustomerStreetName() {
        return customerStreetName.getText();
    }

    public String getCustomerCityName() {
        return customerCityName.getText();
    }

    public String getCustomerZipCode() {
        return customerZipCode.getText();
    }

    public String getCustomerCountry() {
        return customerCountry.getText();
    }

    public Date getCustomerBirthdate() {
        return (Date) customerBirthdate.getModel().getValue();
    }

    public void clearFields() {
        productId.setText("");
        productName.setText("");
        productStoreId.setText("");
        stockCount.setText("");
        description.setText("");
        productPrice.setText("");
        productCategories.setSelectedIndex(0);
        productR18.setSelected(false);

        customerId.setText("");
        customerFirstName.setText("");
        customerLastName.setText("");
        customerPhoneNumber.setText("");
        customerEmailAddress.setText("");
        customerLotNum.setText("");
        customerStreetName.setText("");
        customerCityName.setText("");
        customerZipCode.setText("");
        customerCountry.setText("");
        customerBirthdate.getModel().setValue(null);

        storeId.setText("");
        storeName.setText("");
        storePhoneNumber.setText("");
        storeEmailAddress.setText("");
        storeLotNum.setText("");
        storeStreetName.setText("");
        storeCityName.setText("");
        storeZipCode.setText("");
        storeCountry.setText("");
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

    private static class DateLabelFormatter extends AbstractFormatter {
        private String datePattern = "yyyy-MM-dd";
        private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormatter.parseObject(text);
        }

        @Override
        public String valueToString(Object value) throws ParseException {
            if (value != null) {
                Calendar cal = (Calendar) value;
                return dateFormatter.format(cal.getTime());
            }

            return "";
        }
    }
}