package main.java.mvc_folder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Model 
{   
    private static final String dbUrl = "jdbc:mysql://localhost/dbhr";
    private static final String userName = "root";
    private static final String password = "123456";

    private Connection connection = null;

    public Model() {
        try 
        {
            connection = DriverManager.getConnection(dbUrl, userName, password);
            System.out.println("Connection successful!");
        } 
        catch (SQLException e) 
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        } 
        finally 
        {
            if (connection != null) 
            {
                try 
                {
                    connection.close();
                } 
                catch (SQLException e) 
                {
                    System.out.println("SQLException on close: " + e.getMessage());
                }
            }
        }
    }
}
