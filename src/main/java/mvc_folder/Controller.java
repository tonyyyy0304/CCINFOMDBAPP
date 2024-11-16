package main.java.mvc_folder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
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
                    boolean success = model.addStore(store_name, store_contactId, store_locationId);
                    if (success) {
                        view.showSuccess("Store added successfully!");
                    } else {
                        view.showError("Failed to add the store.");
                    }
                } catch (Exception ex) {
                    view.showError("An unexpected error occurred: " + ex.getMessage());
                }
        
            }
        });

        
    }
}
