package src.main.java.mvc_folder;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Model 
{   
    private static final String dbUrl = "jdbc:mysql://localhost/test";
    private static final String userName = "minty";
    private static final String password = "greatsqldb";

    private Connection conn = null;

    public Model() {
        try {
            conn = DriverManager.getConnection(dbUrl, userName, password);
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }
}
