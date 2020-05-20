package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagement {
    private static ConnectionManagement instance = null;
    private static final String URL = Constants.CONNECTION_STRING;
    private Connection connection = null;

    private ConnectionManagement() {
    }

    public static ConnectionManagement getInstance() {
        if (instance == null)
            instance = new ConnectionManagement();
        return instance;
    }

    public boolean openConnection() {
        try {
            connection = DriverManager.getConnection(URL);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Connection getConnection() {
        if (connection == null) {
            if (openConnection()) {
                System.out.println("Connection opened!");
                return connection;
            } else
                return null;
        }
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
            connection = null;
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
