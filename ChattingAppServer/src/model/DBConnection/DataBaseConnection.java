package model.DBConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author KHoloud
 */
public class DataBaseConnection {

    private Connection connection;
    public DataBaseConnection() throws ClassNotFoundException {
   
        
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mydb", "root", "root");
        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }
    
    public Connection getConnection(){
        return connection;
    }

}
