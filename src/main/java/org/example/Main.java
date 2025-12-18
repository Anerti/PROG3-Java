package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBConnection db = new DBConnection();
        System.out.println(db.getConnection());
    }
}