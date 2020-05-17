
package app.classes;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {
    
    public static Connection connection;
    
    public static Connection DbConnect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/gpv2", "root", "");
            return connection;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}