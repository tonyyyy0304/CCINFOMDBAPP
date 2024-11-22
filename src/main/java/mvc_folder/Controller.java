package main.java.mvc_folder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.sql.Connection;
import java.sql.Date;
// import java.sql.DriverManager;
// import java.sql.PreparedStatement;
import java.sql.SQLException;
// import java.sql.ResultSet;

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
        this.view.setProductShowAllBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {             
                view.refreshProductRecords();
            }
        });
        this.view.setProductSearchBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = view.getProductSearchField();
                String criteria = view.getProductSearchCriteriaComboBox();
                if(query.isEmpty()){
                    view.showMessage("Please enter a search query.");
                    return;
                }
                try {
                    // Call the model to search for products
                    if(criteria.equals("Product ID")){
                        view.refreshProductRecords(model.searchProductRecordsById(query));
                    } else if(criteria.equals("Product Name")){
                        view.refreshProductRecords(model.searchProductRecordsByName(query));
                    }
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

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
                try {
                    int productId = Integer.parseInt(view.getProductId());
                    boolean success = model.removeProduct(productId);
                    if (success) {
                        view.showSuccess("Product removed successfully!");
                        view.clearFields();
                        view.refreshProductRecords();
                    } else {
                        view.showError("Failed to remove the product. Product ID may not exist.");
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Product ID must be a valid number.");
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setProductUpdateSelectBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the product ID is empty
                if (view.getProductUpdateId().isEmpty()) {
                    view.showError("Please enter a Product ID.");
                    return;
                }

                try {
                    int productId = Integer.parseInt(view.getProductUpdateId());
                    if (!model.productExists(productId)) {
                        view.showError("Product with ID " + productId + " does not exist.");
                    }

                    view.productUpdateEditable(true); // Enable the fields

                    // Retrieve the product data
                    String[] productData = model.getProductData(productId);
                    if (productData == null) {
                        view.showError("Failed to retrieve product data.");
                        return;
                    }

                    // Set the product data in the view
                    view.setProductUpdateName(productData[0]);
                    view.setProductUpdatePrice(productData[1]);
                    view.setProductUpdateDescription(productData[2]);
                    view.setProductUpdateR18(productData[3].equalsIgnoreCase("T"));
                } catch (NumberFormatException ex) {
                    view.showError("Product ID must be a valid number.");
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setProductUpdateBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String product_id = view.getProductUpdateId();
                String product_name = view.getProductUpdateName();
                String product_price = view.getProductUpdatePrice();
                String product_desc = view.getProductUpdateDescription();
                boolean product_r18 = view.getProductUpdateR18();

                if (product_price.isEmpty() || product_name.isEmpty() || product_desc.isEmpty())
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
                    int productId = Integer.parseInt(product_id);
                    if (productId < 0)
                    {
                        view.showMessage("Product ID cannot be negative.");
                        return;
                    }

                    String r18 = product_r18 ? "T" : "F";

                    // Check if product exists
                    if (!model.productExists(productId))
                    {
                        view.showError("Product ID does not exist.");
                        return;
                    }

                    // Update product
                    boolean success = model.updateProduct(productId, product_name, product_desc, r18, price);
                    if (success)
                    {
                        view.showSuccess("Product updated successfully!");
                        view.clearFields();
                        view.refreshProductRecords();
                    } else {
                        view.showError("Failed to update the product.");
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Product ID must be a valid number.");
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setStoreShowAllBtn(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                view.refreshStoreRecordsPnl();
            }
        });

        this.view.setStoreSearchBtn(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String query = view.getStoreSearchField();
                String criteria = view.getStoreCriteriaComboBox();

                //if criteria is store id, check if the input is a number
                if(criteria.equals("Store ID")){
                    try{
                        Integer.parseInt(query);
                    } catch (NumberFormatException ex){
                        view.showError("Store ID must be a valid number.");
                        return;
                    }
                }

                if(query.isEmpty()){
                    view.showMessage("Please enter a search query.");
                    return;
                }
                try {
                    // Call the model to search for stores
                    if(criteria.equals("Store ID")){
                
                        view.refreshStoreRecordsPnl(model.searchStoreRecordsById(query));
                    } else if(criteria.equals("Store Name")){
                        view.refreshStoreRecordsPnl(model.searchStoreRecordsByName(query));
                    }
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
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

        // TODO: Implement the store update selection button

        // TODO: Implement the store update button

        this.view.setCustomerSearchBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = view.getCustomerSearchField();
                String criteria = view.getCustomerCriteriaComboBox();

                if (query.isEmpty()) {
                    view.showMessage("Please enter a search query.");
                    return;
                }
                try {
                    // Call the model to search for customers
                    if (criteria.equals("Customer ID")) {
                        view.refreshCustomerRecords(model.searchCustomerRecordsById(query));
                    } else if (criteria.equals("Customer Name")) {
                        view.refreshCustomerRecords(model.searchCustomerRecordsByName(query));
                    }
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setCustomerShowAllBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.refreshCustomerRecords();
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

        this.view.setCustomerUpdateSelectBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Check if the customer ID is empty
                if (view.getCustomerUpdateId().isEmpty()) {
                    view.showError("Please enter a Customer ID.");
                    return;
                }

                try {
                    int customerId = Integer.parseInt(view.getCustomerUpdateId());
                    if (!model.customerExists(customerId)) {
                        view.showError("Customer with ID " + customerId + " does not exist.");
                        return;
                    }

                    view.customerUpdateEditable(true); // Enable the fields

                    // Retrieve the customer data
                    String[] customerData = model.getCustomerData(customerId);
                    if (customerData == null) {
                        view.showError("Failed to retrieve customer data.");
                        return;
                    }

                    // Set the customer data in the view
                    view.setCustomerUpdateFirstName(customerData[0]);
                    view.setCustomerUpdateLastName(customerData[1]);
                    view.setCustomerUpdatePhoneNumber(customerData[2]);
                    view.setCustomerUpdateEmailAddress(customerData[3]);
                    view.setCustomerUpdateLotNum(customerData[4]);
                    view.setCustomerUpdateStreetName(customerData[5]);
                    view.setCustomerUpdateCityName(customerData[6]);
                    view.setCustomerUpdateZipCode(customerData[7]);
                    view.setCustomerUpdateCountry(customerData[8]);
                    
                } catch (NumberFormatException ex) {
                    view.showError("Customer ID must be a valid number.");
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setCustomerUpdateBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String customer_first_name = view.getCustomerUpdateFirstName();
                String customer_last_name = view.getCustomerUpdateLastName();
                String customer_contact_num = view.getCustomerUpdatePhoneNumber();
                String customer_email_add = view.getCustomerUpdateEmailAddress();
                String customer_lot_num = view.getCustomerUpdateLotNum();
                String customer_street_name = view.getCustomerUpdateStreetName();
                String customer_city_name = view.getCustomerUpdateCityName();
                String customer_zipcode = view.getCustomerUpdateZipCode();
                String customer_country_name = view.getCustomerUpdateCountry();

                if (customer_first_name.isEmpty() || customer_last_name.isEmpty() ||
                    customer_contact_num.isEmpty() || customer_email_add.isEmpty() ||
                    customer_lot_num.isEmpty() || customer_street_name.isEmpty() ||
                    customer_city_name.isEmpty() || customer_zipcode.isEmpty() ||
                    customer_country_name.isEmpty()) 
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

                try {
                    // Update customer
                    boolean success = model.updateCustomer(
                            Integer.parseInt(view.getCustomerUpdateId()), customer_first_name, 
                            customer_last_name, customer_contactId, customer_locationId
                        );
                    if (success) {
                        view.showSuccess("Customer updated successfully!");
                        view.clearFields();
                        view.refreshCustomerRecords();
                        view.refreshCustomerStatsPnl();
                        view.refreshAffinityPnl();
                    } else {
                        view.showError("Failed to update the customer.");
                    }
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setStoreRemoveBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int storeId = Integer.parseInt(view.getStoreId());
                    boolean success = model.removeStore(storeId);
                    if (success) {
                        view.showSuccess("Store removed successfully!");
                        view.clearFields();
                        view.refreshStoreRecordsPnl();
                    } else {
                        view.showError("Failed to remove the store. Store ID may not exist.");
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
                        view.refreshCustomerStatsPnl(); // Refresh the customer stats
                        view.refreshPaymentReportsPnl(); // Refresh the payment reports
                        view.refreshAffinityPnl(); // Refresh the affinity report
                    } else {
                        view.showError("Failed to place order. Please check the stock availability or if you are of age to order this product.");
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
                    int orderId = Integer.parseInt(view.getPaymentOrderId());
                    

                    // Call the model to process the payment
                    boolean success = model.payForOrder(orderId);
                    if (success) {
                        view.showSuccess("Payment processed successfully!");
                        view.clearFields(); // Clear fields after success
                        view.refreshPaymentReportsPnl(); // Refresh the payment reports
                        view.refreshAffinityPnl(); // Refresh the affinity report
                    } else {
                        view.showError("Payment failed. Please check if the Order ID are valid or is pending for payment.");
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Please enter valid numbers for Order ID.");
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setPaymentCancelBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Retrieve and validate inputs
                    int orderId = Integer.parseInt(view.getPaymentOrderId());
                    

                    // Call the model to process the payment
                    boolean success = model.cancelPayForOrder(orderId);
                    if (success) {
                        view.showSuccess("Order cancelled successfully!");
                        view.clearFields(); // Clear fields after success
                        view.refreshPaymentReportsPnl(); // Refresh the payment reports
                    } else {
                        view.showError("Cancellation failed. Please check if the Order ID are valid or is pending for payment.");
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Please enter valid numbers for Order ID.");
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

                    if(!model.logisticsCompanyExists(logisticsCompanyId)){
                        view.showError("Logistics Company with ID " + logisticsCompanyId + " does not exist.");
                        return; // Exit early if logistics company does not exist
                    }

                    if(!model.orderExists(orderId)){
                        view.showError("Order with ID " + orderId + " does not exist.");
                        return; // Exit early if order does not exist
                    }

                    if(model.orderShipped(orderId)){
                        view.showError("Order with ID " + orderId + " has already been shipped.");
                        return; // Exit early if order has already been shipped
                    }

                    if(!model.orderPaid(orderId)){
                        view.showError("Order with ID " + orderId + " has not been paid for.");
                        return; // Exit early if order has not been paid for
                    }

                    if(!model.matchLogiscticsScopeToOrderShipping(orderId, logisticsCompanyId)){
                        view.showError("Logistics Company with ID " + logisticsCompanyId + " does not match the order's shipping scope.");
                        return; // Exit early if logistics company does not match the order's shipping scope
                    }

                    // Call the model to process the shipping
                    boolean success = model.shipOrder(orderId, logisticsCompanyId);
                    if (success) {
                        view.showSuccess("Order shipped successfully!");
                        view.clearFields(); // Clear fields after success
                    } 
                    else {
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

        this.view.setLogisticsShowAllBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.refreshLogisticsRecordPnl();
            }
        });

        this.view.setLogisticsSearchBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String query = view.getLogisticsSearchField();
                String criteria = view.getLogisticsCriteriaComboBox();
                //check if the input is a number
                if (criteria.equals("Company ID")) {
                    try {
                        Integer.parseInt(query);
                    } catch (NumberFormatException ex) {
                        view.showError("Company ID must be a valid number.");
                        return;
                    }
                }
                if (query.isEmpty()) {
                    view.showMessage("Please enter a search query.");
                    return;
                }
                try {
                    // Call the model to search for logistics companies
                    if (criteria.equals("Company ID")) {
                        view.refreshLogisticsRecordPnl(model.searchLogisticsRecordsById(query));
                    } else if (criteria.equals("Company Name")) {
                        view.refreshLogisticsRecordPnl(model.searchLogisticsRecordsByName(query));
                    }
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

        this.view.setLogisticsRemoveBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Retrieve the logistics company ID from the view
                    int logisticsCompanyId = Integer.parseInt(view.getLogisticsCompanyID());

                    // Call the model to remove the logistics company
                    boolean success = model.removeLogisticsCompany(logisticsCompanyId);
                    if (success) {
                        view.showSuccess("Logistics company removed successfully!");
                        view.clearFields(); // Clear fields after success
                        view.refreshLogisticsRecordPnl(); // Refresh the logistics records
                    } else {
                        view.showError("Failed to remove the logistics company. Logistics Company ID may not exist.");
                    }
                } catch (NumberFormatException ex) {
                    view.showError("Logistics Company ID must be a valid number.");
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        // TODO: Implement the logistics update selection button

        // TODO: Implement the logistics update button

        this.view.setProductSalesCategory(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.refreshProductSalesPnl();
            }
        });

        this.view.setCustomerStatsSearchBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start_yearS = view.getCustomerStatsStartYear();
                String end_yearS = view.getCustomerStatsEndYear();

                int start_year = 0;
                int end_year = 0;

                if (start_yearS.isEmpty() || end_yearS.isEmpty()) {
                    view.showError("Please fill in all required fields.");
                    view.clearFields();
                    return;
                }

                try {
                    start_year = Integer.parseInt(start_yearS);
                    end_year = Integer.parseInt(end_yearS);
                } catch (Exception ex) {
                    view.showError("Year must be a valid number.");
                    view.clearFields();
                    return;
                }

                if (start_year > end_year) {
                    view.showError("Start year cannot be greater than end year.");
                    view.clearFields();
                    return;
                }

                try {
                    view.refreshCustomerStatsPnl(Model.getCustomerStats(start_year, end_year));
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setCustomerStatsShowAllBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.refreshCustomerStatsPnl();
            }
        });

        this.view.setProductSalesReportSearchBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start_yearS = view.getProductSalesStartYear();
                String end_yearS = view.getProductSalesEndYear();

                int start_year = 0;
                int end_year = 0;

                if (start_yearS.isEmpty() || end_yearS.isEmpty()) {
                    view.showError("Please fill in all required fields.");
                    return;
                }

                try {
                    start_year = Integer.parseInt(start_yearS);
                    end_year = Integer.parseInt(end_yearS);
                } catch (Exception ex) {
                    view.showError("Year must be a valid number.");
                    view.clearFields();
                    return;
                }

                if (start_year > end_year) {
                    view.showError("Start year cannot be greater than end year.");
                    view.clearFields();
                    return;
                }

                try {
                    view.refreshProductSalesPnl(Model.getProductSales(start_year, end_year));
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });
        
        this.view.setProductSalesReportShowAllBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.refreshProductSalesPnl();
            }
        });

        this.view.setPaymentReportSearchBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start_yearS = view.getPaymentReportStartYear();
                String end_yearS = view.getPaymentReportEndYear();

                int start_year = 0;
                int end_year = 0;

                if (start_yearS.isEmpty() || end_yearS.isEmpty()) {
                    view.showError("Please fill in all required fields.");
                    view.clearFields();
                    return;
                }

                try {
                    start_year = Integer.parseInt(start_yearS);
                    end_year = Integer.parseInt(end_yearS);
                } catch (Exception ex) {
                    view.showError("Year must be a valid number.");
                    view.clearFields();
                    return;
                }

                if (start_year > end_year) {
                    view.showError("Start year cannot be greater than end year.");
                    view.clearFields();
                    return;
                }

                try {
                    view.refreshPaymentReportsPnl(Model.getPaymentReports(start_year, end_year));
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });


        this.view.setPaymentReportShowAllBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.refreshPaymentReportsPnl();
            }
        });

        this.view.setAffinitySearchBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String start_yearS = view.getAffinityStartYear();
                String end_yearS = view.getAffinityEndYear();

                int start_year = 0;
                int end_year = 0;

                if (start_yearS.isEmpty() || end_yearS.isEmpty()) {
                    view.showError("Please fill in all required fields.");
                    view.clearFields();
                    return;
                }

                try {
                    start_year = Integer.parseInt(start_yearS);
                    end_year = Integer.parseInt(end_yearS);
                } catch (Exception ex) {
                    view.showError("Year must be a valid number.");
                    view.clearFields();
                    return;
                }

                if (start_year > end_year) {
                    view.showError("Start year cannot be greater than end year.");
                    view.clearFields();
                    return;
                }

                try {
                    view.refreshAffinityPnl(Model.getAffinity(start_year, end_year));
                } catch (SQLException ex) {
                    view.showError("Database error: " + ex.getMessage());
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
            }
        });

        this.view.setAffinityShowAllBtn(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.refreshAffinityPnl();
            }
        });


    }
}
