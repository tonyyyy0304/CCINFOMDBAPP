package main.java.mvc_folder;

import java.awt.Taskbar.State;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
        return DriverManager.getConnection(dbUrl, userName, password);
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

    public boolean removeProduct(int productId) throws SQLException
    {
        String sql = "UPDATE products SET is_deleted = 1 WHERE product_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateProduct(int productID, String productName, String description, String r18, double price) throws SQLException
    {
        String sql = "UPDATE products SET product_name = ?, description = ?, r18 = ?, price = ? WHERE product_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, productName);
            stmt.setString(2, description);
            stmt.setString(3, r18);
            stmt.setDouble(4, price);
            stmt.setInt(5, productID);

            return stmt.executeUpdate() > 0; // Returns true if the update was successful
        }
    }

    public String[] getProductData(int productId) throws SQLException
    {
        String sql = "SELECT product_name, price, description, r18 FROM products WHERE product_id = ? AND is_deleted != 1";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
               if (rs.next()) {
                  return new String[]{
                     rs.getString("product_name"),
                     String.valueOf(rs.getDouble("price")),
                     rs.getString("description"),
                     rs.getString("r18")
                  };
               } else {
                  return null; // Product not found
               }
            }
        }
    }

    public boolean productExists(int productId) throws SQLException
    {
        String sql = "SELECT product_id FROM products WHERE product_id = ? AND is_deleted != 1 ";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public Object[][] searchProductRecordsByName(String query) throws SQLException {
        String sql = "SELECT p.product_id, p.product_name, p.price, s.store_name, p.stock_count, p.description, p.category, p.r18 " +
                        "FROM products p " +
                        "JOIN store s ON p.store_id = s.store_id " +
                        "WHERE p.product_name LIKE ? AND p.is_deleted != 1 " +
                        "ORDER BY p.product_id";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            try (ResultSet rs = stmt.executeQuery()) {
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
    
    public Object[][] searchProductRecordsById(String query) throws SQLException {
        String sql = "SELECT p.product_id, p.product_name, p.price, s.store_name, p.stock_count, p.description, p.category, p.r18 " +
                        "FROM products p " +
                        "JOIN store s ON p.store_id = s.store_id " + 
                        "WHERE product_id = ? AND p.is_deleted != 1 " +
                        "ORDER BY p.product_id";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(query));
            try (ResultSet rs = stmt.executeQuery()) {
                
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

    public Object[][] searchStoreRecordsByName(String query) throws SQLException {
        String sql = "SELECT s.store_id, s.store_name, ci.phone_number, ci.email_address, " +
                        "CONCAT('', l.lot_number, ' ', l.street_name, ' ', l.city_name, ' ', l.zip_code, ' ', l.country_name) AS address, " +
                        "s.registration_date " +
                        "FROM store s " +
                        "JOIN contact_information ci ON s.contact_id = ci.contact_id " +
                        "JOIN locations l ON s.location_id = l.location_id " +
                        "WHERE s.store_name LIKE ? " +
                        "ORDER BY s.store_id";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> records = new ArrayList<>();
                while (rs.next()) {
                    records.add(new Object[]{
                        rs.getInt("store_id"),
                        rs.getString("store_name"),
                        rs.getString("phone_number"),
                        rs.getString("email_address"),
                        rs.getString("address"),
                        rs.getDate("registration_date")
                    });
                }
                return records.toArray(new Object[0][]);
            }
        }
    }

    public Object[][] searchStoreRecordsById(String query) throws SQLException {
        String sql = "SELECT s.store_id, s.store_name, ci.phone_number, ci.email_address, " +
                        "CONCAT('', l.lot_number, ' ', l.street_name, ' ', l.city_name, ' ', l.zip_code, ' ', l.country_name) AS address, " +
                        "s.registration_date " +
                        "FROM store s " +
                        "JOIN contact_information ci ON s.contact_id = ci.contact_id " +
                        "JOIN locations l ON s.location_id = l.location_id " +
                        "WHERE s.store_id = ? " +
                        "ORDER BY s.store_id";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(query));
            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> records = new ArrayList<>();
                while (rs.next()) {
                    records.add(new Object[]{
                        rs.getInt("store_id"),
                        rs.getString("store_name"),
                        rs.getString("phone_number"),
                        rs.getString("email_address"),
                        rs.getString("address"),
                        rs.getDate("registration_date")
                    });
                }
                return records.toArray(new Object[0][]);
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
        String sql = "SELECT store_id FROM store WHERE store_id = ? AND is_deleted != 1";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, store_id);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean removeStore(int store_id) throws SQLException {
        String sql = "UPDATE store SET is_deleted = 1 WHERE store_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, store_id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateStore(int store_id, String store_name, int contact_id, int location_id) {
        String sql = "UPDATE store SET store_name = ?, contact_id = ?, location_id = ? WHERE store_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, store_name);
            stmt.setInt(2, contact_id);
            stmt.setInt(3, location_id);
            stmt.setInt(4, store_id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return false;
        }
    }

    public String[] getStoreData(int storeId) {
        String sql = "SELECT s.store_name, ci.phone_number, ci.email_address, " +
                     "l.lot_number, l.street_name, l.city_name, l.zip_code, l.country_name " +
                     "FROM store s " +
                     "JOIN contact_information ci ON s.contact_id = ci.contact_id " +
                     "JOIN locations l ON s.location_id = l.location_id " +
                     "WHERE s.store_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, storeId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new String[]{
                        rs.getString("store_name"),
                        rs.getString("phone_number"),
                        rs.getString("email_address"),
                        rs.getString("lot_number"),
                        rs.getString("street_name"),
                        rs.getString("city_name"),
                        rs.getString("zip_code"),
                        rs.getString("country_name")
                    };
                } else {
                    return null; // Store not found
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return null;
        }
    }

    public Object[][] searchCustomerRecordsById(String query){
        String sql = "SELECT c.customer_id, c.first_name, c.last_name, ci.phone_number, ci.email_address, c.birthdate, " +
                        "CONCAT('', l.lot_number, ' ', l.street_name, ' ', l.city_name, ' ', l.zip_code, ' ', l.country_name) AS address, " +
                        "c.registration_date " +
                        "FROM customers c " +
                        "JOIN contact_information ci ON c.contact_id = ci.contact_id " +
                        "JOIN locations l ON c.location_id = l.location_id " +
                        "WHERE c.customer_id = ? AND c.is_deleted != 1 " +
                        "ORDER BY c.customer_id";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(query));
            try (ResultSet rs = stmt.executeQuery()) {
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
                        rs.getDate("registration_date")
                    });
                }
                return records.toArray(new Object[0][]);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return null;
        }
    }

    public Object[][] searchCustomerRecordsByName(String query) throws SQLException {
        String sql = "SELECT c.customer_id, c.first_name, c.last_name, ci.phone_number, ci.email_address, c.birthdate, " +
                        "CONCAT('', l.lot_number, ' ', l.street_name, ' ', l.city_name, ' ', l.zip_code, ' ', l.country_name) AS address, " +
                    "c.registration_date " +
                        "FROM customers c " +
                        "JOIN contact_information ci ON c.contact_id = ci.contact_id " +
                        "JOIN locations l ON c.location_id = l.location_id " +
                    "WHERE CONCAT(c.first_name, ' ', c.last_name) LIKE ? AND c.is_deleted != 1 " +
                        "ORDER BY c.customer_id";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            try (ResultSet rs = stmt.executeQuery()) {
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
                        rs.getDate("registration_date")
                    });
                }
                return records.toArray(new Object[0][]);
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
        String sql = "UPDATE customers SET is_deleted = 1 WHERE customer_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            return stmt.executeUpdate() > 0;
        }
    }

    public String[] getCustomerData(int customerID) {
        String sql = "SELECT c.first_name, c.last_name, " +
                     "ci.phone_number, ci.email_address, " +
                     "l.lot_number, l.street_name, l.city_name, l.zip_code, l.country_name " +
                     "FROM customers c " +
                     "JOIN contact_information ci ON c.contact_id = ci.contact_id " +
                     "JOIN locations l ON c.location_id = l.location_id " +
                     "WHERE c.customer_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new String[]{
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone_number"),
                        rs.getString("email_address"),
                        rs.getString("lot_number"),
                        rs.getString("street_name"),
                        rs.getString("city_name"),
                        rs.getString("zip_code"),
                        rs.getString("country_name")
                    };
                } else {
                    return null; // Customer not found
                }
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return null;
        }
    }

    public boolean customerExists(int customerId) throws SQLException {
        String sql = "SELECT customer_id FROM customers WHERE customer_id = ? AND is_deleted != 1 ";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean updateCustomer(int customerId, String firstName, String lastName, int contactId, int locationId) {
        String sql = "UPDATE customers SET first_name = ?, last_name = ?, contact_id = ?, location_id = ? WHERE customer_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setInt(3, contactId);
            stmt.setInt(4, locationId);
            stmt.setInt(5, customerId);

            return stmt.executeUpdate() > 0; // Returns true if the update was successful
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return false; // Return false if there was an error
        }
    }

    public boolean addLogisticsCompany(String name, int location, String scope) throws SQLException {
        String sql = "INSERT INTO logistics_companies (logistics_company_name, location_id, shipment_scope) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, location);
            stmt.setString(3, scope);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Returns true if the insert was successful
        }
    }

    public boolean removeLogisticsCompany(int logisticsCompanyId) throws SQLException {
        String sql = "UPDATE logistics_companies SET is_deleted = 1 WHERE logistics_company_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, logisticsCompanyId);
            return stmt.executeUpdate() > 0; // Returns true if the deletion was successful
        }
    }

    public boolean updateLogisticsCompany(int logisticsCompanyId, String name, int location, String scope) throws SQLException {
        String sql = "UPDATE logistics_companies SET logistics_company_name = ?, location_id = ?, shipment_scope = ? WHERE logistics_company_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setInt(2, location);
            stmt.setString(3, scope);
            stmt.setInt(4, logisticsCompanyId);
            return stmt.executeUpdate() > 0; // Returns true if the update was successful
        }
    }

    public String[] getLogisticsCompanyData(int logisticsCompanyId) throws SQLException {
        String sql = "SELECT lc.logistics_company_name, lc.shipment_scope, " +
                     "l.lot_number, l.street_name, l.city_name, l.zip_code, l.country_name, " +
                     "lc.shipment_scope " +
                     "FROM logistics_companies lc " +
                     "JOIN locations l ON lc.location_id = l.location_id " +
                     "WHERE lc.logistics_company_id = ? AND lc.is_deleted != 1";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, logisticsCompanyId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new String[]{
                        rs.getString("logistics_company_name"),
                        rs.getString("lot_number"),
                        rs.getString("street_name"),
                        rs.getString("city_name"),
                        rs.getString("zip_code"),
                        rs.getString("country_name"),
                        rs.getString("shipment_scope")
                    };
                } else {
                    return null; // Logistics company not found
                }
            }
        }
    }

    public boolean logisticsCompanyExists(int logisticsCompanyId) throws SQLException {
        String sql = "SELECT logistics_company_id FROM logistics_companies WHERE logistics_company_id = ? AND is_deleted != 1 ";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, logisticsCompanyId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if the logistics company exists
            }
        }
    }

    // ----------------- Queries -----------------

    public static Object[][] getCustomerRecords() throws SQLException {
        String sql = "SELECT c.customer_id, c.first_name, c.last_name, ci.phone_number, ci.email_address, c.birthdate, " +
                        "CONCAT('', l.lot_number, ' ', l.street_name, ' ', l.city_name, ' ', l.zip_code, ' ', l.country_name) AS address, " +
                 "c.registration_date " +
                        "FROM customers c " +
                        "JOIN contact_information ci ON c.contact_id = ci.contact_id " +
                 "JOIN locations l ON c.location_id = l.location_id " +
                 "WHERE c.is_deleted != 1";
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
                        rs.getDate("registration_date")
                });
            }
            return records.toArray(new Object[0][]);
        }
    } 

    public static Object[][] getListOfStoresSellingSameCategory(String category){
        String sql = "SELECT s.store_name, COUNT(p.product_id) AS num_products " +
                        "FROM products p " +
                        "JOIN store s ON p.store_id = s.store_id " +
                        "WHERE p.category = ? " +
                        "GROUP BY s.store_id";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> records = new ArrayList<>();
                DecimalFormat priceFormat = new DecimalFormat("Php #,###.##");
                while (rs.next()) {
                    String formattedPrice = priceFormat.format(rs.getDouble("price"));
                    records.add(new Object[]{
                        rs.getString("store_name"),
                        rs.getString("product_name"),
                        rs.getString("logistics_company_name"),
                        formattedPrice,
                        rs.getInt("stock_count")
                    });
                }
                return records.toArray(new Object[0][]);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return null;
        }
    }

    

    public static Object[][] getOrdersHandledByLogisticsCompanyName(String companyName){
        String sql = "SELECT o.order_id, s.logistics_company_id, lc.logistics_company_name, o.order_date, s.expected_arrival_date " +
                        "FROM orders o " +
                        "JOIN shipping s ON o.order_id = s.order_id " +
                        "JOIN logistics_companies lc ON s.logistics_company_id = lc.logistics_company_id " +
                        "WHERE lc.logistics_company_name LIKE ? " +
                        "ORDER BY o.order_id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + companyName + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> records = new ArrayList<>();
                while (rs.next()) {
                    records.add(new Object[]{
                        rs.getInt("order_id"),
                        rs.getInt("logistics_company_id"),
                        rs.getString("logistics_company_name"),
                        rs.getDate("order_date"),
                        rs.getDate("expected_arrival_date")
                    });
                }
                
                return records.toArray(new Object[0][]);
            }catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                return null;
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return null;
        }

    }

    public Object[][] getOrdersHandledByLogisticsCompanyId(int companyId){
        String sql = "SELECT o.order_id, s.logistics_company_id, lc.logistics_company_name, o.order_date, s.expected_arrival_date " +
                        "FROM orders o " +
                        "JOIN shipping s ON o.order_id = s.order_id " +
                        "JOIN logistics_companies lc ON s.logistics_company_id = lc.logistics_company_id " +
                        "WHERE s.logistics_company_id = ? " +
                        "ORDER BY o.order_id";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, companyId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> records = new ArrayList<>();
                while (rs.next()) {
                    records.add(new Object[]{
                        rs.getInt("order_id"),
                        rs.getInt("logistics_company_id"),
                        rs.getString("logistics_company_name"),
                        rs.getDate("order_date"),
                        rs.getDate("expected_arrival_date")
                    });
                }
                return records.toArray(new Object[0][]);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return null;
        }
    }

    public static Object[][] getListOfProductsStoreSells(String storeName){
        String sql = "SELECT p.product_id, p.product_name, p.price, p.stock_count, p.description, p.category, p.r18 " +
                        "FROM products p " +
                        "JOIN store s ON p.store_id = s.store_id " +
                        "WHERE s.store_name LIKE ? AND p.is_deleted != 1 " +
                        "ORDER BY p.product_id";
        try (Connection conn = getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, "%" + storeName + "%");
                try (ResultSet rs = stmt.executeQuery()) {
                    List<Object[]> records = new ArrayList<>();
                    DecimalFormat priceFormat = new DecimalFormat("Php #,###.##");
                    while (rs.next()) {
                        String formattedPrice = priceFormat.format(rs.getDouble("price"));
                        records.add(new Object[]{
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            formattedPrice,
                            rs.getInt("stock_count"),
                            rs.getString("description"),
                            rs.getString("category"),
                            rs.getBoolean("r18")
                        });
                    }
                    return records.toArray(new Object[0][]);
                }
            } catch (SQLException e) {
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                return null;
            }
        }

    public static Object[][] getListOfProductsStoreSells(int storeId){
        String sql = "SELECT p.product_id, p.product_name, p.price, p.stock_count, p.description, p.category, p.r18 " +
                        "FROM products p " +
                        "WHERE p.store_id = ? AND p.is_deleted != 1 " +
                        "ORDER BY p.product_id";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, storeId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> records = new ArrayList<>();
                DecimalFormat priceFormat = new DecimalFormat("Php #,###.##");
                while (rs.next()) {
                    String formattedPrice = priceFormat.format(rs.getDouble("price"));
                    records.add(new Object[]{
                        rs.getInt("product_id"),
                        rs.getString("product_name"),
                        formattedPrice,
                        rs.getInt("stock_count"),
                        rs.getString("description"),
                        rs.getString("category"),
                        rs.getBoolean("r18")
                    });
                }
                return records.toArray(new Object[0][]);
            }
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return null;
        }
    }

    public static Object[][] getStoresCustomersBoughtFrom() throws SQLException {
        String sql =
                "SELECT CONCAT(c.first_name, ' ', c.last_name) AS customer_name, " +
                        "GROUP_CONCAT(DISTINCT s.store_name SEPARATOR ', ') AS stores_bought_from " +
                        "FROM customers c " +
                        "JOIN orders o ON o.customer_id = c.customer_id " +
                        "JOIN products p ON o.product_id = p.product_id " +
                        "JOIN store s ON p.store_id = s.store_id " +
                        "WHERE c.is_deleted != 1 " +
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
                        "JOIN store s ON p.store_id = s.store_id" + 
                        " WHERE p.is_deleted != 1 ";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) 
        {

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

    public static Object[][] getStoreRecords() throws SQLException {
        String sql = "SELECT s.store_id, s.store_name, ci.phone_number, ci.email_address, " +
            "CONCAT('', l.lot_number, ' ', l.street_name, ' ', l.city_name, ' ', l.zip_code, ' ', l.country_name) AS address, " +
            "s.registration_date " +
            "FROM store s " +
            "JOIN contact_information ci ON s.contact_id = ci.contact_id " +
            "JOIN locations l ON s.location_id = l.location_id " + 
            "WHERE s.is_deleted != 1 ";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Object[]> records = new ArrayList<>();
            while (rs.next()) {
                records.add(new Object[]{
                    rs.getInt("store_id"),
                    rs.getString("store_name"),
                    rs.getString("phone_number"),
                    rs.getString("email_address"),
                    rs.getString("address"),
                    rs.getDate("registration_date")
                });
            }
            return records.toArray(new Object[0][]);
        }
    }

    public Object[][] searchLogisticsRecordsByName(String query) throws SQLException {
        String sql = "SELECT lc.logistics_company_id, lc.logistics_company_name, " +
            "CONCAT('', l.lot_number, ' ', l.street_name, ' ', l.city_name, ' ', l.zip_code, ' ', l.country_name) AS address, " +
            "lc.shipment_scope " +
            "FROM logistics_companies lc " +
            "JOIN locations l ON lc.location_id = l.location_id " +
            "WHERE lc.logistics_company_name LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, "%" + query + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> records = new ArrayList<>();
                while (rs.next()) {
                    records.add(new Object[]{
                        rs.getInt("logistics_company_id"),
                        rs.getString("logistics_company_name"),
                        rs.getString("address"),
                        rs.getString("shipment_scope")
                    });
                }
                return records.toArray(new Object[0][]);
            }
        }
    }

    public Object[][] searchLogisticsRecordsById(String query) throws SQLException {
        String sql = "SELECT lc.logistics_company_id, lc.logistics_company_name, " +
            "CONCAT('', l.lot_number, ' ', l.street_name, ' ', l.city_name, ' ', l.zip_code, ' ', l.country_name) AS address, " +
            "lc.shipment_scope " +
            "FROM logistics_companies lc " +
            "JOIN locations l ON lc.location_id = l.location_id " +
            "WHERE lc.logistics_company_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(query));
            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> records = new ArrayList<>();
                while (rs.next()) {
                    records.add(new Object[]{
                        rs.getInt("logistics_company_id"),
                        rs.getString("logistics_company_name"),
                        rs.getString("address"),
                        rs.getString("shipment_scope")
                    });
                }
                return records.toArray(new Object[0][]);
            }
        }
    }

    public static Object[][] getLogisticsRecords() throws SQLException {
        String sql = "SELECT lc.logistics_company_id, lc.logistics_company_name, " +
            "CONCAT('', l.lot_number, ' ', l.street_name, ' ', l.city_name, ' ', l.zip_code, ' ', l.country_name) AS address, " +
            "lc.shipment_scope " +
            "FROM logistics_companies lc " +
            "JOIN locations l ON lc.location_id = l.location_id " +
            "WHERE lc.is_deleted != 1 ";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            List<Object[]> records = new ArrayList<>();
            while (rs.next()) {
                records.add(new Object[]{
                    rs.getInt("logistics_company_id"),
                    rs.getString("logistics_company_name"),
                    rs.getString("address"),
                    rs.getString("shipment_scope")
                });
            }
            return records.toArray(new Object[0][]);
        }
    }

    public static Object[][] getCustomerStats(int startYear, int endYear) throws SQLException {
        String sql = "SELECT YEAR(o.order_date) as year, " +
                     "CONCAT(c.first_name, ' ', c.last_name) as customer_name, " +
                     "COUNT(o.order_id) as total_orders, " +
                     "SUM(o.quantity * p.price) as total_sales " +
                     "FROM customers c " +
                     "JOIN orders o ON o.customer_id = c.customer_id " +
                     "JOIN products p ON o.product_id = p.product_id " +
                     "WHERE YEAR(o.order_date) BETWEEN ? AND ? " +
                     "GROUP BY year, customer_name " +
                     "ORDER BY year, customer_name";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, startYear);
            stmt.setInt(2, endYear);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> records = new ArrayList<>();
                while (rs.next()) {
                    int year = rs.getInt("year");
                    String customerName = rs.getString("customer_name");
                    int totalOrders = rs.getInt("total_orders");
                    double totalSales = rs.getDouble("total_sales");

                    records.add(new Object[]{year, customerName, totalOrders, totalSales});
                }
                return records.toArray(new Object[0][]);
            }
        }
    }

    public static Object[][] getProductSales(int startYear, int endYear) throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT YEAR(o.order_date) AS year, p.category, SUM(o.quantity * p.price) AS total_sales ")
           .append("FROM products p ")
           .append("LEFT JOIN orders o ON p.product_id = o.product_id ")
           .append("WHERE YEAR(o.order_date) BETWEEN ? AND ? ")
           .append("GROUP BY YEAR(o.order_date), p.category ")
           .append("ORDER BY YEAR(o.order_date), p.category");

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {
            stmt.setInt(1, startYear);
            stmt.setInt(2, endYear);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> records = new ArrayList<>();
                while (rs.next()) {
                    int year = rs.getInt("year");
                    String category = rs.getString("category");
                    double totalSales = rs.getDouble("total_sales");

                    records.add(new Object[]{year, category, totalSales});
                }

                return records.toArray(new Object[0][]);
            }
        }
    }

    public static Object[][] getPaymentReports(int startYear, int endYear) throws SQLException {
        String sql = "SELECT YEAR(payment_date) as year, payment_status, COUNT(payment_status) as count " +
                     "FROM payments " +
                     "WHERE YEAR(payment_date) BETWEEN ? AND ? " +
                     "GROUP BY YEAR(payment_date), payment_status " + 
                     "ORDER BY year, payment_status";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, startYear);
            stmt.setInt(2, endYear);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> records = new ArrayList<>();
                while (rs.next()) {
                    int year = rs.getInt("year");
                    String paymentStatus = rs.getString("payment_status");
                    int count = rs.getInt("count");

                    records.add(new Object[]{year, paymentStatus, count});
                }
                return records.toArray(new Object[0][]);
            }
        }
    }

    public static Object[][] getAffinity(int startYear, int endYear) throws SQLException {
        // TODO: fix sql query
        String sql = "SELECT YEAR(o.order_date) as year, " +
                     "CONCAT(c.first_name, ' ', c.last_name) as customer_name, s.store_name, " +
                     "COUNT(o.order_id) as num_orders_made_at_store, " +
                     "SUM(pm.amount_paid) as total_amount_spent " +
                     "FROM customers c " +
                     "JOIN orders o ON o.customer_id = c.customer_id " +
                     "JOIN products p ON p.product_id = o.product_id " +
                     "JOIN store s ON s.store_id = p.store_id " +
                     "JOIN payments pm on pm.order_id = pm.order_id " +
                     "WHERE YEAR(o.order_date) BETWEEN ? AND ? AND c.is_deleted != 1 " +
                     "GROUP BY year, customer_name, store_name " +
                     "ORDER BY year, customer_name";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, startYear);
            stmt.setInt(2, endYear);

            try (ResultSet rs = stmt.executeQuery()) {
                List<Object[]> records = new ArrayList<>();
                while (rs.next()) {
                    int year = rs.getInt("year");
                    String customerName = rs.getString("customer_name");
                    String storeName = rs.getString("store_name");
                    int numOrders = rs.getInt("num_orders_made_at_store");
                    double totalAmountSpent = rs.getDouble("total_amount_spent");

                    records.add(new Object[]{year, customerName, storeName, numOrders, totalAmountSpent});
                }
                return records.toArray(new Object[0][]);
            }
        }
    }


    public boolean adjustStock(int productId, int newStockCount) {
        String sql = "UPDATE products SET stock_count = ? WHERE product_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newStockCount);
            stmt.setInt(2, productId);

            // Execute the update and return true if successful
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return false; // Return false if there was an error
        }
    }

    public int getCurrentStock(int productId) throws SQLException {
        String sql = "SELECT stock_count FROM products WHERE product_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("stock_count");
                } else {
                    throw new SQLException("Product not found");
                }
            }
        }
    }

    public boolean placeOrder(int customerId, int productId, int quantity,
        int lotNum, String streetName, String cityName, int zipCode, String countryName,
        String paymentMethod) throws SQLException
    {
        // Check if the customer exists
        if (!customerExists(customerId)) {
            return false; // Customer does not exist
        }

        // Check if the product is R18
        String checkR18Sql = "SELECT r18 FROM products WHERE product_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement checkR18Stmt = conn.prepareStatement(checkR18Sql)) {
            checkR18Stmt.setInt(1, productId);
            ResultSet rs = checkR18Stmt.executeQuery();

            if (rs.next()) {
                String isR18 = rs.getString("r18");
                if (isR18.equalsIgnoreCase("T")) {
                    // Check if the customer is 18 years old or older
                    String checkAgeSql = "SELECT CASE " +
                        "WHEN TIMESTAMPDIFF(YEAR, (SELECT c.birthdate FROM customers c WHERE c.customer_id = ?), NOW()) < 18 THEN 'TRUE' " +
                        "ELSE 'FALSE' END AS result " +
                        "FROM products p " +
                        "WHERE p.product_id = ?";
                    try (PreparedStatement checkAgeStmt = conn.prepareStatement(checkAgeSql)) {
                        checkAgeStmt.setInt(1, customerId);
                        checkAgeStmt.setInt(2, productId);
                        ResultSet ageRs = checkAgeStmt.executeQuery();
                        if (ageRs.next()) {
                            String isUnderage = ageRs.getString("result");
                            if (isUnderage.equalsIgnoreCase("TRUE")) {
                                return false; // Customer is under 18, cannot purchase R18 product
                            }
                        }
                    }
                }
            } else {
                return false; // Product not found
            }
        }

        // Check if the product exists and its stock count
        String checkProductSql = "SELECT stock_count FROM products WHERE product_id = ? AND is_deleted != 1 ";
        try (Connection conn = getConnection();
             PreparedStatement checkStmt = conn.prepareStatement(checkProductSql)) {
            checkStmt.setInt(1, productId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int availableStock = rs.getInt("stock_count");
                if (quantity > availableStock) {
                    // Not enough stock available
                    return false; // Indicate failure due to insufficient stock
                }
            } else {
                // Product not found
                return false; // Indicate failure due to product not existing
            }
        }

        int locationId;
        // Creates a new location if it does not exist
        if (!locationExists(lotNum, streetName, cityName, countryName, zipCode)) {
            addLocationId(lotNum, streetName, cityName, countryName, zipCode);
        }
        locationId = getLocationId(lotNum, streetName, cityName, countryName, zipCode);
        
        // Now, insert the order into the orders table
        String insertOrderSql = "INSERT INTO orders (customer_id, product_id, quantity, payment_method, delivery_location_id) VALUES (?, ?, ?, ?, ?)";
        int orderId;
        try (Connection conn = getConnection();
             PreparedStatement insertStmt = conn.prepareStatement(insertOrderSql, Statement.RETURN_GENERATED_KEYS)) {
            insertStmt.setInt(1, customerId);
            insertStmt.setInt(2, productId);
            insertStmt.setInt(3, quantity);
            insertStmt.setString(4, paymentMethod);
            insertStmt.setInt(5, locationId);
            insertStmt.executeUpdate();

            ResultSet generatedKeys = insertStmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Creating order failed, no ID obtained.");
            }
        }

        // Insert the payment to the payments table
        String insertPaymentSql = "INSERT INTO payments (order_id) VALUES (?)";
        try (Connection conn = getConnection();
             PreparedStatement insertPaymentStmt = conn.prepareStatement(insertPaymentSql)) {
            insertPaymentStmt.setInt(1, orderId);
            insertPaymentStmt.executeUpdate();
        }
        

        // Update the stock count of the product
        String updateStockSql = "UPDATE products SET stock_count = stock_count - ? WHERE product_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateStockSql)) {
            updateStmt.setInt(1, quantity);
            updateStmt.setInt(2, productId);
            updateStmt.executeUpdate();
        }

        return true; // Order placed successfully
    }

    private int getStoreIdByProductId(int productId) throws SQLException {
        String sql = "SELECT store_id FROM products WHERE product_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("store_id");
                } else {
                    throw new SQLException("Store ID not found for product ID: " + productId);
                }
            }
        }
    }

    public boolean isOrderPending(int orderId) throws SQLException {
        String sql = "SELECT payment_status FROM payments WHERE order_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String paymentStatus = rs.getString("payment_status");
                    return paymentStatus.equalsIgnoreCase("Pending");
                } else {
                    throw new SQLException("Order not found");
                }
            }
        }
    }

    public boolean cancelPayForOrder(int orderId) throws SQLException {
        // Check if the order is pending
        if (!isOrderPending(orderId)) {
            return false; // Payment already completed/cancelled
        }
        String sql = "UPDATE payments SET payment_status = 'Cancelled' WHERE order_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            return stmt.executeUpdate() > 0; // Returns true if the update was successful
        }
    }

    public boolean isOrderInternational(int orderId) throws SQLException {
        // Check if order is international by comparing the country of the store and the delivery address given in order
        String sql = "SELECT ol.country_name AS delivery_country, sl.country_name AS store_country FROM orders o " +
            "JOIN products p ON o.product_id = p.product_id " +
            "JOIN store s ON p.store_id = s.store_id " + 
            "JOIN locations ol ON o.delivery_location_id = ol.location_id " +
            "JOIN locations sl ON s.location_id = sl.location_id " +
            "WHERE o.order_id = ?";
     
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String deliveryCountry = rs.getString("delivery_country");
                    String storeCountry = rs.getString("store_country");
                    return !deliveryCountry.equals(storeCountry);
                } else {
                    throw new SQLException("Order not found");
                }
            }
            catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
                return false;
            }
        }catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return false;
        }
    }

    public boolean payForOrder(int orderId) throws SQLException {
        // Check if order is pending
        if (!isOrderPending(orderId)) {
            return false; // Payment already completed/cancelled
        }


        double shippingPrice = 50;
        //check if order is international
        if (isOrderInternational(orderId)) {
            shippingPrice = 200;
        }

        // Get the price of the product
        String sql = "SELECT p.price FROM orders o " +
            "JOIN products p ON o.product_id = p.product_id " +
            "WHERE o.order_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double price = rs.getDouble("price");
                    double totalAmount = price + shippingPrice;

                    // Proceed to update the payment
                    String updatePaymentSql =
                        "UPDATE payments SET payment_status = 'Completed', " + 
                        "amount_paid = ?, payment_date = CURRENT_DATE WHERE order_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updatePaymentSql)) {
                        updateStmt.setDouble(1, totalAmount);
                        updateStmt.setInt(2, orderId);
                        updateStmt.executeUpdate();
                    }

                    return true; // Payment processed successfully
                } else {
                    return false; // Order does not exist
                }
            }
        }
    }

  

    public boolean orderExists(int orderId) throws SQLException {
        String sql = "SELECT order_id FROM orders WHERE order_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if the order exists
            }
        }
    }

    public boolean matchLogiscticsScopeToOrderShipping(int orderId, int logisticsCompanyId){
        //check if logistics company covers international shipping
        String checkScopeSql = "SELECT shipment_scope FROM logistics_companies WHERE logistics_company_id = ?";
        boolean isLogisticsInternational = false;
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(checkScopeSql)) {
            stmt.setInt(1, logisticsCompanyId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String scope = rs.getString("shipment_scope");
                    if (scope.equals("International")) {
                        isLogisticsInternational = true;
                    }
                } else {
                    return false; // Logistics company does not exist
                }
            }
            catch(SQLException e){
                System.out.println("SQLException: " + e.getMessage());
                System.out.println("SQLState: " + e.getSQLState());
                System.out.println("VendorError: " + e.getErrorCode());
            }
        }
        catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        try{
            if(isOrderInternational(orderId) && isLogisticsInternational) {
                return true; // Logistics company covers international shipping
            }else return false;
        }catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return false;
        }
    }

    public boolean orderShipped(int orderId) throws SQLException {
        // join orders and shipping tables, if order exist in shipping table, then it is already assigned a logistics company
        String sql = "SELECT o.order_id FROM orders o " +
            "JOIN shipping s ON o.order_id = s.order_id " +
            "WHERE o.order_id = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if the order is already shipped
            }
        }
    }

    public boolean orderPaid(int orderId){
        String sql = "SELECT payment_status FROM payments WHERE order_id = ?";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String paymentStatus = rs.getString("payment_status");
                    return paymentStatus.equals("Completed");
                } else {
                    return false; // Order does not exist
                }
            }
        }
        catch(SQLException e){
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
            return false;
        }
    }

    public boolean shipOrder(int orderId, int logisticsCompanyId) throws SQLException {
        // Check if the order exists
        if (!orderExists(orderId)) {
            return false; // Order does not exist
        }

        // Check if the logistics company exists
        if (!logisticsCompanyExists(logisticsCompanyId)) {
            return false; // Logistics company does not exist
        }

        if(!matchLogiscticsScopeToOrderShipping(orderId, logisticsCompanyId)){
            return false; // Logistics company does not cover international shipping
        }

        int deliveryOffset = 3;
        if(isOrderInternational(orderId)){
            deliveryOffset = 7;
        }
        // Insert the shipping record, add the expected arrival date based on the delivery offset
        String insertShippingSql = "INSERT INTO shipping (order_id, logistics_company_id, expected_arrival_date) VALUES (?, ?, DATE_ADD(CURRENT_DATE, INTERVAL ? DAY))";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(insertShippingSql)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, logisticsCompanyId);
            stmt.setInt(3, deliveryOffset);
            stmt.executeUpdate();
            return true;
        }
    }


    
}
