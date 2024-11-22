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
import java.util.ArrayList;
import java.util.List;

public class View extends JFrame {
    private final int COLUMN_WIDTH = 10;

    private JPanel mainPanel, customerRecordsPanel, storesCustomersBoughtFromPnl,
            productRecordsPanel, storeRecordsPanel, logisticsRecordsPanel,
            customerStatsPanel, productSalesPanel, paymentReportsPanel, affinityPanel;

    //Products Table
    private JTextField productId, productName, productPrice,
            productStoreId, stockCount, description;
    private JComboBox<String> productCategories;
    private JCheckBox productR18;
    private JTextField productSearchField;
    private JButton productSearchBtn, productShowAllBtn;
    private JComboBox<String> productSearchCriteriaComboBox;

    // Customers Table
    private JTextField customerId, customerFirstName, customerLastName,
            customerPhoneNumber, customerEmailAddress,
            customerLotNum, customerStreetName, customerCityName,
            customerZipCode, customerCountry;
    private JDatePickerImpl customerBirthdate;
    private JButton customerSearchBtn, customerShowAllBtn;
    private JComboBox<String> customerCriteriaComboBox;
    private JTextField customerSearchField;

    // Stores Table
    private JTextField storeId, storeName,
            storePhoneNumber, storeEmailAddress,
            storeLotNum, storeStreetName, storeCityName,
            storeZipCode, storeCountry;

    private JButton storeSearchBtn, storeShowAllBtn;
    private JComboBox<String> storeCriteriaComboBox;
    private JTextField storeSearchField;

    //logistics table
    private JTextField logisticsCompanyID, logisticsCompanyName,
            logisticsCompanyLocationID, logisticsLotNum,
            logisticsStreetName, logisticsCityName, logisticsZipCode,
            logisticsCountry;
    private JComboBox<String> shipmentScope;
    private JButton logisticsSearchBtn, logisticsShowAllBtn;
    private JComboBox<String> logisticsCriteriaComboBox;
    private JTextField logisticsSearchField;
    private JPanel logisticsRelatedRecordsPanel;
    private JButton logisticsRelatedRecordSearchBtn, logisticsRelatedRecordShowAllBtn;
    private JComboBox<String> logisticsRelatedRecordCriteriaComboBox;
    private JTextField logisticsRelatedRecordSearchField;

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
    private JButton paymentBtn, paymentCancelBtn;

    // Ship Order
    private JTextField shipOrderId, shipLogisticsId;
    private JButton shipOrderBtn;

    // Customer Statistics
    private JTextField customerStatsStartYearTF, customerStatsEndYearTF;
    private JButton customerStatsSearchBtn, customerStatsShowAllBtn;
    
    // Product Sales
    private JComboBox<String> productSalesCategory;
    private JTextField productSalesReportStartYearTF;
    private JTextField productSalesReportEndYearTF;
    private JButton productSalesReportSearchBtn;
    private JButton productSalesReportShowAllBtn;

    // Payment Reports
    private JTextField paymentReportStartYearTF, paymentReportEndYearTF;
    private JButton paymentReportSearchBtn, paymentReportShowAllBtn;

    // Affinity
    private JTextField affinityStartYearTF, affinityEndYearTF;
    private JButton affinitySearchBtn, affinityShowAllBtn;
   

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
        customerSearchBtn = new JButton("Search");
        storeAddBtn = new JButton("Add Store");
        storeRemoveBtn = new JButton("Remove Store");
        logisticsAddBtn = new JButton("Add Logistics Company");
        logisticsRemoveBtn = new JButton("Remove Logistics Company");
        placeOrderBtn = new JButton("Place Order");
        adjustStockBtn = new JButton("Adjust Stock");
        paymentBtn = new JButton("Pay for Order");
        shipOrderBtn = new JButton("Ship Order");
        customerStatsSearchBtn = new JButton("Search");
        customerStatsShowAllBtn = new JButton("Show All");
        productSalesReportSearchBtn = new JButton("Search");
        productSalesReportShowAllBtn = new JButton("Show All");
        paymentReportSearchBtn = new JButton("Search");
        paymentReportShowAllBtn = new JButton("Show All");
        affinitySearchBtn = new JButton("Search");
        affinityShowAllBtn = new JButton("Show All");

