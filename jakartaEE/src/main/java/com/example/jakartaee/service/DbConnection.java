package com.example.jakartaee.service;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbConnection {

    private static Connection CONNECTION;

    public static synchronized Connection getInstance() {
        if (CONNECTION == null) {
            CONNECTION = createConnection();
        }
        return CONNECTION;
    }

    private DbConnection()  {}

    private static Connection createConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/scheduler",
                    "postgres", "root");
            if (!connection.isClosed()) {
                return connection;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
