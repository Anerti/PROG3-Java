package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    public Connection getConnection() throws SQLException {
        final String url = System.getenv("JDBC_URL");
        final String user =  System.getenv("USER");
        final String password = System.getenv("PASSWORD");
        return DriverManager.getConnection(url, user, password);
    }

}
