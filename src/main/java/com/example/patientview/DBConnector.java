package com.example.patientview;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

    //This small method will set the connection between the DB and the Java programm using JDBC

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/conconvhealth","ADMIN","123");
        return connection;
    }
}