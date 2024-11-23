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
import java.util.*;

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

    private JTextField productUpdateId, productUpdateName, 
            productUpdatePrice, productUpdateDescription;
    private JCheckBox productUpdateR18;
    private JButton productUpdateSelectBtn, productUpdateBtn;
    private JPanel productRelatedRecordsPanel;
    private JButton productRelatedRecordsSearchBtn, productRelatedRecordsShowAllBtn;
    private JComboBox<String> productRelatedRecordsCriteriaComboBox;
    private JTextField productRelatedRecordsSearchField;

    // Customers Table
    private JTextField customerId, customerFirstName, customerLastName,
            customerPhoneNumber, customerEmailAddress,
            customerLotNum, customerStreetName, customerCityName,
            customerZipCode, customerCountry;
    private JDatePickerImpl customerBirthdate;
    private JButton customerSearchBtn, customerShowAllBtn;

    private JComboBox<String> customerCriteriaComboBox;
    private JTextField customerSearchField;

    private JComboBox<String> customerStoresCriteriaComboBox;
    private JTextField customerStoresSearchField;
    private JButton customerStoresSearchBtn, customerStoresShowAllBtn;

    private JTextField customerUpdateId, customerUpdateFirstName, customerUpdateLastName,
            customerUpdatePhoneNumber, customerUpdateEmailAddress,
            customerUpdateLotNum, customerUpdateStreetName, customerUpdateCityName,
            customerUpdateZipCode, customerUpdateCountry;
    private JButton customerUpdateSelectBtn, customerUpdateBtn;

    // Stores Table
    private JTextField storeId, storeName,
            storePhoneNumber, storeEmailAddress,
            storeLotNum, storeStreetName, storeCityName,
            storeZipCode, storeCountry;

    private JButton storeSearchBtn, storeShowAllBtn;
    private JComboBox<String> storeCriteriaComboBox;
    private JTextField storeSearchField;

    private JPanel storeProductListPanel;
    private JButton storeProductListSearchBtn, storeProductListShowAllBtn;
    private JComboBox<String> storeProductListCriteriaComboBox;
    private JTextField storeProductListSearchField;

    private JTextField storeUpdateId, storeUpdateName,
            storeUpdatePhoneNumber, storeUpdateEmailAddress,
            storeUpdateLotNum, storeUpdateStreetName, storeUpdateCityName,
            storeUpdateZipCode, storeUpdateCountry;
    private JButton storeUpdateSelectBtn, storeUpdateBtn;

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

    private JTextField logisticsUpdateId, logisticsUpdateName,
            logisticsUpdateLotNum, logisticsUpdateStreetName, logisticsUpdateCityName,
            logisticsUpdateZipCode, logisticsUpdateCountry;
    private JComboBox<String> logisticsUpdateShipmentScope;
    private JButton logisticsUpdateSelectBtn, logisticsUpdateBtn;

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
        productUpdateSelectBtn = new JButton("Select Product");
        productUpdateBtn = new JButton("Update Product");
        customerAddBtn = new JButton("Add Customer");
        customerRemoveBtn = new JButton("Remove Customer");
        customerUpdateSelectBtn = new JButton("Select Customer");
        customerUpdateBtn = new JButton("Update Customer");
        customerSearchBtn = new JButton("Search");
        customerShowAllBtn = new JButton("Show All");
        customerStoresSearchBtn = new JButton("Search");
        customerStoresShowAllBtn = new JButton("Show All");
        storeAddBtn = new JButton("Add Store");
        storeRemoveBtn = new JButton("Remove Store");
        storeUpdateSelectBtn = new JButton("Select Store");
        storeUpdateBtn = new JButton("Update Store");
        storeProductListSearchBtn = new JButton("Search");
        storeProductListShowAllBtn = new JButton("Show All");
        logisticsAddBtn = new JButton("Add Logistics Company");
        logisticsRemoveBtn = new JButton("Remove Logistics Company");
        logisticsUpdateSelectBtn = new JButton("Select Logistics Company");
        logisticsUpdateBtn = new JButton("Update Logistics Company");
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

        logisticsUpdateShipmentScope = new JComboBox<String>(new String[] {"Domestic", "International"});
        productSalesCategory = new JComboBox<String>(new String[] {"Clothing", "Electronics", "Beauty & Personal Care", "Food & Beverages", "Toys", "Appliances", "Home & Living"});

        JTabbedPane mainTabbedPane = new JTabbedPane();

        // Records Management Panel
        JPanel recordsManagementPanel = new JPanel(new BorderLayout());
        JTabbedPane recordsManagementTabbedPane = new JTabbedPane();

        // Products Panel
        JPanel productsPanel = new JPanel(new BorderLayout());
        JTabbedPane productsTabbedPane = new JTabbedPane();
        productsTabbedPane.addTab("Product Records", productRecordsPnl());
        productsTabbedPane.addTab("Stores Selling Category Same as Product",  productRelatedRecordsPnl()); // TODO: Implement panel
        productsTabbedPane.addTab("Add Product", productAddPnl());
        productsTabbedPane.addTab("Remove Product", productRemovePnl());
        productsTabbedPane.addTab("Update Product", productUpdatePnl());
        productsPanel.add(productsTabbedPane, BorderLayout.CENTER);

        // Customers Panel
        JPanel customersPanel = new JPanel(new BorderLayout());
        JTabbedPane customersTabbedPane = new JTabbedPane();
        customersTabbedPane.addTab("Customer Records", customerRecordsPnl());
        customersTabbedPane.addTab("Stores Customers Bought From", storesCustomersBoughtFromPnl());
        customersTabbedPane.addTab("Add Customer", customerAddPnl());
        customersTabbedPane.addTab("Remove Customer", customerRemovePnl());
        customersTabbedPane.addTab("Update Customer", customerUpdatePnl());
        customersPanel.add(customersTabbedPane, BorderLayout.CENTER);

        // Stores Panel
        JPanel storesPanel = new JPanel(new BorderLayout());
        JTabbedPane storesTabbedPane = new JTabbedPane();
        storesTabbedPane.addTab("Store Records", storeRecordsPnl());
        storesTabbedPane.addTab("Product List of a Store", storeProductListPnl()); // TODO: Implement panel
        storesTabbedPane.addTab("Add Store", storeAddPnl());
        storesTabbedPane.addTab("Remove Store", storeRemovePnl());
        storesTabbedPane.addTab("Update Store", storeUpdatePnl());
        storesPanel.add(storesTabbedPane, BorderLayout.CENTER);

        // Logistics Companies Panel
        JPanel logisticsPanel = new JPanel(new BorderLayout());
        JTabbedPane logisticsTabbedPane = new JTabbedPane();
        logisticsTabbedPane.addTab("Logistics Company Records", logisticsRecordPnl());
        logisticsTabbedPane.addTab("Orders Handled by Logistics Companies", ordersHandledByLogisticsCompaniesPnl());
        logisticsTabbedPane.addTab("Add Logistics Company", logisticsAddPnl());
        logisticsTabbedPane.addTab("Remove Logistics Company", logisticsRemovePnl());
        logisticsTabbedPane.addTab("Update Logistics Company", logisticsUpdatePnl());
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

    private JPanel productUpdatePnl() {
        JPanel parentPanel = new JPanel(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel(new GridBagLayout());
        JPanel rightPanel = new JPanel(new GridBagLayout());

        productUpdateId = new JTextField(COLUMN_WIDTH);
        productUpdateName = new JTextField(COLUMN_WIDTH);
        productUpdatePrice = new JTextField(COLUMN_WIDTH);
        productUpdateDescription = new JTextField(COLUMN_WIDTH);

        productUpdateR18 = new JCheckBox("R18");

        productUpdateSelectBtn = new JButton("Select Product");
        productUpdateSelectBtn.setActionCommand("Select Product");
        productUpdateBtn = new JButton("Update Product");
        productUpdateBtn.setActionCommand("Update Product");

        GridBagConstraints gbc = setGBC();

        // Left Panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(new JLabel("Product ID:"), gbc);
        gbc.gridx++;
        leftPanel.add(productUpdateId, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        leftPanel.add(productUpdateSelectBtn, gbc);

        // Right Panel
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(new JLabel("Product Name:"), gbc);
        gbc.gridx++;
        rightPanel.add(productUpdateName, gbc);

        gbc.gridx++;
        rightPanel.add(new JLabel("Price:"), gbc);
        gbc.gridx++;
        rightPanel.add(productUpdatePrice, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Description:"), gbc);
        gbc.gridwidth = 2;
        gbc.gridx++;
        rightPanel.add(productUpdateDescription, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("R18:"), gbc);
        gbc.gridx++;
        rightPanel.add(productUpdateR18, gbc);

        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(productUpdateBtn, gbc);

        productUpdateEditable(false);

        parentPanel.add(leftPanel);
        parentPanel.add(rightPanel);

        return parentPanel;
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
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        productRecordsPanel.add(new JLabel("Search:"), gbc);

        gbc.gridx++;
        productRecordsPanel.add(productSearchField, gbc);

        gbc.gridx++;
        productRecordsPanel.add(productSearchCriteriaComboBox, gbc);

        gbc.gridwidth = 2;
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

    private JPanel customerUpdatePnl() {
        JPanel parentPanel = new JPanel(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel(new GridBagLayout());
        JPanel rightPanel = new JPanel(new GridBagLayout());

        customerUpdateId = new JTextField(COLUMN_WIDTH);
        customerUpdateFirstName = new JTextField(COLUMN_WIDTH);
        customerUpdateLastName = new JTextField(COLUMN_WIDTH);
        customerUpdatePhoneNumber = new JTextField(COLUMN_WIDTH);
        customerUpdateEmailAddress = new JTextField(COLUMN_WIDTH);
        customerUpdateLotNum = new JTextField(COLUMN_WIDTH);
        customerUpdateStreetName = new JTextField(COLUMN_WIDTH);
        customerUpdateCityName = new JTextField(COLUMN_WIDTH);
        customerUpdateZipCode = new JTextField(COLUMN_WIDTH);
        customerUpdateCountry = new JTextField(COLUMN_WIDTH);

        customerUpdateSelectBtn = new JButton("Select Customer");
        customerUpdateSelectBtn.setActionCommand("Select Customer");
        customerUpdateBtn = new JButton("Update Customer");
        customerUpdateBtn.setActionCommand("Update Customer");

        GridBagConstraints gbc = setGBC();

        // Left Panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(new JLabel("Customer ID:"), gbc);
        gbc.gridx++;
        leftPanel.add(customerUpdateId, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        leftPanel.add(customerUpdateSelectBtn, gbc);


        // Right Panel
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(new JLabel("First Name:"), gbc);
        gbc.gridx++;
        rightPanel.add(customerUpdateFirstName, gbc);
        gbc.gridx++;
        rightPanel.add(new JLabel("Last Name:"), gbc);
        gbc.gridx++;
        rightPanel.add(customerUpdateLastName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Phone Number:"), gbc);
        gbc.gridx++;
        rightPanel.add(customerUpdatePhoneNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Email Address:"), gbc);
        gbc.gridwidth = 2;
        gbc.gridx++;
        rightPanel.add(customerUpdateEmailAddress, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Lot Number:"), gbc);
        gbc.gridx++;
        rightPanel.add(customerUpdateLotNum, gbc);
        gbc.gridx++;
        rightPanel.add(new JLabel("Street Name:"), gbc);
        gbc.gridx++;
        rightPanel.add(customerUpdateStreetName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("City Name:"), gbc);
        gbc.gridx++;
        rightPanel.add(customerUpdateCityName, gbc);
        gbc.gridx++;
        rightPanel.add(new JLabel("Zip Code:"), gbc);
        gbc.gridx++;
        rightPanel.add(customerUpdateZipCode, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Country:"), gbc);
        gbc.gridx++;
        rightPanel.add(customerUpdateCountry, gbc);

        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(customerUpdateBtn, gbc);

        customerUpdateEditable(false);


        parentPanel.add(leftPanel);
        parentPanel.add(rightPanel);

        return parentPanel;
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
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        customerRecordsPanel.add(new JLabel("Search:"), gbc);

        gbc.gridx++;
        customerRecordsPanel.add(customerSearchField, gbc);

        gbc.gridx++;
        customerRecordsPanel.add(customerCriteriaComboBox, gbc);

        gbc.gridwidth = 2;
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
        logisticsRelatedRecordSearchBtn.setPreferredSize(new Dimension(150, 25));
        logisticsRelatedRecordCriteriaComboBox = new JComboBox<>(new String[]{"Company Name", "Company ID"});

        GridBagConstraints gbc = setGBC();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        logisticsRelatedRecordsPanel.add(new JLabel("Search:"), gbc);

        gbc.gridx++;
        logisticsRelatedRecordsPanel.add(logisticsRelatedRecordSearchField, gbc);

        gbc.gridx++;
        logisticsRelatedRecordsPanel.add(logisticsRelatedRecordCriteriaComboBox, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        logisticsRelatedRecordsPanel.add(logisticsRelatedRecordSearchBtn, gbc);
        


        refreshOrdersHandledByLogisticsCompanies();
        return logisticsRelatedRecordsPanel;
    }

    public void refreshOrdersHandledByLogisticsCompanies(){
        String[] columnNames = {"Order ID", "Logistics Company ID", "Company Name", "Order Date", "Delivery Date"};

        Object[][] data = {};
        
        try {
            data = Model.getOrdersHandledByLogisticsCompanyName(" ");
        } catch (Exception e) {
            showError("Failed to retrieve orders handled by logistics companies: " + e.getMessage());
        }


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

    private JPanel productRelatedRecordsPnl(){
        productRelatedRecordsPanel = new JPanel(new GridBagLayout());

        productRelatedRecordsSearchField = new JTextField(20);
        productRelatedRecordsSearchBtn = new JButton("Search");
        productRelatedRecordsSearchBtn.setPreferredSize(new Dimension(150, 25));
        productRelatedRecordsCriteriaComboBox = new JComboBox<>(new String[]{"Product Name", "Product ID"});

        GridBagConstraints gbc = setGBC();

        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        productRelatedRecordsPanel.add(new JLabel("Search:"), gbc);

        gbc.gridx++;
        productRelatedRecordsPanel.add(productRelatedRecordsSearchField, gbc);

        gbc.gridx++;
        productRelatedRecordsPanel.add(productRelatedRecordsCriteriaComboBox, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        productRelatedRecordsPanel.add(productRelatedRecordsSearchBtn, gbc);
        
        refreshProductRelatedRecords();
        return productRelatedRecordsPanel;
    }

    public void refreshProductRelatedRecords(Object[][] data){
        String[] columnNames = {"Store Name", "Store ID", "Product Category", "Number of Products"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        if(productRelatedRecordsPanel.getComponentCount() > 4) {
            productRelatedRecordsPanel.remove(4); 
        }

        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        
        productRelatedRecordsPanel.add(scrollPane, gbc);

        productRelatedRecordsPanel.revalidate();
        productRelatedRecordsPanel.repaint();
    }

    public void refreshProductRelatedRecords(){
    
        String[] columnNames = {"Store Name", "Store ID", "Product Category", "Number of Products"};

        Object[][] data = {};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        if(productRelatedRecordsPanel.getComponentCount() > 4) {
            productRelatedRecordsPanel.remove(4); 
        }

        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 5;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        productRelatedRecordsPanel.add(scrollPane, gbc);

        productRelatedRecordsPanel.revalidate();
        productRelatedRecordsPanel.repaint();
    }

    private JPanel storesCustomersBoughtFromPnl() {
        storesCustomersBoughtFromPnl = new JPanel(new GridBagLayout());
        customerStoresSearchBtn = new JButton("Search");
        customerStoresSearchBtn.setPreferredSize(new Dimension(150, 25));
        customerStoresShowAllBtn = new JButton("Show All");
        customerStoresShowAllBtn.setPreferredSize(new Dimension(150, 25));
        customerStoresSearchField = new JTextField(20);
        customerStoresCriteriaComboBox = new JComboBox<>(new String[]{"Customer Name", "Customer ID"});

        GridBagConstraints gbc = setGBC();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        storesCustomersBoughtFromPnl.add(new JLabel("Search:"), gbc);
        gbc.gridx++;
        storesCustomersBoughtFromPnl.add(customerStoresSearchField, gbc);
        gbc.gridx++;
        storesCustomersBoughtFromPnl.add(customerStoresCriteriaComboBox, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        storesCustomersBoughtFromPnl.add(customerStoresSearchBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        storesCustomersBoughtFromPnl.add(customerStoresShowAllBtn, gbc);

        refreshStoresCustomerBoughtFrom();
        return storesCustomersBoughtFromPnl;
    }

    public void refreshStoresCustomerBoughtFrom(Object[][] data) {
        String[] columnNames = {"Customer Name", "Store Name"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        if (storesCustomersBoughtFromPnl.getComponentCount() > 5) {
            storesCustomersBoughtFromPnl.remove(5);
        }

        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        storesCustomersBoughtFromPnl.add(scrollPane, gbc);
        storesCustomersBoughtFromPnl.revalidate();
        storesCustomersBoughtFromPnl.repaint();
    }

    public void refreshStoresCustomerBoughtFrom() {
        Object[][] data = {};
        try {
            data = Model.getStoresCustomersBoughtFrom();
        } catch (SQLException e) {
            showError("Failed to retrieve stores customer bought from: " + e.getMessage());
        }
        refreshStoresCustomerBoughtFrom(data);
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

    private JPanel storeUpdatePnl() {
        JPanel parentPanel = new JPanel(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel(new GridBagLayout());
        JPanel rightPanel = new JPanel(new GridBagLayout());

        storeUpdateId = new JTextField(COLUMN_WIDTH);
        storeUpdateName = new JTextField(COLUMN_WIDTH);
        storeUpdatePhoneNumber = new JTextField(COLUMN_WIDTH);
        storeUpdateEmailAddress = new JTextField(COLUMN_WIDTH);
        storeUpdateLotNum = new JTextField(COLUMN_WIDTH);
        storeUpdateStreetName = new JTextField(COLUMN_WIDTH);
        storeUpdateCityName = new JTextField(COLUMN_WIDTH);
        storeUpdateZipCode = new JTextField(COLUMN_WIDTH);
        storeUpdateCountry = new JTextField(COLUMN_WIDTH);

        storeUpdateSelectBtn = new JButton("Select Store");
        storeUpdateSelectBtn.setActionCommand("Select Store");
        storeUpdateBtn = new JButton("Update Store");
        storeUpdateBtn.setActionCommand("Update Store");

        GridBagConstraints gbc = setGBC();

        // Left Panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(new JLabel("Store ID:"), gbc);
        gbc.gridx++;
        leftPanel.add(storeUpdateId, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        leftPanel.add(storeUpdateSelectBtn, gbc);

        // Right Panel
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(new JLabel("Store Name:"), gbc);
        gbc.gridx++;
        rightPanel.add(storeUpdateName, gbc);

        gbc.gridx++;
        rightPanel.add(new JLabel("Phone Number:"), gbc);
        gbc.gridx++;
        rightPanel.add(storeUpdatePhoneNumber, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Email Address:"), gbc);
        gbc.gridwidth = 2;
        gbc.gridx++;
        rightPanel.add(storeUpdateEmailAddress, gbc);

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Lot Number:"), gbc);
        gbc.gridx++;
        rightPanel.add(storeUpdateLotNum, gbc);
        gbc.gridx++;
        rightPanel.add(new JLabel("Street Name:"), gbc);
        gbc.gridx++;
        rightPanel.add(storeUpdateStreetName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("City Name:"), gbc);
        gbc.gridx++;
        rightPanel.add(storeUpdateCityName, gbc);
        gbc.gridx++;
        rightPanel.add(new JLabel("Zip Code:"), gbc);
        gbc.gridx++;
        rightPanel.add(storeUpdateZipCode, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Country:"), gbc);
        gbc.gridx++;
        rightPanel.add(storeUpdateCountry, gbc);

        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(storeUpdateBtn, gbc);

        storeUpdateEditable(false);

        parentPanel.add(leftPanel);
        parentPanel.add(rightPanel);

        return parentPanel;
    }

    private JPanel storeRecordsPnl() {
        storeRecordsPanel = new JPanel(new GridBagLayout());

        storeShowAllBtn = new JButton("Show All");
        storeSearchBtn = new JButton("Search");
        storeSearchBtn.setPreferredSize(new Dimension(150, 25));
        storeShowAllBtn.setPreferredSize(new Dimension(150, 25));
        storeCriteriaComboBox = new JComboBox<>(new String[]{"Store Name", "Store ID"});
        storeSearchField = new JTextField(20);

        GridBagConstraints gbc = setGBC();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        storeRecordsPanel.add(new JLabel("Search:"), gbc);

        gbc.gridx++;
        storeRecordsPanel.add(storeSearchField, gbc);

        gbc.gridx++;
        storeRecordsPanel.add(storeCriteriaComboBox, gbc);

        gbc.gridwidth = 2;
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

    private JPanel storeProductListPnl() {
        storeProductListPanel = new JPanel(new GridBagLayout());

        storeProductListSearchBtn = new JButton("Search");
        storeProductListSearchBtn.setPreferredSize(new Dimension(150, 25));
        storeProductListShowAllBtn = new JButton("Show All");
        storeProductListShowAllBtn.setPreferredSize(new Dimension(150, 25));
        storeProductListCriteriaComboBox = new JComboBox<>(new String[]{"Store Name", "Store ID"});
        storeProductListSearchField = new JTextField(20);

        GridBagConstraints gbc = setGBC();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        storeProductListPanel.add(new JLabel("Search:"), gbc);

        gbc.gridx++;
        storeProductListPanel.add(storeProductListSearchField, gbc);

        gbc.gridx++;
        storeProductListPanel.add(storeProductListCriteriaComboBox, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        storeProductListPanel.add(storeProductListSearchBtn, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        storeProductListPanel.add(storeProductListShowAllBtn, gbc);

        refreshStoreProductListPnl();
        return storeProductListPanel;
    }

    public void refreshStoreProductListPnl(Object[][] data) {
        String[] columnNames = {"Product ID", "Product Name", "Price", "Store Name", "Stock Count", "Description", "Category", "R18"};

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        adjustColumnWidths(table);

        if(storeProductListPanel.getComponentCount() > 5) {
            storeProductListPanel.remove(5); 
        }
        GridBagConstraints gbc = setGBC();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 4;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        storeProductListPanel.add(scrollPane, gbc);
        storeProductListPanel.revalidate();
        storeProductListPanel.repaint();
    }

    public void refreshStoreProductListPnl() {
        Object[][] data = {};
        try {
            data = Model.getProductRecords();
        } catch (SQLException e) {
            showError("Failed to retrieve store product list: " + e.getMessage());
        }
        refreshStoreProductListPnl(data);
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
        shipmentScope.addItem("Domestic");
        shipmentScope.addItem("International");
        
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

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Shipment Scope:"), gbc);
        gbc.gridx++;
        panel.add(shipmentScope, gbc);

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

    private JPanel logisticsUpdatePnl() {
        JPanel parentPanel = new JPanel(new GridLayout(1, 2));
        JPanel leftPanel = new JPanel(new GridBagLayout());
        JPanel rightPanel = new JPanel(new GridBagLayout());

        logisticsUpdateId = new JTextField(COLUMN_WIDTH);
        logisticsUpdateName = new JTextField(COLUMN_WIDTH);
        logisticsUpdateLotNum = new JTextField(COLUMN_WIDTH);
        logisticsUpdateStreetName = new JTextField(COLUMN_WIDTH);
        logisticsUpdateCityName = new JTextField(COLUMN_WIDTH);
        logisticsUpdateZipCode = new JTextField(COLUMN_WIDTH);
        logisticsUpdateCountry = new JTextField(COLUMN_WIDTH);
        logisticsUpdateShipmentScope = new JComboBox<String>();
        logisticsUpdateShipmentScope.addItem("Domestic");
        logisticsUpdateShipmentScope.addItem("International");
        logisticsUpdateShipmentScope.setSelectedItem(null);

        logisticsUpdateSelectBtn = new JButton("Select Company");
        logisticsUpdateSelectBtn.setActionCommand("Select Company");
        logisticsUpdateBtn = new JButton("Update Company");
        logisticsUpdateBtn.setActionCommand("Update Company");

        GridBagConstraints gbc = setGBC();

        // Left Panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        leftPanel.add(new JLabel("Logistics Company ID:"), gbc);
        gbc.gridx++;
        leftPanel.add(logisticsUpdateId, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        leftPanel.add(logisticsUpdateSelectBtn, gbc);

        // Right Panel
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        rightPanel.add(new JLabel("Logistics Company Name:"), gbc);
        gbc.gridx++;
        rightPanel.add(logisticsUpdateName, gbc);

        gbc.gridx++;
        rightPanel.add(new JLabel("Lot Number:"), gbc);
        gbc.gridx++;
        rightPanel.add(logisticsUpdateLotNum, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Street Name:"), gbc);
        gbc.gridx++;
        rightPanel.add(logisticsUpdateStreetName, gbc);

        gbc.gridx++;
        rightPanel.add(new JLabel("City Name:"), gbc);
        gbc.gridx++;
        rightPanel.add(logisticsUpdateCityName, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Zip Code:"), gbc);
        gbc.gridx++;
        rightPanel.add(logisticsUpdateZipCode, gbc);

        gbc.gridx++;
        rightPanel.add(new JLabel("Country:"), gbc);
        gbc.gridx++;
        rightPanel.add(logisticsUpdateCountry, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(new JLabel("Shipment Scope:"), gbc);
        gbc.gridx++;
        rightPanel.add(logisticsUpdateShipmentScope, gbc);

        gbc.gridwidth = 4;
        gbc.gridx = 0;
        gbc.gridy++;
        rightPanel.add(logisticsUpdateBtn, gbc);

        logisticsUpdateEditable(false);

        parentPanel.add(leftPanel);
        parentPanel.add(rightPanel);

        return parentPanel;
    }
    
    private JPanel logisticsRecordPnl() {
        logisticsRecordsPanel = new JPanel(new GridBagLayout());
        
        logisticsSearchField = new JTextField(20);
        logisticsSearchBtn = new JButton("Search");
        logisticsShowAllBtn = new JButton("Show All");
        logisticsSearchBtn.setPreferredSize(new Dimension(150, 25));
        logisticsShowAllBtn.setPreferredSize(new Dimension(150, 25));
        logisticsCriteriaComboBox = new JComboBox<>(new String[]{"Company Name", "Company ID"});

        GridBagConstraints gbc = setGBC();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;

        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        logisticsRecordsPanel.add(new JLabel("Search:"), gbc);

        gbc.gridx++;
        logisticsRecordsPanel.add(logisticsSearchField, gbc);
        
        gbc.gridx++;
        logisticsRecordsPanel.add(logisticsCriteriaComboBox, gbc);

        gbc.gridwidth = 2;
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
        Object[][] data = {};
        try {
            data = Model.getLogisticsRecords();
        } catch (SQLException e) {
            showError("Failed to retrieve logistics records: " + e.getMessage());
        }
        refreshLogisticsRecordPnl(data);
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

        customerStatsSearchBtn.setPreferredSize(new Dimension(150, 25));
        customerStatsShowAllBtn.setPreferredSize(new Dimension(150, 25));

        GridBagConstraints gbc = setGBC();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        customerStatsPanel.add(new JLabel("Customer Stats for"), gbc);

        gbc.gridwidth = 1;
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

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        customerStatsPanel.add(customerStatsSearchBtn, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
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
        gbc.gridy = 5;
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

        productSalesReportSearchBtn.setPreferredSize(new Dimension(150, 25));
        productSalesReportShowAllBtn.setPreferredSize(new Dimension(150, 25));

        GridBagConstraints gbc = setGBC();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        productSalesPanel.add(new JLabel("Product Sales for"), gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.gridy++;
        productSalesPanel.add(new JLabel("Start Year:"), gbc);
        gbc.gridx++;
        productSalesPanel.add(productSalesReportStartYearTF, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        productSalesPanel.add(new JLabel("End Year:"), gbc);
        gbc.gridx++;
        productSalesPanel.add(productSalesReportEndYearTF, gbc);
        
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        productSalesPanel.add(productSalesReportSearchBtn, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
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
        gbc.gridy = 5;
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

        paymentReportSearchBtn.setPreferredSize(new Dimension(150, 25));
        paymentReportShowAllBtn.setPreferredSize(new Dimension(150, 25));

        GridBagConstraints gbc = setGBC();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        paymentReportsPanel.add(new JLabel("Payment Reports for"), gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.gridy++;
        paymentReportsPanel.add(new JLabel("Start Year:"), gbc);
        gbc.gridx++;
        paymentReportsPanel.add(paymentReportStartYearTF, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        paymentReportsPanel.add(new JLabel("End Year:"), gbc);
        gbc.gridx++;
        paymentReportsPanel.add(paymentReportEndYearTF, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        paymentReportsPanel.add(paymentReportSearchBtn, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
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
        gbc.gridy = 5;
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

        affinitySearchBtn.setPreferredSize(new Dimension(150, 25));
        affinityShowAllBtn.setPreferredSize(new Dimension(150, 25));

        GridBagConstraints gbc = setGBC();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        affinityPanel.add(new JLabel("Affinity for"), gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.gridy++;
        affinityPanel.add(new JLabel("Start Year:"), gbc);
        gbc.gridx++;
        affinityPanel.add(affinityStartYearTF, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        affinityPanel.add(new JLabel("End Year:"), gbc);
        gbc.gridx++;
        affinityPanel.add(affinityEndYearTF, gbc);

        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy++;
        affinityPanel.add(affinitySearchBtn, gbc);
        
        gbc.gridx = 0;
        gbc.gridy++;
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
        gbc.gridy = 5;
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

    public void setProductUpdateSelectBtn(ActionListener listener) {
        productUpdateSelectBtn.addActionListener(listener);
    }

    public void setProductUpdateBtn(ActionListener listener) {
        productUpdateBtn.addActionListener(listener);
    }

    public void setCustomerAddBtn(ActionListener listener) {
        customerAddBtn.addActionListener(listener);
    }

    public void setCustomerRemoveBtn(ActionListener listener) {
        customerRemoveBtn.addActionListener(listener);
    }

    public void setCustomerUpdateSelectBtn(ActionListener listener) {
        customerUpdateSelectBtn.addActionListener(listener);
    }

    public void setCustomerUpdateBtn(ActionListener listener) {
        customerUpdateBtn.addActionListener(listener);
    }

    public void setCustomerStoresSearchBtn(ActionListener listener) {
        customerStoresSearchBtn.addActionListener(listener);
    }

    public void setCustomerStoresShowAllBtn(ActionListener listener) {
        customerStoresShowAllBtn.addActionListener(listener);
    }

    public void setStoreAddBtn(ActionListener listener) {
        storeAddBtn.addActionListener(listener);
    }

    public void setStoreRemoveBtn(ActionListener listener) {
        storeRemoveBtn.addActionListener(listener);
    }

    public void setStoreSearchBtn(ActionListener listener) {
        storeSearchBtn.addActionListener(listener);
    }

    public void setStoreShowAllBtn(ActionListener listener) {
        storeShowAllBtn.addActionListener(listener);
    }

    public void setStoreProductListSearchBtn(ActionListener listener) {
        storeProductListSearchBtn.addActionListener(listener);
    }

    public void setStoreProductListShowAllBtn(ActionListener listener) {
        storeProductListShowAllBtn.addActionListener(listener);
    }

    public void setStoreUpdateSelectBtn(ActionListener listener) {
        storeUpdateSelectBtn.addActionListener(listener);
    }

    public void setStoreUpdateBtn(ActionListener listener) {
        storeUpdateBtn.addActionListener(listener);
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

    public void setLogisticsUpdateSelectBtn(ActionListener listener) {
        logisticsUpdateSelectBtn.addActionListener(listener);
    }

    public void setLogisticsUpdateBtn(ActionListener listener) {
        logisticsUpdateBtn.addActionListener(listener);
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


    public void setProductUpdateId(String text) {
        productUpdateId.setText(text);
    }

    public void setProductUpdateName(String text) {
        productUpdateName.setText(text);
    }

    public void setProductUpdatePrice(String text) {
        productUpdatePrice.setText(text);
    }

    public void setProductUpdateDescription(String text) {
        productUpdateDescription.setText(text);
    }

    public void setProductUpdateR18(boolean selected) {
        productUpdateR18.setSelected(selected);
    }

    public void setCustomerUpdateFirstName(String text) {
        customerUpdateFirstName.setText(text);
    }

    public void setCustomerUpdateLastName(String text) {
        customerUpdateLastName.setText(text);
    }

    public void setCustomerUpdatePhoneNumber(String text) {
        customerUpdatePhoneNumber.setText(text);
    }

    public void setCustomerUpdateEmailAddress(String text) {
        customerUpdateEmailAddress.setText(text);
    }

    public void setCustomerUpdateLotNum(String text) {
        customerUpdateLotNum.setText(text);
    }

    public void setCustomerUpdateStreetName(String text) {
        customerUpdateStreetName.setText(text);
    }

    public void setCustomerUpdateCityName(String text) {
        customerUpdateCityName.setText(text);
    }

    public void setCustomerUpdateZipCode(String text) {
        customerUpdateZipCode.setText(text);
    }

    public void setCustomerUpdateCountry(String text) {
        customerUpdateCountry.setText(text);
    }

    public void setStoreUpdateId(String text) {
        storeUpdateId.setText(text);
    }

    public void setStoreUpdateName(String text) {
        storeUpdateName.setText(text);
    }

    public void setStoreUpdatePhoneNumber(String text) {
        storeUpdatePhoneNumber.setText(text);
    }

    public void setStoreUpdateEmailAddress(String text) {
        storeUpdateEmailAddress.setText(text);
    }

    public void setStoreUpdateLotNum(String text) {
        storeUpdateLotNum.setText(text);
    }

    public void setStoreUpdateStreetName(String text) {
        storeUpdateStreetName.setText(text);
    }

    public void setStoreUpdateCityName(String text) {
        storeUpdateCityName.setText(text);
    }

    public void setStoreUpdateZipCode(String text) {
        storeUpdateZipCode.setText(text);
    }

    public void setStoreUpdateCountry(String text) {
        storeUpdateCountry.setText(text);
    }

    public void setLogisticsUpdateId(String text) {
        logisticsUpdateId.setText(text);
    }

    public void setLogisticsUpdateName(String text) {
        logisticsUpdateName.setText(text);
    }

    public void setLogisticsUpdateLotNum(String text) {
        logisticsUpdateLotNum.setText(text);
    }

    public void setLogisticsUpdateStreetName(String text) {
        logisticsUpdateStreetName.setText(text);
    }

    public void setLogisticsUpdateCityName(String text) {
        logisticsUpdateCityName.setText(text);
    }

    public void setLogisticsUpdateZipCode(String text) {
        logisticsUpdateZipCode.setText(text);
    }

    public void setLogisticsUpdateCountry(String text) {
        logisticsUpdateCountry.setText(text);
    }

    public void setLogisticsUpdateShipmentScope(int index) {
        logisticsUpdateShipmentScope.setSelectedIndex(index);
    }

    public void setProductRelatedRecordsSearchBtn(ActionListener listener) {
        productRelatedRecordsSearchBtn.addActionListener(listener);
    }

    public void setProductRelatedRecordsShowAllBtn(ActionListener listener) {
        productRelatedRecordsShowAllBtn.addActionListener(listener);
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

    public String getStockCount() {
        return stockCount.getText();
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

    public String getProductUpdateId() {
        return productUpdateId.getText();
    }

    public String getProductUpdateName() {
        return productUpdateName.getText();
    }

    public String getProductUpdatePrice() {
        return productUpdatePrice.getText();
    }

    public String getProductUpdateDescription() {
        return productUpdateDescription.getText();
    }

    public boolean getProductUpdateR18() {
        return productUpdateR18.isSelected();
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

    public String getCustomerUpdateId() {
        return customerUpdateId.getText();
    }

    public String getCustomerUpdateFirstName() {
        return customerUpdateFirstName.getText();
    }

    public String getCustomerUpdateLastName() {
        return customerUpdateLastName.getText();
    }

    public String getCustomerUpdatePhoneNumber() {
        return customerUpdatePhoneNumber.getText();
    }

    public String getCustomerUpdateEmailAddress() {
        return customerUpdateEmailAddress.getText();
    }

    public String getCustomerUpdateLotNum() {
        return customerUpdateLotNum.getText();
    }

    public String getCustomerUpdateStreetName() {
        return customerUpdateStreetName.getText();
    }

    public String getCustomerUpdateCityName() {
        return customerUpdateCityName.getText();
    }

    public String getCustomerUpdateZipCode() {
        return customerUpdateZipCode.getText();
    }

    public String getCustomerUpdateCountry() {
        return customerUpdateCountry.getText();
    }

    public String getCustomerStoresSearchField() {
        return customerStoresSearchField.getText();
    }

    public String getCustomerStoresCriteriaComboBox() {
        return customerStoresCriteriaComboBox.getSelectedItem().toString();
    }

    public String getStoreUpdateId() {
        return storeUpdateId.getText();
    }

    public String getStoreUpdateName() {
        return storeUpdateName.getText();
    }

    public String getStoreUpdatePhoneNumber() {
        return storeUpdatePhoneNumber.getText();
    }

    public String getStoreUpdateEmailAddress() {
        return storeUpdateEmailAddress.getText();
    }

    public String getStoreUpdateLotNum() {
        return storeUpdateLotNum.getText();
    }

    public String getStoreUpdateStreetName() {
        return storeUpdateStreetName.getText();
    }

    public String getStoreUpdateCityName() {
        return storeUpdateCityName.getText();
    }

    public String getStoreUpdateZipCode() {
        return storeUpdateZipCode.getText();
    }

    public String getStoreUpdateCountry() {
        return storeUpdateCountry.getText();
    }

    public String getLogisticsUpdateId() {
        return logisticsUpdateId.getText();
    }

    public String getLogisticsUpdateName() {
        return logisticsUpdateName.getText();
    }

    public String getLogisticsUpdateLotNum() {
        return logisticsUpdateLotNum.getText();
    }

    public String getLogisticsUpdateStreetName() {
        return logisticsUpdateStreetName.getText();
    }

    public String getLogisticsUpdateCityName() {
        return logisticsUpdateCityName.getText();
    }

    public String getLogisticsUpdateZipCode() {
        return logisticsUpdateZipCode.getText();
    }

    public String getLogisticsUpdateCountry() {
        return logisticsUpdateCountry.getText();
    }

    public String getLogisticsUpdateShipmentScope() {
        return logisticsUpdateShipmentScope.getSelectedItem().toString();
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

    public String getStoreProductListSearchField() {
        return storeProductListSearchField.getText();
    }

    public String getStoreProductListCriteriaComboBox() {
        return storeProductListCriteriaComboBox.getSelectedItem().toString();
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

    public String getProductRelatedRecordsSearchField() {
        return productRelatedRecordsSearchField.getText();
    }

    public String getProductRelatedRecordsCriteriaComboBox() {
        return productRelatedRecordsCriteriaComboBox.getSelectedItem().toString();
    }

    public void productUpdateEditable(boolean editable) {
        productUpdateId.setEditable(!editable);
        productUpdateSelectBtn.setEnabled(!editable);

        productUpdateName.setEditable(editable);
        productUpdatePrice.setEditable(editable);
        productUpdateDescription.setEditable(editable);
        productUpdateR18.setEnabled(editable);
        productUpdateBtn.setEnabled(editable);
    }

    public void customerUpdateEditable(boolean editable) {
        customerUpdateId.setEditable(!editable);
        customerUpdateSelectBtn.setEnabled(!editable);

        customerUpdateFirstName.setEditable(editable);
        customerUpdateLastName.setEditable(editable);
        customerUpdatePhoneNumber.setEditable(editable);
        customerUpdateEmailAddress.setEditable(editable);
        customerUpdateLotNum.setEditable(editable);
        customerUpdateStreetName.setEditable(editable);
        customerUpdateCityName.setEditable(editable);
        customerUpdateZipCode.setEditable(editable);
        customerUpdateCountry.setEditable(editable);
        customerUpdateBtn.setEnabled(editable);
    }

    public void storeUpdateEditable(boolean editable) {
        storeUpdateId.setEditable(!editable);
        storeUpdateSelectBtn.setEnabled(!editable);

        storeUpdateName.setEditable(editable);
        storeUpdatePhoneNumber.setEditable(editable);
        storeUpdateEmailAddress.setEditable(editable);
        storeUpdateLotNum.setEditable(editable);
        storeUpdateStreetName.setEditable(editable);
        storeUpdateCityName.setEditable(editable);
        storeUpdateZipCode.setEditable(editable);
        storeUpdateCountry.setEditable(editable);
        storeUpdateBtn.setEnabled(editable);
    }

    public void logisticsUpdateEditable(boolean editable) {
        logisticsUpdateId.setEditable(!editable);
        logisticsUpdateSelectBtn.setEnabled(!editable);

        logisticsUpdateName.setEditable(editable);
        logisticsUpdateLotNum.setEditable(editable);
        logisticsUpdateStreetName.setEditable(editable);
        logisticsUpdateCityName.setEditable(editable);
        logisticsUpdateZipCode.setEditable(editable);
        logisticsUpdateCountry.setEditable(editable);
        logisticsUpdateShipmentScope.setEnabled(editable);
        logisticsUpdateBtn.setEnabled(editable);
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

        productUpdateId.setText("");
        productUpdateName.setText("");
        productUpdatePrice.setText("");
        productUpdateDescription.setText("");
        productUpdateR18.setSelected(false);
        productUpdateEditable(false);

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

        customerUpdateId.setText("");
        customerUpdateFirstName.setText("");
        customerUpdateLastName.setText("");
        customerUpdatePhoneNumber.setText("");
        customerUpdateEmailAddress.setText("");
        customerUpdateLotNum.setText("");
        customerUpdateStreetName.setText("");
        customerUpdateCityName.setText("");
        customerUpdateZipCode.setText("");
        customerUpdateCountry.setText("");
        customerUpdateEditable(false);

        customerSearchField.setText("");
        customerStoresSearchField.setText("");

        storeId.setText("");
        storeName.setText("");
        storePhoneNumber.setText("");
        storeEmailAddress.setText("");
        storeLotNum.setText("");
        storeStreetName.setText("");
        storeCityName.setText("");
        storeZipCode.setText("");
        storeCountry.setText("");

        storeUpdateId.setText("");
        storeUpdateName.setText("");
        storeUpdatePhoneNumber.setText("");
        storeUpdateEmailAddress.setText("");
        storeUpdateLotNum.setText("");
        storeUpdateStreetName.setText("");
        storeUpdateCityName.setText("");
        storeUpdateZipCode.setText("");
        storeUpdateCountry.setText("");
        storeUpdateEditable(false);

        storeProductListSearchField.setText("");

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
        logisticsLotNum.setText("");
        logisticsStreetName.setText("");
        logisticsCityName.setText("");
        logisticsZipCode.setText("");
        logisticsCountry.setText("");
        shipmentScope.setSelectedIndex(0);
        logisticsUpdateEditable(false);

        logisticsUpdateId.setText("");
        logisticsUpdateName.setText("");
        logisticsUpdateLotNum.setText("");
        logisticsUpdateStreetName.setText("");
        logisticsUpdateCityName.setText("");
        logisticsUpdateZipCode.setText("");
        logisticsUpdateCountry.setText("");
        logisticsUpdateShipmentScope.setSelectedItem(null);

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