        productSalesCategory = new JComboBox<String>(new String[] {"Clothing", "Electronics", "Beauty & Personal Care", "Food & Beverages", "Toys", "Appliances", "Home & Living"});

        JTabbedPane mainTabbedPane = new JTabbedPane();

        // Records Management Panel
        JPanel recordsManagementPanel = new JPanel(new BorderLayout());
        JTabbedPane recordsManagementTabbedPane = new JTabbedPane();

        // Products Panel
        JPanel productsPanel = new JPanel(new BorderLayout());
        JTabbedPane productsTabbedPane = new JTabbedPane();
        productsTabbedPane.addTab("Product Records", productRecordsPnl());
        productsTabbedPane.addTab("Stores Selling Specific Category", productRecordsPnl());
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
        storesTabbedPane.addTab("Product List of Stores", productRecordsPnl());
        storesTabbedPane.addTab("Add Store", storeAddPnl());
        storesTabbedPane.addTab("Remove Store", storeRemovePnl());
        storesPanel.add(storesTabbedPane, BorderLayout.CENTER);

        // Logistics Companies Panel
        JPanel logisticsPanel = new JPanel(new BorderLayout());
        JTabbedPane logisticsTabbedPane = new JTabbedPane();
        logisticsTabbedPane.addTab("Logistics Company Records", logisticsRecordPnl());
        logisticsTabbedPane.addTab("Orders Handled by Logistics Companies", ordersHandledByLogisticsCompaniesPnl());
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
        reportsTabbedPane.addTab("Payment Reports", paymentReportsPnl());
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
        productSearchField = new JTextField(20);
        productSearchBtn = new JButton("Search");
        productSearchBtn.setPreferredSize(new Dimension(150, 25));
        productShowAllBtn = new JButton("Show All");
        productShowAllBtn.setPreferredSize(new Dimension(150, 25));
        productSearchCriteriaComboBox = new JComboBox<>(new String[]{"Product Name", "Product ID"});

        productRecordsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = setGBC();

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        productRecordsPanel.add(new JLabel("Search:"), gbc);

        gbc.gridx++;
        productRecordsPanel.add(productSearchField, gbc);

