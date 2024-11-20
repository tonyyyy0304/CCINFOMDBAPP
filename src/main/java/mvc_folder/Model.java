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

    public Object[][] searchProductRecordsByName(String query) throws SQLException {
        String sql = "SELECT p.product_id, p.product_name, p.price, s.store_name, p.stock_count, p.description, p.category, p.r18 " +
                        "FROM products p " +
                        "JOIN store s ON p.store_id = s.store_id " +
                        "WHERE p.product_name LIKE ?" +
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
                        "WHERE product_id = ?" +
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

    public boolean removeStore(int store_id) throws SQLException {
        String sql = "DELETE FROM store WHERE store_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, store_id);
            return stmt.executeUpdate() > 0;
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
        String sql = "DELETE FROM customers WHERE customer_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean customerExists(int customerId) throws SQLException {
        String sql = "SELECT customer_id FROM customers WHERE customer_id = ?";
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

    public static Object[][] getStoresCustomersBoughtFrom() throws SQLException {
        String sql =
                "SELECT CONCAT(c.first_name, ' ', c.last_name) AS customer_name, " +
                        "GROUP_CONCAT(DISTINCT s.store_name SEPARATOR ', ') AS stores_bought_from " +
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
            "JOIN locations l ON s.location_id = l.location_id";

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

    public static Object[][] getLogisticsRecords() throws SQLException {
        String sql = "SELECT lc.logistics_company_id, lc.logistics_company_name, " +
            "CONCAT('', l.lot_number, ' ', l.street_name, ' ', l.city_name, ' ', l.zip_code, ' ', l.country_name) AS address, " +
            "lc.shipment_scope " +
            "FROM logistics_companies lc " +
            "JOIN locations l ON lc.location_id = l.location_id";

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

    public static Object[][] getCustomerStats() throws SQLException {
        String sql = 
        "SELECT c.customer_id, CONCAT(c.first_name, ' ', c.last_name) AS customer_name,"+ 
        " COUNT(o.order_id) / (TIMESTAMPDIFF(YEAR, c.registration_date, NOW()) + 1) AS num_orders_per_year,"+ 
        " SUM(amount_paid) / (TIMESTAMPDIFF(YEAR, c.registration_date, NOW()) + 1) AS amount_spent_per_year" +
        " FROM customers c"+ 
        " LEFT JOIN orders o ON c.customer_id = o.customer_id"+
        " LEFT JOIN payments p ON o.order_id = p.order_id"+
        " GROUP BY c.customer_id"+
        " ORDER BY c.customer_id";

        try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery())
        {
            List<Object[]> records = new ArrayList<>();
            while (rs.next()) {
                int customerId = rs.getInt("customer_id");
                String customerName = rs.getString("customer_name");
                double numOrdersPerYear = rs.getDouble("num_orders_per_year");
                double amountSpentPerYear = rs.getDouble("amount_spent_per_year");
                BigDecimal roundedAmountSpent = BigDecimal.valueOf(amountSpentPerYear).setScale(2, RoundingMode.HALF_UP);

                records.add(new Object[]{
                    customerId,
                    customerName,
                    numOrdersPerYear,
                    roundedAmountSpent.doubleValue()
                });
            }
            return records.toArray(new Object[0][]);
        }
    }

    public static Object[][] getProductSales(String category) throws SQLException {
        // TODO: change to show yearly average sales instead of monthly 
        String sql = 
        "SELECT monthly_sales.category, AVG(monthly_sales.total_sales) AS total_sales_per_month"+
        " FROM ("+
        " SELECT p1.category," +
        " DATE_FORMAT(o1.order_date, '%Y-%m') AS month," +
        " SUM(pm1.amount_paid) AS total_sales"+
        " FROM products p1"+
        " LEFT JOIN orders o1 ON o1.product_id = p1.product_id"+
        " LEFT JOIN payments pm1 ON pm1.order_id = o1.order_id"+
        " GROUP BY p1.category, month"+
        ") AS monthly_sales"+
        " WHERE monthly_sales.category = ?"+
        " GROUP BY monthly_sales.category"+
        " ORDER BY monthly_sales.category";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, category); // Set the category parameter
            try (ResultSet rs = stmt.executeQuery()) {

                List<Object[]> records = new ArrayList<>();
                while (rs.next()) {
                    String categoryValue = rs.getString("category");
                    double totalSalesPerMonth = rs.getDouble("total_sales_per_month");
                    BigDecimal roundedTotalSales = BigDecimal.valueOf(totalSalesPerMonth).setScale(2, RoundingMode.HALF_UP);

                    records.add(new Object[]{
                        categoryValue,
                        roundedTotalSales.doubleValue()
                    });
                }
                return records.toArray(new Object[0][]);
            }
        }
    }

    public static Object[][] getPaymentReports(String status) throws SQLException {
        String sql =
            "SELECT YEAR(p.payment_date) AS year, p.payment_status, COUNT(*) AS number_of_orders " +
            "FROM payments p " +
            "WHERE p.payment_status = ? " +
            "GROUP BY YEAR(p.payment_date), p.payment_status " +
            "ORDER BY year, p.payment_status";
    
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status); // Set the status parameter
            try (ResultSet rs = stmt.executeQuery()) {
    
                List<Object[]> records = new ArrayList<>();
                while (rs.next()) {
                    records.add(new Object[]{
                        rs.getInt("year"),
                        rs.getString("payment_status"),
                        rs.getInt("number_of_orders")
                    });
                }
                return records.toArray(new Object[0][]);
            }
        }
    }

    public static Object[][] getAffinity() throws SQLException {
        String sql = 
        "SELECT" + 
        " CONCAT(c.first_name, ' ', c.last_name) AS customer_name," + 
        " s.store_name," +
        " COUNT(o.order_id) AS num_orders_made_at_store," + 
        " SUM(pm.amount_paid) AS total_amount_spent" + 
        " FROM" + 
        " customers c" +
        " LEFT JOIN" +
        " orders o ON o.customer_id = c.customer_id" + 
        " LEFT JOIN" +
        " products p ON p.product_id = o.product_id" +
        " LEFT JOIN" + 
        " store s ON s.store_id = p.store_id" +
        " LEFT JOIN" + 
        " payments pm ON pm.order_id = o.order_id" +
        " GROUP BY" +
        " c.customer_id, s.store_id" + 
        " ORDER BY" + 
        " customer_name, s.store_name";

        try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) 
        {
            List<Object[]> records = new ArrayList<>();
            while (rs.next()) {
                String customerName = rs.getString("customer_name");
                String storeName = rs.getString("store_name");
                int numOrdersMadeAtStore = rs.getInt("num_orders_made_at_store");
                double totalAmountSpent = rs.getDouble("total_amount_spent");
                BigDecimal roundedTotalAmountSpent = BigDecimal.valueOf(totalAmountSpent).setScale(2, RoundingMode.HALF_UP);
                records.add(new Object[]{
                    customerName,
                    storeName,
                    numOrdersMadeAtStore,
                    roundedTotalAmountSpent.doubleValue()
                });
            }
            return records.toArray(new Object[0][]);
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

        // Check if the product exists and its stock count
        String checkProductSql = "SELECT stock_count FROM products WHERE product_id = ?";
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
        String insertPaymentSql = "INSERT INTO payments (order_id, store_id, customer_id, product_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement insertPaymentStmt = conn.prepareStatement(insertPaymentSql)) {
            insertPaymentStmt.setInt(1, orderId);
            insertPaymentStmt.setInt(2, getStoreIdByProductId(productId));
            insertPaymentStmt.setInt(3, customerId);
            insertPaymentStmt.setInt(4, productId);
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

    public boolean payForOrder(int customerId, int orderId, double paymentAmount) throws SQLException {
        String sql = "SELECT o.order_id, o.customer_id, o.price, o.shipping_price FROM orders o WHERE o.order_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) 
        {
            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int orderCustomerId = rs.getInt("customer_id");
                    double price = rs.getDouble("price");
                    double shippingPrice = rs.getDouble("shipping_price");
                    double totalAmount = price + shippingPrice;

                    // Check if the customer ID matches
                    if (orderCustomerId != customerId) {
                        return false; // Customer does not match the order
                    }

                    // Check if the payment amount is sufficient
                    if (paymentAmount < totalAmount) {
                        return false; // Payment is less than the total amount
                    }
                    paymentAmount = totalAmount; // Set the payment amount to the total amount

                    // Proceed to update the payment
                    String updatePaymentSql =
                        "UPDATE payments SET payment_status = 'Completed', " + 
                        "amount_paid = ?, payment_date = CURRENT_DATE WHERE order_id = ?";
                    try (PreparedStatement updateStmt = conn.prepareStatement(updatePaymentSql)) {
                        updateStmt.setDouble(1, paymentAmount);
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

    public boolean logisticsCompanyExists(int logisticsCompanyId) throws SQLException {
        String sql = "SELECT logistics_company_id FROM logistics_companies WHERE logistics_company_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, logisticsCompanyId);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Returns true if the logistics company exists
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

    public boolean shipOrder(int orderId, int logisticsCompanyId) throws SQLException {
        // Check if the order exists
        if (!orderExists(orderId)) {
            return false; // Order does not exist
        }

        // Check if the logistics company exists
        if (!logisticsCompanyExists(logisticsCompanyId)) {
            return false; // Logistics company does not exist
        }

        // Update the order status to 'Shipped' and set the logistics company ID
        String sql = "UPDATE orders SET order_status = 'Shipped', logistics_company_id = ? WHERE order_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, logisticsCompanyId);
            stmt.setInt(2, orderId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Returns true if the update was successful
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
}
