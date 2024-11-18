package main.java.mvc_folder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class Controller
{
    private Model model;
    private View view;

    public Controller(Model model, View view)
    {
        this.model = model;
        this.view = view;

        listeners();
    }

    public void listeners(){
        this.view.setProductAddBtn(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String product_name = view.getProductName();
                String product_price = view.getProductPrice();
                String product_desc = view.getDescription();
                String product_store_id = view.getProductStoreId();
                Integer product_stock_count = view.getStockCount();
                String product_category = view.getSelectedProductCategory();
                boolean product_r18 = view.isProductR18();

                if (product_price.isEmpty() || product_name.isEmpty() || product_store_id.isEmpty() ||
                        product_stock_count <= 0 || product_category.isEmpty())
                {
                    view.showMessage("Please fill in all required fields.");
                    return;
                }

                double price = 0;
                try
                {
                    price = Double.parseDouble(product_price);
                    if (price < 0)
                    {
                        view.showMessage("Price cannot be negative.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Price must be a valid number.");
                    return;
                }

                try
                {
                    int storeId = Integer.parseInt(product_store_id);
                    if (storeId < 0)
                    {
                        view.showMessage("Store ID cannot be negative.");
                        return;
                    }

                    String r18 = product_r18 ? "T" : "F";

                    // Check if store exists
                    if (!model.storeExists(storeId))
                    {
                        view.showError("Store ID does not exist.");
                        return;
                    }

                    // Add new product
                    boolean success = model.addProduct(product_name, product_desc,
                            storeId, product_stock_count,
                            product_category, r18, price);

                    if (success)
                    {
                        view.showSuccess("Product added successfully!");
                        view.clearFields();
                        view.refreshProductRecords();
                    } else {
                        view.showError("Failed to add the product.");
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Store ID must be a valid number.");
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setProductRemoveBtn(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("test");
            }
        });

        this.view.setStoreAddBtn(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String store_name = view.getStoreName();
                String store_contact_num = view.getStorePhoneNumber();
                String store_email_add = view.getStoreEmailAddress();
                String store_lot_num = view.getStoreLotNum();
                String store_street_name = view.getStoreStreetName();
                String store_city_name = view.getStoreCityName();
                String store_country_name = view.getStoreCountry()  ;
                String store_zipcode = view.getStoreZipCode();

                if (store_name.isEmpty() || store_contact_num.isEmpty() ||
                        store_email_add.isEmpty() || store_lot_num.isEmpty() ||
                        store_street_name.isEmpty() || store_city_name.isEmpty() ||
                        store_country_name.isEmpty())
                {
                    view.showMessage("Please fill in all required fields.");
                    return;
                }

                try{
                    int lot_num = Integer.parseInt(store_lot_num);
                    if (lot_num < 0) {
                        view.showMessage("Lot number cannot be negative.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Lot number must be a valid number.");
                    return;
                }
                int phone_num = 0;
                try{
                    phone_num = Integer.parseInt(store_contact_num);
                    if (phone_num < 0) {
                        view.showMessage("Phone number cannot be negative.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Phone number must be a valid number.");
                    return;
                }
                try{
                    int zipcode = Integer.parseInt(store_zipcode);
                    if (zipcode < 0) {
                        view.showMessage("Zipcode cannot be negative.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Zipcode must be a valid number.");
                    return;
                }

                try {
                    // Add new location
                    if(!model.locationExists(Integer.parseInt(store_lot_num),
                            store_street_name, store_city_name,
                            store_country_name, Integer.parseInt(store_zipcode)))
                    {
                        model.addLocationId(Integer.parseInt(store_lot_num),
                                store_street_name, store_city_name,
                                store_country_name, Integer.parseInt(store_zipcode));
                    }
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }

                int store_locationId = 0;
                try {
                    store_locationId = model.getLocationId(Integer.parseInt(store_lot_num),
                            store_street_name,
                            store_city_name,
                            store_country_name,
                            Integer.parseInt(store_zipcode));

                    System.out.println("Retrieved Location   ID: " + store_locationId);
                } catch(SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }

                try{
                    if(!model.contactExists(phone_num, store_email_add)){
                        model.addContactId(phone_num, store_email_add);
                        System.out.println("added");
                    }
                }catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }

                int store_contactId = 0;
                try{
                    store_contactId = model.getContactId(phone_num, store_email_add);
                    System.out.println("Retrieved Contact ID: " + store_contactId);
                } catch(SQLException ex){
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }

                try {
                    // Add new store
                    if(model.storeExists(store_name, store_contactId, store_locationId)){
                        view.showError("Store already exists.");
                        return;
                    }
                    boolean success = model.addStore(store_name, store_contactId, store_locationId);
                    if (success) {
                        view.showSuccess("Store added successfully!");
                        view.clearFields();
                        view.refreshStoreRecordsPnl();
                    } else {
                        view.showError("Failed to add the store.");
                    }
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }

            }
        });

        this.view.setCustomerAddBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customer_first_name = view.getCustomerFirstName();
                String customer_last_name = view.getCustomerLastName();
                String customer_contact_num = view.getCustomerPhoneNumber();
                String customer_email_add = view.getCustomerEmailAddress();
                String customer_lot_num = view.getCustomerLotNum();
                String customer_street_name = view.getCustomerStreetName();
                String customer_city_name = view.getCustomerCityName();
                String customer_zipcode = view.getCustomerZipCode();
                String customer_country_name = view.getCustomerCountry();
                Date given_customer_birthdate = view.getCustomerBirthdate();

                if (customer_first_name.isEmpty() || customer_last_name.isEmpty() ||
                        customer_contact_num.isEmpty() || customer_email_add.isEmpty() ||
                        customer_lot_num.isEmpty() || customer_street_name.isEmpty() ||
                        customer_city_name.isEmpty() || customer_country_name.isEmpty())
                {
                    view.showMessage("Please fill in all required fields.");
                    return;
                }

                try {
                    int lot_num = Integer.parseInt(customer_lot_num);
                    if (lot_num < 0) {
                        view.showMessage("Lot number cannot be negative.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Lot number must be a valid number.");
                    return;
                }
                long phone_num = 0;
                try {
                    phone_num = Long.parseLong(customer_contact_num);
                    if (phone_num < 0) {
                        view.showMessage("Phone number cannot be negative.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Phone number must be a valid number.");
                    return;
                }
                try {
                    int zipcode = Integer.parseInt(customer_zipcode);
                    if (zipcode < 0) {
                        view.showMessage("Zipcode cannot be negative.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Zipcode must be a valid number.");
                    return;
                }

                try {
                    // Add new location
                    if (!model.locationExists(Integer.parseInt(customer_lot_num),
                            customer_street_name, customer_city_name,
                            customer_country_name, Integer.parseInt(customer_zipcode)))
                    {
                        model.addLocationId(Integer.parseInt(customer_lot_num),
                                customer_street_name, customer_city_name,
                                customer_country_name, Integer.parseInt(customer_zipcode));
                    }
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }

                int customer_locationId = 0;
                try {
                    customer_locationId = model.getLocationId(Integer.parseInt(customer_lot_num),
                            customer_street_name,
                            customer_city_name,
                            customer_country_name,
                            Integer.parseInt(customer_zipcode));

                    System.out.println("Retrieved Location ID: " + customer_locationId);
                } catch(SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }

                try {
                    if (!model.contactExists(phone_num, customer_email_add)) {
                        model.addContactId(phone_num, customer_email_add);
                        System.out.println("added");
                    }
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }

                int customer_contactId = 0;
                try {
                    customer_contactId = model.getContactId(phone_num, customer_email_add);
                    System.out.println("Retrieved Contact ID: " + customer_contactId);
                } catch(SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }

                if (given_customer_birthdate == null) {
                    view.showMessage("Please enter a valid birthdate.");
                    return;
                }

                Date currentDate = new Date(System.currentTimeMillis());
                if (given_customer_birthdate.after(currentDate)) {
                    view.showMessage("Birthdate cannot be in the future.");
                    return;
                }

                Date customer_birthdate = given_customer_birthdate;

                try {
                    // Add new customer
                    boolean success = model.addCustomer(customer_first_name, customer_last_name,
                            customer_contactId, customer_locationId, customer_birthdate);
                    if (success) {
                        view.showSuccess("Customer added successfully!");
                        view.clearFields();
                        view.refreshCustomerRecords();
                    } else {
                        view.showError("Failed to add the customer.");
                    }
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setCustomerRemoveBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int customerId = Integer.parseInt(view.getCustomerId());
                    boolean success = model.removeCustomer(customerId);
                    if (success) {
                        view.showSuccess("Customer removed successfully!");
                        view.clearFields();
                        view.refreshCustomerRecords();
                    } else {
                        view.showError("Failed to remove the customer. Customer ID may not exist.");
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Customer ID must be a valid number.");
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setAdjustStockBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Retrieve the inputs from the view
                    String productIDInput = view.getAdjustStockProductId(); // Method to get the product ID
                    String amountInput = view.getAdjustStockQuantity(); // Method to get the stock amount
                    String action = view.getAdjustStockType(); // Method to get the action

                    // Validate and parse the product ID
                    int productID;
                    try {
                        productID = Integer.parseInt(productIDInput);
                    } catch (NumberFormatException ex) {
                        view.showError("Product ID must be a valid number.");
                        return; // Exit early if product ID is invalid
                    }

                    // Check if the product exists
                    if (!model.productExists(productID)) {
                        view.showError("Product with ID " + productID + " does not exist.");
                        return; // Exit early if product does not exist
                    }

                    // Validate and parse the amount
                    int amount;
                    try {
                        amount = Integer.parseInt(amountInput);
                    } catch (NumberFormatException ex) {
                        view.showError("Amount must be a valid number.");
                        return; // Exit early if amount is invalid
                    }

                    // Get the current stock for the product
                    int currentStock = model.getCurrentStock(productID);

                    // Check if the amount to remove is valid
                    if (amount > currentStock && action.equals("Remove")) {
                        view.showError("Cannot remove more stock than available. Current stock: " + currentStock);
                        return; // Exit early if attempting to remove too much stock
                    }
                    
                    if (action.equals("Remove")) {
                        amount -= currentStock; // Change the amount to negative if removing stock
                    }
                    else{
                        amount += currentStock; // Change the amount to positive if adding stock
                    }
                    // Adjust the stock in the model
                    boolean success = model.adjustStock(productID, amount);
                    if (success) {
                        view.showSuccess("Amount changed successfully!");
                        view.clearFields();
                        view.refreshProductRecords();
                    } else {
                        view.showError("Failed to adjust stock.");
                    }
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setPlaceOrderBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Retrieve and validate inputs
                    int customerId = Integer.parseInt(view.getOrderCustomerId());
                    int productId = Integer.parseInt(view.getOrderProductId());
                    int quantity = Integer.parseInt(view.getOrderQuantity());
                    int lotNum = Integer.parseInt(view.getOrderLotNum());
                    String streetName = view.getOrderStreetName();
                    String cityName = view.getOrderCityName();
                    int zipCode = Integer.parseInt(view.getOrderZipCode());
                    String countryName = view.getOrderCountry();
                    String paymentMethod = view.getOrderPaymentMethod();

                    // Check if customer exists
                    if (!model.customerExists(customerId)) {
                        view.showError("Customer with ID " + customerId + " does not exist.");
                        return; // Exit early if customer does not exist
                    }

                    // Check if product exists
                    if (!model.productExists(productId)) {
                        view.showError("Product with ID " + productId + " does not exist.");
                        return; // Exit early if product does not exist
                    }

                    // Call the model to place the order
                    boolean success = model.placeOrder(customerId, productId, quantity,
                                lotNum, streetName, cityName, zipCode, countryName, paymentMethod);
                    if (success) {
                        view.showSuccess("Order placed successfully!");
                        view.clearFields(); // Clear fields after success
                        view.refreshStoresCustomerBoughtFrom(); // Refresh the stores the customer bought from
                        view.refreshProductRecords(); // Refresh the product records
                    } else {
                        view.showError("Failed to place order. Please check the stock availability.");
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Please enter valid numbers for Customer ID, Product ID, Lot Number, Zip Code, and Quantity.");
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setPaymentBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Retrieve and validate inputs
                    int customerId = Integer.parseInt(view.getPaymentCustomerId());
                    int orderId = Integer.parseInt(view.getPaymentOrderId());
                    double paymentAmount = Double.parseDouble(view.getPaymentAmount());

                    // Call the model to process the payment
                    boolean success = model.payForOrder(customerId, orderId, paymentAmount);
                    if (success) {
                        view.showSuccess("Payment processed successfully!");
                        view.clearFields(); // Clear fields after success
                    } else {
                        view.showError("Payment failed. Please check if the Customer ID and Order ID are valid and if the payment amount is sufficient.");
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Please enter valid numbers for Customer ID, Order ID, and Payment Amount.");
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setShipOrderBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Retrieve and validate inputs
                    int orderId = Integer.parseInt(view.getShipOrderId());
                    int logisticsCompanyId = Integer.parseInt(view.getShipLogisticsId());

                    // Call the model to process the shipping
                    boolean success = model.shipOrder(orderId, logisticsCompanyId);
                    if (success) {
                        view.showSuccess("Order shipped successfully!");
                        view.clearFields(); // Clear fields after success
                    } else {
                        view.showError("Shipping failed. Please check if the Order ID and Logistics Company ID are valid.");
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Please enter valid numbers for Order ID and Logistics Company ID.");
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setLogisticsAddBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve input fields from the view
                String logistics_company_name = view.getLogisticsCompanyName();
                String logistics_street_name = view.getLogisticsStreetName();
                String logistics_city_name = view.getLogisticsCityName();
                String logistics_country_name = view.getLogisticsCountry();
                String logistics_zipcode = view.getLogisticsZipCode();
                String logistics_lot_num = view.getLogisticsLotNum();
                String logistics_scope = view.getLogisticsScope();


                // Validate input fields
                if (logistics_company_name.isEmpty() || logistics_street_name.isEmpty() ||
                        logistics_city_name.isEmpty() || logistics_country_name.isEmpty() ||
                        logistics_zipcode.isEmpty() || logistics_lot_num.isEmpty()){
                    view.showMessage("Please fill in all required fields.");
                    return;
                }

                // You can add more validation here if needed (e.g., check for specific formats)
                int lot_num = 0;
                try{
                    lot_num = Integer.parseInt(logistics_lot_num);
                    if (lot_num < 0) {
                        view.showMessage("Lot number cannot be negative.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Lot number must be a valid number.");
                    return;
                }

                int zipcode = 0;
                try{
                    zipcode = Integer.parseInt(logistics_zipcode);
                    if (zipcode < 0) {
                        view.showMessage("Zipcode cannot be negative.");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Zipcode must be a valid number.");
                    return;
                }
                try{
                    // Add new location
                    if(!model.locationExists(Integer.parseInt(logistics_lot_num),
                            logistics_street_name, logistics_city_name,
                            logistics_country_name, Integer.parseInt(logistics_zipcode)))
                    {
                        model.addLocationId(Integer.parseInt(logistics_lot_num),
                                logistics_street_name, logistics_city_name,
                                logistics_country_name, Integer.parseInt(logistics_zipcode));
                    }
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
                int location_id = 0;
                try{
                    location_id = model.getLocationId(Integer.parseInt(logistics_lot_num),
                            logistics_street_name, logistics_city_name,
                            logistics_country_name, Integer.parseInt(logistics_zipcode));
                    System.out.println("Retrieved Location ID: " + location_id);
                } catch(SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }

                try {
                    // Call the model to add the logistics company
                    boolean success = model.addLogisticsCompany(logistics_company_name, location_id, logistics_scope);

                    if (success) {
                        view.showSuccess("Logistics company added successfully!");
                        view.clearFields(); // Clear fields after success
                        view.refreshLogisticsRecordPnl();
                    } else {
                        view.showError("Failed to add the logistics company.");
                    }
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

    }
}