        gbc.gridx++;
        productRecordsPanel.add(productSearchCriteriaComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        productRecordsPanel.add(productSearchBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        productRecordsPanel.add(productShowAllBtn, gbc);
        
        refreshProductRecords();
        return productRecordsPanel;
    }

    public void refreshProductRecords(Object [][] data) {
        String[] columnNames = {"Product ID", "Product Name", "Price", "Store Name", "Stock Count", "Description", "Category", "R18"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);
        if (productRecordsPanel.getComponentCount() > 5) {
            productRecordsPanel.remove(5); // Assuming the table is the fifth component
        }

        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        productRecordsPanel.add(scrollPane, gbc);
        productRecordsPanel.revalidate();
        productRecordsPanel.repaint();
    }

    public void refreshProductRecords() {
        Object[][] data = {};
        try {
            data = Model.getProductRecords();
        } catch (SQLException e) {
            showError("Failed to retrieve product records: " + e.getMessage());
        }
        refreshProductRecords(data);
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
        customerSearchField = new JTextField(20);
        customerSearchBtn = new JButton("Search");
        customerSearchBtn.setPreferredSize(new Dimension(150, 25));
        customerShowAllBtn = new JButton("Show All");
        customerShowAllBtn.setPreferredSize(new Dimension(150, 25));
        customerCriteriaComboBox = new JComboBox<>(new String[]{"Customer Name", "Customer ID"});
        
        customerRecordsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = setGBC();

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        customerRecordsPanel.add(new JLabel("Search:"), gbc);

        gbc.gridx++;
        customerRecordsPanel.add(customerSearchField, gbc);

        gbc.gridx++;
        customerRecordsPanel.add(customerCriteriaComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        customerRecordsPanel.add(customerSearchBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        customerRecordsPanel.add(customerShowAllBtn, gbc);

        refreshCustomerRecords();
        return customerRecordsPanel;
    }

    public void refreshCustomerRecords(Object data[][]){
        String[] columnNames = {"Customer ID", "First Name", "Last Name", "Phone Number", "Email Address", "Birthdate", "Address", "Registration Date"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);
        if (customerRecordsPanel.getComponentCount() > 5) {
            customerRecordsPanel.remove(5); // Assuming the table is the fifth component
        }
        
        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        customerRecordsPanel.add(scrollPane, gbc);
        customerRecordsPanel.revalidate();
        customerRecordsPanel.repaint();
    }

    public void refreshCustomerRecords() {
        Object[][] data = {};
        try {
            data = Model.getCustomerRecords();
        } catch (SQLException e) {
            showError("Failed to retrieve customer records: " + e.getMessage());
        }
        refreshCustomerRecords(data);
    }

    private JPanel ordersHandledByLogisticsCompaniesPnl() {
        logisticsRelatedRecordsPanel = new JPanel(new GridBagLayout());

        //search 
        logisticsRelatedRecordSearchField = new JTextField(20);
        logisticsRelatedRecordSearchBtn = new JButton("Search");
        logisticsRelatedRecordCriteriaComboBox = new JComboBox<>(new String[]{"Company Name", "Company ID"});

        GridBagConstraints gbc = setGBC();

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        logisticsRelatedRecordsPanel.add(new JLabel("Search:"), gbc);

        gbc.gridx++;
        logisticsRelatedRecordsPanel.add(logisticsRelatedRecordSearchField, gbc);

        gbc.gridx++;
        logisticsRelatedRecordsPanel.add(logisticsRelatedRecordCriteriaComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        logisticsRelatedRecordsPanel.add(logisticsRelatedRecordSearchBtn, gbc);
        


        refreshOrdersHandledByLogisticsCompanies();
        return logisticsRelatedRecordsPanel;
    }

    public void refreshOrdersHandledByLogisticsCompanies(){
        String[] columnNames = {"Order ID", "Logistics Company ID", "Company Name", "Order Date", "Delivery Date"};

        Object[][] data = {};

        JTable table = new JTable(data, columnNames);

        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        if(logisticsRelatedRecordsPanel.getComponentCount() > 4) {
            logisticsRelatedRecordsPanel.remove(4); 
        }

        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        logisticsRelatedRecordsPanel.add(scrollPane, gbc);

        logisticsRelatedRecordsPanel.revalidate();
        logisticsRelatedRecordsPanel.repaint();
    }

    public void refreshOrdersHandledByLogisticsCompanies(Object[][] data){
        String[] columnNames = {"Order ID", "Logistics Company ID", "Company Name", "Order Date", "Delivery Date"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        if(logisticsRelatedRecordsPanel.getComponentCount() > 4) {
            logisticsRelatedRecordsPanel.remove(4); 
        }

        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        logisticsRelatedRecordsPanel.add(scrollPane, gbc);
        logisticsRelatedRecordsPanel.revalidate();
        logisticsRelatedRecordsPanel.repaint();
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

        storeShowAllBtn = new JButton("Show All");
        storeSearchBtn = new JButton("Search");
        storeCriteriaComboBox = new JComboBox<>(new String[]{"Store Name", "Store ID"});
        storeSearchField = new JTextField(20);

        GridBagConstraints gbc = setGBC();

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        storeRecordsPanel.add(new JLabel("Search:"), gbc);

        gbc.gridx++;
        storeRecordsPanel.add(storeSearchField, gbc);

        gbc.gridx++;
        storeRecordsPanel.add(storeCriteriaComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        storeRecordsPanel.add(storeSearchBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        storeRecordsPanel.add(storeShowAllBtn, gbc);

        refreshStoreRecordsPnl();
        return storeRecordsPanel;
    }

    public void refreshStoreRecordsPnl(Object[][] data) {
        
        String[] columnNames = {"Store ID", "Store Name", "Phone Number", "Email Address", "Address", "Registration Date"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        if(storeRecordsPanel.getComponentCount() > 5) {
            storeRecordsPanel.remove(5); 
        }
        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        storeRecordsPanel.add(scrollPane, gbc);
        storeRecordsPanel.revalidate();
        storeRecordsPanel.repaint();
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

        if(storeRecordsPanel.getComponentCount() > 5) {
            storeRecordsPanel.remove(5); 
        }
        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
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
        logisticsLotNum = new JTextField(COLUMN_WIDTH);
        logisticsStreetName = new JTextField(COLUMN_WIDTH);
        logisticsCityName = new JTextField(COLUMN_WIDTH);
        logisticsZipCode = new JTextField(COLUMN_WIDTH);
        logisticsCountry = new JTextField(COLUMN_WIDTH);

        shipmentScope = new JComboBox<String>();
        shipmentScope.addItem("domestic");
        shipmentScope.addItem("international");
        
        GridBagConstraints gbc = setGBC();

        logisticsAddBtn = new JButton("Add Company");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Logistics Company Name:"), gbc);
        gbc.gridx++;
        panel.add(logisticsCompanyName, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Lot Number:"), gbc);
        gbc.gridx++;
        panel.add(logisticsLotNum, gbc);

        gbc.gridx++;
        panel.add(new JLabel("Street Name:"), gbc);
        gbc.gridx++;
        panel.add(logisticsStreetName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("City Name:"), gbc);
        gbc.gridx++;
        panel.add(logisticsCityName, gbc);

        gbc.gridx++;
        panel.add(new JLabel("Zip Code:"), gbc);
        gbc.gridx++;
        panel.add(logisticsZipCode, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Country:"), gbc);
        gbc.gridx++;
        panel.add(logisticsCountry, gbc);

        gbc.gridwidth = 4;
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
        logisticsRecordsPanel = new JPanel(new GridBagLayout());
        
        logisticsSearchField = new JTextField(20);
        logisticsSearchBtn = new JButton("Search");
        logisticsShowAllBtn = new JButton("Show All");
        logisticsCriteriaComboBox = new JComboBox<>(new String[]{"Company Name", "Company ID"});

        GridBagConstraints gbc = setGBC();

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        logisticsRecordsPanel.add(new JLabel("Search:"), gbc);

        gbc.gridx++;
        logisticsRecordsPanel.add(logisticsSearchField, gbc);
        
        gbc.gridx++;
        logisticsRecordsPanel.add(logisticsCriteriaComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        logisticsRecordsPanel.add(logisticsSearchBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        logisticsRecordsPanel.add(logisticsShowAllBtn, gbc);


        refreshLogisticsRecordPnl();
        return logisticsRecordsPanel;
    }

    public void refreshLogisticsRecordPnl(Object[][] data) {
        String[] columnNames = {"Logistics Company ID", "Company Name", "Address", "Shipment Scope"};
        

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        if(logisticsRecordsPanel.getComponentCount() > 5) {
            logisticsRecordsPanel.remove(5); 
        }


        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        logisticsRecordsPanel.add(scrollPane, gbc);
        logisticsRecordsPanel.revalidate();
        logisticsRecordsPanel.repaint();
    }

    public void refreshLogisticsRecordPnl() {
        String[] columnNames = {"Logistics Company ID", "Company Name", "Address", "Shipment Scope"};
        Object[][] data = {};

        try {
            data = Model.getLogisticsRecords();
        } catch (SQLException e) {
            showError("Failed to retrieve logistics records: " + e.getMessage());
        }

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        if(logisticsRecordsPanel.getComponentCount() > 5) {
            logisticsRecordsPanel.remove(5); 
        }
        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        logisticsRecordsPanel.add(scrollPane, gbc);
        logisticsRecordsPanel.revalidate();
        logisticsRecordsPanel.repaint();
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
        orderPaymentMethod.addItem("Cash");

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
        paymentCancelBtn = new JButton("Cancel Order");
        paymentCancelBtn.setActionCommand("Cancel Order");


        // Order ID
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Order ID:"), gbc);
        gbc.gridx++;
        panel.add(paymentOrderId, gbc);

        // Pay for Order Button
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(paymentBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(paymentCancelBtn, gbc);

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
        customerStatsStartYearTF = new JTextField(COLUMN_WIDTH);
        customerStatsEndYearTF = new JTextField(COLUMN_WIDTH);

        customerStatsStartYearTF.setText("2000");
        customerStatsEndYearTF.setText("2030");

        GridBagConstraints gbc = setGBC();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        customerStatsPanel.add(new JLabel("Customer Stats for"), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        customerStatsPanel.add(new JLabel("Start Year:"), gbc);
        gbc.gridx++;
        customerStatsPanel.add(customerStatsStartYearTF, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        customerStatsPanel.add(new JLabel("End Year:"), gbc);
        gbc.gridx++;
        customerStatsPanel.add(customerStatsEndYearTF, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        customerStatsPanel.add(customerStatsSearchBtn, gbc);
        gbc.gridx++;
        customerStatsPanel.add(customerStatsShowAllBtn, gbc);

        refreshCustomerStatsPnl();
        return customerStatsPanel;
    }

    public void refreshCustomerStatsPnl(Object[][] data) {
        String[] columnNames = {"Year", "Customer Name", "Total Orders", "Total Spent"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        if (customerStatsPanel.getComponentCount() > 7) {
            customerStatsPanel.remove(7);
        }

        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        customerStatsPanel.add(scrollPane, gbc);

        customerStatsPanel.revalidate();
        customerStatsPanel.repaint();
    }

    public void refreshCustomerStatsPnl() {
        Object[][] data = {};
        try {
            data = Model.getCustomerStats(2000, 2030);
        } catch (SQLException e) {
            showError("Failed to retrieve customer stats: " + e.getMessage());
        }
        refreshCustomerStatsPnl(data);
    }

    private JPanel productSalesPnl() {
        productSalesPanel = new JPanel(new GridBagLayout());
        productSalesReportStartYearTF = new JTextField(COLUMN_WIDTH);
        productSalesReportEndYearTF = new JTextField(COLUMN_WIDTH);

        productSalesReportStartYearTF.setText("2000");
        productSalesReportEndYearTF.setText("2030");

        GridBagConstraints gbc = setGBC();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        productSalesPanel.add(new JLabel("Product Sales for"), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        productSalesPanel.add(new JLabel("Start Year:"), gbc);
        gbc.gridx++;
        productSalesPanel.add(productSalesReportStartYearTF, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        productSalesPanel.add(new JLabel("End Year:"), gbc);
        gbc.gridx++;
        productSalesPanel.add(productSalesReportEndYearTF, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        productSalesPanel.add(productSalesReportSearchBtn, gbc);
        gbc.gridx++;
        productSalesPanel.add(productSalesReportShowAllBtn, gbc);

        refreshProductSalesPnl();
        return productSalesPanel;
    }

    public void refreshProductSalesPnl(Object[][] data) {
        String[] columnNames = {"Year", "Category", "Total Sales"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        if (productSalesPanel.getComponentCount() > 7) {
            productSalesPanel.remove(7);
        }
        
        GridBagConstraints gbc = setGBC();
        
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        productSalesPanel.add(scrollPane, gbc);

        productSalesPanel.revalidate();
        productSalesPanel.repaint();
    }

    public void refreshProductSalesPnl() {
        Object[][] data = {};
        try {
            data = Model.getProductSales(2000, 2030);
        } catch (SQLException e) {
            showError("Failed to retrieve product sales: " + e.getMessage());
        }
        refreshProductSalesPnl(data);
    }

    private JPanel paymentReportsPnl() {
        paymentReportsPanel = new JPanel(new GridBagLayout());
        paymentReportStartYearTF = new JTextField(COLUMN_WIDTH);
        paymentReportEndYearTF = new JTextField(COLUMN_WIDTH);

        paymentReportStartYearTF.setText("2000");
        paymentReportEndYearTF.setText("2030");

        GridBagConstraints gbc = setGBC();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        paymentReportsPanel.add(new JLabel("Payment Reports for"), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        paymentReportsPanel.add(new JLabel("Start Year:"), gbc);
        gbc.gridx++;
        paymentReportsPanel.add(paymentReportStartYearTF, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        paymentReportsPanel.add(new JLabel("End Year:"), gbc);
        gbc.gridx++;
        paymentReportsPanel.add(paymentReportEndYearTF, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        paymentReportsPanel.add(paymentReportSearchBtn, gbc);
        gbc.gridx++;
        paymentReportsPanel.add(paymentReportShowAllBtn, gbc);

        refreshPaymentReportsPnl();
        return paymentReportsPanel;
    }

    public void refreshPaymentReportsPnl(Object[][] data) {
        String[] columnNames = {"Year", "Payment Status", "Number of Orders"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        if (paymentReportsPanel.getComponentCount() > 7) {
            paymentReportsPanel.remove(7);
        }

        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        paymentReportsPanel.add(scrollPane, gbc);

        paymentReportsPanel.revalidate();
        paymentReportsPanel.repaint();
    }

    public void refreshPaymentReportsPnl() {
        Object[][] data = {};
        try {
            data = Model.getPaymentReports(2000, 2030);
        } catch (SQLException e) {
            showError("Failed to retrieve payment reports: " + e.getMessage());
        }
        refreshPaymentReportsPnl(data);
    }

    private JPanel affinityPnl() {
        affinityPanel = new JPanel(new GridBagLayout());
        affinityStartYearTF = new JTextField(COLUMN_WIDTH);
        affinityEndYearTF = new JTextField(COLUMN_WIDTH);

        affinityStartYearTF.setText("2000");
        affinityEndYearTF.setText("2030");

        GridBagConstraints gbc = setGBC();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        affinityPanel.add(new JLabel("Affinity for"), gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        affinityPanel.add(new JLabel("Start Year:"), gbc);
        gbc.gridx++;
        affinityPanel.add(affinityStartYearTF, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        affinityPanel.add(new JLabel("End Year:"), gbc);
        gbc.gridx++;
        affinityPanel.add(affinityEndYearTF, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        affinityPanel.add(affinitySearchBtn, gbc);
        gbc.gridx++;
        affinityPanel.add(affinityShowAllBtn, gbc);

        refreshAffinityPnl();
        return affinityPanel;
    }

    public void refreshAffinityPnl(Object[][] data) {
        String[] columnNames = {"Year", "Customer Name", "Store Name", "Number of Orders Made at Store", "Total Amount Spent"};
        
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
    
        adjustColumnWidths(table);

        if (affinityPanel.getComponentCount() > 7) {
            affinityPanel.remove(7);
        }

        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        affinityPanel.add(scrollPane, gbc);

        affinityPanel.revalidate();
        affinityPanel.repaint();
    }

    public void refreshAffinityPnl() {
        Object[][] data = {};
        try {
            data = Model.getAffinity(2000, 2030);
        } catch (SQLException e) {
            showError("Failed to retrieve affinity: " + e.getMessage());
        }
        refreshAffinityPnl(data);
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

    public void setLogisticsRemoveBtn(ActionListener listener) {
        logisticsRemoveBtn.addActionListener(listener);
    }

    public void setProductSearchBtn(ActionListener listener) {
        productSearchBtn.addActionListener(listener);
    }

    public void setProductShowAllBtn(ActionListener listener) {
        productShowAllBtn.addActionListener(listener);
    }

    public void setCustomerSearchBtn(ActionListener listener) {
        customerSearchBtn.addActionListener(listener);
    }

    public void setCustomerShowAllBtn(ActionListener listener) {
        customerShowAllBtn.addActionListener(listener);
    }

    public void setPaymentCancelBtn(ActionListener listener) {
        paymentCancelBtn.addActionListener(listener);
    }
    
    public void setLogisticsSearchBtn(ActionListener listener) {
        logisticsSearchBtn.addActionListener(listener);
    }

    public void setLogisticsShowAllBtn(ActionListener listener) {
        logisticsShowAllBtn.addActionListener(listener);
    }

    public void setStoreSearchBtn(ActionListener listener) {
        storeSearchBtn.addActionListener(listener);
    }

    public void setStoreShowAllBtn(ActionListener listener) {
        storeShowAllBtn.addActionListener(listener);
    }

    public void setProductSalesCategory(ActionListener listener) {
        productSalesCategory.addActionListener(listener);
    }

    public void setCustomerStatsSearchBtn(ActionListener listener) {
        customerStatsSearchBtn.addActionListener(listener);
    }

    public void setCustomerStatsShowAllBtn(ActionListener listener) {
        customerStatsShowAllBtn.addActionListener(listener);
    }

    public void setProductSalesReportSearchBtn(ActionListener listener) {
        productSalesReportSearchBtn.addActionListener(listener);
    }

    public void setProductSalesReportShowAllBtn(ActionListener listener) {
        productSalesReportShowAllBtn.addActionListener(listener);
    }

    public void setPaymentReportSearchBtn(ActionListener listener) {
        paymentReportSearchBtn.addActionListener(listener);
    }

    public void setPaymentReportShowAllBtn(ActionListener listener) {
        paymentReportShowAllBtn.addActionListener(listener);
    }

    public void setAffinitySearchBtn(ActionListener listener) {
        affinitySearchBtn.addActionListener(listener);
    }

    public void setAffinityShowAllBtn(ActionListener listener) {
        affinityShowAllBtn.addActionListener(listener);
    }

    public void setLogisticsRelatedRecordSearchBtn(ActionListener listener) {
        logisticsRelatedRecordSearchBtn.addActionListener(listener);
    }


    //getters

    public String getProductId() {
        return productId.getText();
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

    public String getStoreId() {
        return storeId.getText();
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

    public String getLogisticsLotNum() {
        return logisticsLotNum.getText();
    }

    public String getLogisticsStreetName() {
        return logisticsStreetName.getText();
    }

    public String getLogisticsCityName() {
        return logisticsCityName.getText();
    }

    public String getLogisticsZipCode() {
        return logisticsZipCode.getText();
    }

    public String getLogisticsCountry() {
        return logisticsCountry.getText();
    }

    public String getLogisticsCompanyName() {
        return logisticsCompanyName.getText();
    }

    public String getLogisticsScope() {
        return shipmentScope.getSelectedItem().toString();
    }

    public String getProductSalesCategory() {
        return productSalesCategory.getSelectedItem().toString();
    }

    public String getCustomerStatsStartYear() {
        return customerStatsStartYearTF.getText();
    }

    public String getCustomerStatsEndYear() {
        return customerStatsEndYearTF.getText();
    }

    public String getProductSalesStartYear() {
        return productSalesReportStartYearTF.getText();
    }

    public String getProductSalesEndYear() {
        return productSalesReportEndYearTF.getText();
    }

    public String getPaymentReportStartYear() {
        return paymentReportStartYearTF.getText();
    }

    public String getPaymentReportEndYear() {
        return paymentReportEndYearTF.getText();
    }

    public String getAffinityStartYear() {
        return affinityStartYearTF.getText();
    }

    public String getAffinityEndYear() {
        return affinityEndYearTF.getText();
    }

    public String getProductSearchField() {
        return productSearchField.getText();
    }

    public String getProductSearchCriteriaComboBox() {
        return productSearchCriteriaComboBox.getSelectedItem().toString();
    }

    public String getCustomerSearchField() {
        return customerSearchField.getText();
    }

    public String getCustomerCriteriaComboBox() {
        return customerCriteriaComboBox.getSelectedItem().toString();
    }

    public String getStoreSearchField() {
        return storeSearchField.getText();
    }

    public String getStoreCriteriaComboBox() {
        return storeCriteriaComboBox.getSelectedItem().toString();
    }

    public String getLogisticsSearchField() {
        return logisticsSearchField.getText();
    }

    public String getLogisticsCriteriaComboBox() {
        return logisticsCriteriaComboBox.getSelectedItem().toString();
    }

    public String getLogisticsRelatedRecordSearchField() {
        return logisticsRelatedRecordSearchField.getText();
    }

    public String getLogisticsRelatedRecordCriteriaComboBox() {
        return logisticsRelatedRecordCriteriaComboBox.getSelectedItem().toString();
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

        customerStatsStartYearTF.setText("2000");
        customerStatsEndYearTF.setText("2030");

        productSalesReportStartYearTF.setText("2000");
        productSalesReportEndYearTF.setText("2030");

        paymentReportStartYearTF.setText("2000");
        paymentReportEndYearTF.setText("2030");

        affinityStartYearTF.setText("2000");
        affinityEndYearTF.setText("2030");

        refreshProductRecords();
        refreshCustomerRecords();
        refreshStoresCustomerBoughtFrom();
        refreshStoreRecordsPnl();
        refreshLogisticsRecordPnl();
        refreshCustomerStatsPnl();
        refreshProductSalesPnl();
        refreshPaymentReportsPnl();
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
