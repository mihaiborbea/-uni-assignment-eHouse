package utils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtils {

    public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException {

        // Note: Change the connection parameters accordingly.
        String hostName = "localhost";
        String dbName = "ehouse";
        String userName = "root";
        String password = "password";

        Class.forName("com.mysql.jdbc.Driver");
        // URL Connection for MySQL
        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        Connection conn = DriverManager.getConnection(connectionURL, userName, password);
        return conn;
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {

        return getMySQLConnection();
    }

    public static void closeQuietly(Connection conn) {
        try {
            conn.close();
        } catch (Exception e) {
            System.out.println("Connection close error!");
        }
    }

    public static void rollbackQuietly(Connection conn) {
        try {
            conn.rollback();
        } catch (Exception e) {
            System.out.println("Connection rollback error!");
        }
    }

}
