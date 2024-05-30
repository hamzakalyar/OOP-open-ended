package com.labtasks.task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/hotelMS";
    private static final String USER = "root"; // replace with your MySQL username
    private static final String PASSWORD = "asd123";

    public Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
