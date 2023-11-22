package com.cultura;
import java.sql.*;

public class CreateDB {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String DB_NAME = "cultura_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "4vPRBRYJU9.";

    public static void main(String[] args) {
        createDB();
        createUsersTable();
    }

    private static boolean createDB(){

        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
            statement = connection.createStatement();
            String sql = "CREATE DATABASE cultura_db";
            statement.executeUpdate(sql);
            System.out.println("Database created successfully");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                // Closing resources
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private static boolean createUsersTable(){

        Connection connection = null;
        Statement statement = null;

        try {
            connection = DriverManager.getConnection(JDBC_URL+DB_NAME, DB_USER, DB_PASSWORD);

            statement = connection.createStatement();
            String createTableSQL = "CREATE TABLE user " +
                    "(username VARCHAR(255) NOT NULL PRIMARY KEY, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "name VARCHAR(255), " +
                    "email VARCHAR(255))"
                    ;
            statement.executeUpdate(createTableSQL);

            System.out.println("Table 'user' created successfully");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}


