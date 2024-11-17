package main.java.mvc_folder;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

public class Model 
{   
    private static final String dbUrl = "jdbc:mysql://localhost:3306/ecommerce_db";
    private static final String userName = "root";
    private static final String password = "password";


    public Model() {
    }

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/ecommerce_db", "root", "password");
    }


    public boolean addProduct(String product_name, String description, int store_id, int stock_count, String category, 
                                String r18_flag, double price) throws SQLException 
    {
        String sql = "INSERT INTO products (product_name, description, store_id, stock_count, category, R18, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, product_name);
            stmt.setString(2, description);
            stmt.setInt(3, store_id);
            stmt.setInt(4, stock_count);
            stmt.setString(5, category);
            stmt.setString(6, r18_flag);
            stmt.setDouble(7, price);

            return stmt.executeUpdate() > 0; //true if insertion was successful
        }
    }

    public boolean productExists(int productId) throws SQLException 
    {
        String sql = "SELECT product_id FROM products WHERE product_id = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); 
            }
        }
    }

    public void addLocationId(int lot_num, String street, String city, String country, int zipcode) 
    {
        try {
            if (!locationExists(lot_num, street, city, country, zipcode)) {
                String sql = "INSERT INTO locations (lot_number, street_name, city_name, country_name, zip_code) VALUES (?, ?, ?, ?, ?)";
                
                try (Connection conn = getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, lot_num);
                    stmt.setString(2, street);
                    stmt.setString(3, city);
                    stmt.setString(4, country);
                    stmt.setInt(5, zipcode);
    
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("SQLException: " + e.getMessage());
                    System.out.println("SQLState: " + e.getSQLState());
                    System.out.println("VendorError: " + e.getErrorCode());
                }
            } else {
                System.out.println("Location already exists.");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    public boolean locationExists(int lot_num, String street, String city, String country, int zip_code) throws SQLException
    {
        String sql = "SELECT location_id FROM locations WHERE lot_number = ? AND street_name = ? AND city_name = ? AND country_name = ? AND zip_code = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lot_num);
            stmt.setString(2, street);
            stmt.setString(3, city);
            stmt.setString(4, country);
            stmt.setInt(5, zip_code);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public int getLocationId(int lot_num, String street, String city, String country, int zip_code) throws SQLException 
    {
        String sql = "SELECT location_id FROM locations WHERE lot_number = ? AND street_name = ? AND city_name = ? AND country_name = ? AND zip_code = ?";
    
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, lot_num);
            stmt.setString(2, street);
            stmt.setString(3, city);
            stmt.setString(4, country);
            stmt.setInt(5, zip_code);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("location_id");
                } else {
                    throw new SQLException("Location not found");
                }
            }
        }
    }

    public void addContactId(long phone_num, String email) {
        try {
            if (!contactExists(phone_num, email)) {
                String sql = "INSERT INTO contact_information (phone_number, email_address) VALUES (?, ?)";
                
                try (Connection conn = getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setLong(1, phone_num);
                    stmt.setString(2, email);
    
                    stmt.executeUpdate();
                } catch (SQLException e) {
                    System.out.println("SQLException: " + e.getMessage());
                    System.out.println("SQLState: " + e.getSQLState());
                    System.out.println("VendorError: " + e.getErrorCode());
                }
            } else {
                System.out.println("Contact already exists.");
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    public boolean contactExists(long phone_num, String email) throws SQLException {
        String sql = "SELECT contact_id FROM contact_information WHERE phone_number = ? AND email_address = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, phone_num);
            stmt.setString(2, email);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } 
    }

    public int getContactId(long phone_num, String email) throws SQLException {
        String sql = "SELECT contact_id FROM contact_information WHERE phone_number = ? AND email_address = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, phone_num);
            stmt.setString(2, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("contact_id");
                } else {
                    throw new SQLException("contact not found");
                }
            }
        }
    }

    public boolean addStore(String store_name, int contact_id, int location_id) {

        String sql = "INSERT INTO store (store_name, contact_id, location_id) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, store_name);
            stmt.setInt(2, contact_id);
            stmt.setInt(3, location_id);

            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return false;
        }
    }

    public boolean storeExists(String store_name, int contact_id, int location_id) throws SQLException {
        String sql = "SELECT store_id FROM store WHERE store_name = ? AND contact_id = ? AND location_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, store_name);
            stmt.setInt(2, contact_id);
            stmt.setInt(3, location_id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean storeExists(int store_id) throws SQLException {
        String sql = "SELECT store_id FROM store WHERE store_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, store_id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean addCustomer(String firstName, String lastName, int contactId, int locationId, Date dateOfBirth) {
        String sql = "INSERT INTO customers (first_name, last_name, contact_id, location_id, birthdate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setInt(3, contactId);
            stmt.setInt(4, locationId);
            stmt.setDate(5, dateOfBirth);

            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return false;
        }
    }

    public boolean removeCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean customerExists(int customerId) throws SQLException {
        String sql = "SELECT customer_id FROM customer WHERE customer_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }


    // ----------------- Queries -----------------

    public static Object[][] getCustomerRecords() throws SQLException {
        String sql =
            "SELECT c.customer_id, c.first_name, c.last_name, " +
            "ci.phone_number, ci.email_address, c.birthdate, " +
            "CONCAT('', l.lot_number, ' ', l.street_name, ' ', l.city_name, ' ', l.zip_code, ' ', l.country_name) AS address, " +
            "c.customer_status, c.registration_date " +
            "FROM customers c " +
            "JOIN contact_information ci ON c.contact_id = ci.contact_id " +
            "JOIN locations l ON c.location_id = l.location_id";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Object[]> records = new ArrayList<>();
            while (rs.next()) {
                records.add(new Object[]{
                    rs.getInt("customer_id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("phone_number"),
                    rs.getString("email_address"),
                    rs.getDate("birthdate"),
                    rs.getString("address"),
                    rs.getString("customer_status"),
                    rs.getDate("registration_date")
                });
            }
            return records.toArray(new Object[0][]);
        }
    }

    public static Object[][] getStoresCustomersBoughtFrom() throws SQLException {
        String sql = 
            "SELECT CONCAT(c.first_name, ' ', c.last_name) AS customer_name, " +
            "GROUP_CONCAT(DISTINCT s.store_name) AS stores_bought_from " +
            "FROM customers c " +
            "JOIN orders o ON o.customer_id = c.customer_id " +
            "JOIN products p ON o.product_id = p.product_id " +
            "JOIN store s ON p.store_id = s.store_id " +
            "GROUP BY c.customer_id";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Object[]> records = new ArrayList<>();
            while (rs.next()) {
                records.add(new Object[]{
                    rs.getString("customer_name"),
                    rs.getString("stores_bought_from")
                });
            }
            return records.toArray(new Object[0][]);
        }
    }

    public static Object[][] getProductRecords() throws SQLException {
        String sql =
            "SELECT p.product_id, p.product_name, p.price, s.store_name, p.stock_count, p.description, p.category, p.r18 " +
            "FROM products p " +
            "JOIN store s ON p.store_id = s.store_id";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            List<Object[]> records = new ArrayList<>();
            DecimalFormat priceFormat = new DecimalFormat("Php #,###.##");

            while (rs.next()) {
                String formattedPrice = priceFormat.format(rs.getDouble("price"));
                records.add(new Object[]{
                    rs.getInt("product_id"),
                    rs.getString("product_name"),
                    formattedPrice,
                    rs.getString("store_name"),
                    rs.getInt("stock_count"),
                    rs.getString("description"),
                    rs.getString("category"),
                    rs.getBoolean("r18")
                });
            }
            return records.toArray(new Object[0][]);
        }
    }


}
