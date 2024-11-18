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

    private JPanel mainPanel, customerRecordsPanel, storesCustomersBoughtFromPnl,
            productRecordsPanel, storeRecordsPanel, logisticsRecordsPanel,
            customerStatsPanel, productSalesPanel, shippingReportsPanel, affinityPanel;

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

    //logistics table
    private JTextField logisticsCompanyID, logisticsCompanyName,
            logisticsCompanyLocationID, shipmentScope;

    // Buttons
    private JButton productAddBtn, productRemoveBtn,
            customerAddBtn, customerRemoveBtn,
            storeAddBtn, storeRemoveBtn, logisticsAddBtn, logisticsRemoveBtn;

    // Place Order
    private JTextField orderCustomerId, orderProductId, orderQuantity,
                orderLotNum, orderStreetName, orderCityName, orderZipCode, orderCountry;
    private JComboBox<String> orderPaymentMethod;
    private JButton placeOrderBtn;

    // Adjust Stock
    private JTextField adjustStockProductId, adjustStockQuantity;
    private JComboBox<String> adjustStockType;
    private JButton adjustStockBtn;

    // Pay for Order
    private JTextField paymentCustomerId, paymentOrderId, paymentAmount;
    private JButton paymentBtn;

    // Ship Order
    private JTextField shipOrderId, shipLogisticsId;
    private JButton shipOrderBtn;

    public View() {
        // Set up the frame
        setTitle("Online Shopping System");
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
        placeOrderBtn = new JButton("Place Order");
        adjustStockBtn = new JButton("Adjust Stock");
        paymentBtn = new JButton("Pay for Order");
        shipOrderBtn = new JButton("Ship Order");

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
        JTabbedPane logisticsTabbedPane = new JTabbedPane();
        logisticsTabbedPane.addTab("Logistics Company Records", logisticsRecordPnl());
        logisticsTabbedPane.addTab("Add Logistics Company", logisticsAddPnl());
        logisticsTabbedPane.addTab("Remove Logistics Company", logisticsRemovePnl());
        logisticsPanel.add(logisticsTabbedPane, BorderLayout.CENTER);

        recordsManagementTabbedPane.addTab("Products", productsPanel);
        recordsManagementTabbedPane.addTab("Customers", customersPanel);
        recordsManagementTabbedPane.addTab("Stores", storesPanel);
        recordsManagementTabbedPane.addTab("Logistics Companies", logisticsPanel);
        recordsManagementPanel.add(recordsManagementTabbedPane, BorderLayout.CENTER);

        // Transactions Panel
        JPanel transactionsPanel = new JPanel(new BorderLayout());
        JTabbedPane transactionsTabbedPane = new JTabbedPane();
        transactionsTabbedPane.addTab("Place Order", placeOrderPnl());
        transactionsTabbedPane.addTab("Adjust Stock", adjustStockPnl());
        transactionsTabbedPane.addTab("Pay for Order", payForOrderPnl());
        transactionsTabbedPane.addTab("Ship Order", shipOrderPnl());
        transactionsPanel.add(transactionsTabbedPane, BorderLayout.CENTER);

        // Reports Panel
        JPanel reportsPanel = new JPanel(new BorderLayout());
        JTabbedPane reportsTabbedPane = new JTabbedPane();
        reportsTabbedPane.addTab("Customer Statistics", customerStatsPnl());
        reportsTabbedPane.addTab("Product Sales", productSalesPnl());
        reportsTabbedPane.addTab("Shipping Reports", shippingReportsPnl());
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

    private JPanel productAddPnl() {
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

    private JPanel productRemovePnl() {
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

    private JPanel productRecordsPnl() {
        productRecordsPanel = new JPanel(new GridBagLayout());
        refreshProductRecords();
        return productRecordsPanel;
    }

    public void refreshProductRecords() {
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

        productRecordsPanel.removeAll();
        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        productRecordsPanel.add(scrollPane, gbc);
        productRecordsPanel.revalidate();
        productRecordsPanel.repaint();
    }

    private JPanel customerAddPnl() {
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

    private JPanel customerRemovePnl() {
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

    private JPanel customerRecordsPnl() {
        customerRecordsPanel = new JPanel(new GridBagLayout());
        refreshCustomerRecords();
        return customerRecordsPanel;
    }

    public void refreshCustomerRecords() {
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

        customerRecordsPanel.removeAll();
        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        customerRecordsPanel.add(scrollPane, gbc);
        customerRecordsPanel.revalidate();
        customerRecordsPanel.repaint();
    }

    private JPanel storesCustomersBoughtFromPnl() {
        storesCustomersBoughtFromPnl = new JPanel(new GridBagLayout());
        refreshStoresCustomerBoughtFrom();
        return storesCustomersBoughtFromPnl;
    }

    public void refreshStoresCustomerBoughtFrom() {
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

        storesCustomersBoughtFromPnl.removeAll();
        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        storesCustomersBoughtFromPnl.add(scrollPane, gbc);
        storesCustomersBoughtFromPnl.revalidate();
        storesCustomersBoughtFromPnl.repaint();
    }

    private JPanel storeAddPnl() {
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

    private JPanel storeRemovePnl() {
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

    private JPanel storeRecordsPnl() {
        storeRecordsPanel = new JPanel(new GridBagLayout());
        refreshStoreRecordsPnl();
        return storeRecordsPanel;
    }

    public void refreshStoreRecordsPnl() {
        String[] columnNames = {"Store ID", "Store Name", "Phone Number", "Email Address", "Address", "Registration Date"};
        Object[][] data = {};

        try {
            data = Model.getStoreRecords();
        } catch (SQLException e) {
            showError("Failed to retrieve store records: " + e.getMessage());
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        storeRecordsPanel.removeAll();
        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        storeRecordsPanel.add(scrollPane, gbc);
        storeRecordsPanel.revalidate();
        storeRecordsPanel.repaint();
    }

    private JPanel logisticsAddPnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        logisticsCompanyName = new JTextField(COLUMN_WIDTH);
        logisticsCompanyLocationID = new JTextField(COLUMN_WIDTH);
        shipmentScope = new JTextField(COLUMN_WIDTH);

        GridBagConstraints gbc = setGBC();

        logisticsAddBtn = new JButton("Add Company");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Logistics Company Name:"), gbc);
        gbc.gridx++;
        panel.add(logisticsCompanyName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Logistics Company Location:"), gbc);
        gbc.gridx++;
        panel.add(logisticsCompanyLocationID, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(logisticsAddBtn, gbc);

        return panel;
    }

    private JPanel logisticsRemovePnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        logisticsCompanyID = new JTextField(COLUMN_WIDTH);

        logisticsRemoveBtn = new JButton("Remove Company");
        logisticsRemoveBtn.setActionCommand("Remove Company");

        GridBagConstraints gbc = setGBC();

        // Logistics Company ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Logistics Company ID:"), gbc);
        gbc.gridx++;
        panel.add(logisticsCompanyID, gbc);

        // Remove Store Button
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(logisticsRemoveBtn, gbc);

        return panel;
    }

    private JPanel logisticsRecordPnl() {
        JPanel panel = new JPanel(new GridBagLayout());

        return panel;
    }

    private JPanel placeOrderPnl() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = setGBC();

        orderCustomerId = new JTextField(COLUMN_WIDTH);
        orderProductId = new JTextField(COLUMN_WIDTH);
        orderQuantity = new JTextField(COLUMN_WIDTH);
        orderLotNum = new JTextField(COLUMN_WIDTH);
        orderStreetName = new JTextField(COLUMN_WIDTH);
        orderCityName = new JTextField(COLUMN_WIDTH);
        orderZipCode = new JTextField(COLUMN_WIDTH);
        orderCountry = new JTextField(COLUMN_WIDTH);

        orderPaymentMethod = new JComboBox<String>();
        orderPaymentMethod.addItem("Credit");
        orderPaymentMethod.addItem("Debit");

        placeOrderBtn = new JButton("Place Order");
        placeOrderBtn.setActionCommand("Place Order");

        // Customer ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Customer ID:"), gbc);
        gbc.gridx++;
        panel.add(orderCustomerId, gbc);

        // Product ID
        gbc.gridx++;
        panel.add(new JLabel("Product ID:"), gbc);
        gbc.gridx++;
        panel.add(orderProductId, gbc);

        // Quantity
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx++;
        panel.add(orderQuantity, gbc);

        // Address
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Delivery Address:"), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Lot Number:"), gbc);
        gbc.gridx++;
        panel.add(orderLotNum, gbc);

        gbc.gridx++;
        panel.add(new JLabel("Street Name:"), gbc);
        gbc.gridx++;
        panel.add(orderStreetName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("City Name:"), gbc);
        gbc.gridx++;
        panel.add(orderCityName, gbc);

        gbc.gridx++;
        panel.add(new JLabel("Zip Code:"), gbc);
        gbc.gridx++;
        panel.add(orderZipCode, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Country:"), gbc);
        gbc.gridx++;
        panel.add(orderCountry, gbc);

        // Payment Method
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Payment Method:"), gbc);
        gbc.gridx++;
        panel.add(orderPaymentMethod, gbc);

        // Place Order Button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 4;
        panel.add(placeOrderBtn, gbc);

        return panel;
    }

    private JPanel adjustStockPnl() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = setGBC();

        adjustStockProductId = new JTextField(COLUMN_WIDTH);
        adjustStockQuantity = new JTextField(COLUMN_WIDTH);

        adjustStockType = new JComboBox<String>();
        adjustStockType.addItem("Increase");
        adjustStockType.addItem("Decrease");

        adjustStockBtn = new JButton("Adjust Stock");
        adjustStockBtn.setActionCommand("Adjust Stock");

        // Product ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Product ID:"), gbc);
        gbc.gridx++;
        panel.add(adjustStockProductId, gbc);

        // Quantity
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Quantity:"), gbc);
        gbc.gridx++;
        panel.add(adjustStockQuantity, gbc);

        // Adjustment Type
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Adjustment Type:"), gbc);
        gbc.gridx++;
        panel.add(adjustStockType, gbc);

        // Adjust Stock Button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(adjustStockBtn, gbc);

        return panel;
    }

    private JPanel payForOrderPnl() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = setGBC();

        paymentCustomerId = new JTextField(COLUMN_WIDTH);
        paymentOrderId = new JTextField(COLUMN_WIDTH);
        paymentAmount = new JTextField(COLUMN_WIDTH);

        paymentBtn = new JButton("Pay for Order");
        paymentBtn.setActionCommand("Pay for Order");

        // Customer ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Customer ID:"), gbc);
        gbc.gridx++;
        panel.add(paymentCustomerId, gbc);

        // Order ID
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Order ID:"), gbc);
        gbc.gridx++;
        panel.add(paymentOrderId, gbc);

        // Amount
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Amount:"), gbc);
        gbc.gridx++;
        panel.add(paymentAmount, gbc);

        // Pay for Order Button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(paymentBtn, gbc);

        return panel;
    }

    private JPanel shipOrderPnl() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = setGBC();

        shipOrderId = new JTextField(COLUMN_WIDTH);
        shipLogisticsId = new JTextField(COLUMN_WIDTH);

        shipOrderBtn = new JButton("Ship Order");
        shipOrderBtn.setActionCommand("Ship Order");

        // Order ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Order ID:"), gbc);
        gbc.gridx++;
        panel.add(shipOrderId, gbc);

        // Logistics Company ID
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Logistics Company ID:"), gbc);
        gbc.gridx++;
        panel.add(shipLogisticsId, gbc);

        // Ship Order Button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(shipOrderBtn, gbc);

        return panel;
    }

    private JPanel customerStatsPnl() {
        customerStatsPanel = new JPanel(new GridBagLayout());
        refreshCustomerStatsPnl();
        return customerStatsPanel;
    }

    public void refreshCustomerStatsPnl() {
        String[] columnNames = {"Customer ID", "Customer Name", "Number of Orders Per Month", "Amount Spent Per Month"};
        Object[][] data = {};

        try {
            data = Model.getCustomerStats();
        } catch (SQLException e) {
            showError("Failed to retrieve customer stats: " + e.getMessage());
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        customerStatsPanel.removeAll();
        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        customerStatsPanel.add(scrollPane, gbc);
        customerStatsPanel.revalidate();
        customerStatsPanel.repaint();
    }

    private JPanel productSalesPnl() {
        productSalesPanel = new JPanel(new GridBagLayout());
        refreshProductSalesPnl();
        return productSalesPanel;
    }

    public void refreshProductSalesPnl() {
        String[] columnNames = {"Category", "Total Sales Per Month"};
        Object[][] data = {};

        try {
            data = Model.getProductSales();
        } catch (SQLException e) {
            showError("Failed to retrieve product sales: " + e.getMessage());
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        productSalesPanel.removeAll();
        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        productSalesPanel.add(scrollPane, gbc);
        productSalesPanel.revalidate();
        productSalesPanel.repaint();
    }

    private JPanel shippingReportsPnl() {
        shippingReportsPanel = new JPanel(new GridBagLayout());
        refreshShippingReportsPnl();
        return shippingReportsPanel;
    }

    public void refreshShippingReportsPnl() {
        String[] columnNames = {"Shipping Status", "Number of Orders"};
        Object[][] data = {};

        try {
            data = Model.getShippingReports();
        } catch (SQLException e) {
            showError("Failed to retrieve security reports: " + e.getMessage());
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        shippingReportsPanel.removeAll();
        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        shippingReportsPanel.add(scrollPane, gbc);
        shippingReportsPanel.revalidate();
        shippingReportsPanel.repaint();
    }

    private JPanel affinityPnl() {
        affinityPanel = new JPanel(new GridBagLayout());
        refreshAffinityPnl();
        return affinityPanel;
    }

    public void refreshAffinityPnl() {
        String[] columnNames = {"Customer Name", "Store Name", "Number of Orders Made at Store", "Total Amount Spent"};
        Object[][] data = {};

        try {
            data = Model.getAffinity();
        } catch (SQLException e) {
            showError("Failed to retrieve affinity: " + e.getMessage());
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        affinityPanel.removeAll();
        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        affinityPanel.add(scrollPane, gbc);
        affinityPanel.revalidate();
        affinityPanel.repaint();
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

    public void setPlaceOrderBtn(ActionListener listener) {
        placeOrderBtn.addActionListener(listener);
    }

    public void setAdjustStockBtn(ActionListener listener) {
        adjustStockBtn.addActionListener(listener);
    }

    public void setPaymentBtn(ActionListener listener) {
        paymentBtn.addActionListener(listener);
    }

    public void setShipOrderBtn(ActionListener listener) {
        shipOrderBtn.addActionListener(listener);
    }

    public void setLogisticsAddBtn(ActionListener listener) {
        logisticsAddBtn.addActionListener(listener);
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
        return productCategories.getSelectedItem().toString();
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

    public String getOrderCustomerId() {
        return orderCustomerId.getText();
    }

    public String getOrderProductId() {
        return orderProductId.getText();
    }

    public String getOrderQuantity() {
        return orderQuantity.getText();
    }

    public String getOrderLotNum() {
        return orderLotNum.getText();
    }

    public String getOrderStreetName() {
        return orderStreetName.getText();
    }

    public String getOrderCityName() {
        return orderCityName.getText();
    }

    public String getOrderZipCode() {
        return orderZipCode.getText();
    }

    public String getOrderCountry() {
        return orderCountry.getText();
    }

    public String getOrderPaymentMethod() {
        return orderPaymentMethod.getSelectedItem().toString();
    }

    public String getAdjustStockProductId() {
        return adjustStockProductId.getText();
    }

    public String getAdjustStockQuantity() {
        return adjustStockQuantity.getText();
    }

    public String getAdjustStockType() {
        return adjustStockType.getSelectedItem().toString();
    }

    public String getPaymentCustomerId() {
        return paymentCustomerId.getText();
    }

    public String getPaymentOrderId() {
        return paymentOrderId.getText();
    }

    public String getPaymentAmount() {
        return paymentAmount.getText();
    }

    public String getShipOrderId() {
        return shipOrderId.getText();
    }

    public String getShipLogisticsId() {
        return shipLogisticsId.getText();
    }

    public String getLogisticsCompanyID() {
        return logisticsCompanyID.getText();
    }

    public String getLogisticsCompanyLocation() {
        return logisticsCompanyLocationID.getText();
    }

    public String getLogisticsCompanyName() {
        return logisticsCompanyName.getText();
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

        orderCustomerId.setText("");
        orderProductId.setText("");
        orderQuantity.setText("");
        orderLotNum.setText("");
        orderStreetName.setText("");
        orderCityName.setText("");
        orderZipCode.setText("");
        orderCountry.setText("");
        orderPaymentMethod.setSelectedIndex(0);

        adjustStockProductId.setText("");
        adjustStockQuantity.setText("");
        adjustStockType.setSelectedIndex(0);

        paymentCustomerId.setText("");
        paymentOrderId.setText("");
        paymentAmount.setText("");

        shipOrderId.setText("");
        shipLogisticsId.setText("");

        logisticsCompanyID.setText("");
        logisticsCompanyName.setText("");
        logisticsCompanyLocationID.setText("");

        refreshProductRecords();
        refreshCustomerRecords();
        refreshStoresCustomerBoughtFrom();
        refreshStoreRecordsPnl();
        refreshCustomerStatsPnl();
        refreshProductSalesPnl();
        refreshShippingReportsPnl();
        refreshAffinityPnl();
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
