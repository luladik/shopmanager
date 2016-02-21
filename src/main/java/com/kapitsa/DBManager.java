package com.kapitsa;
//Created by Luladik on 2/21/2016.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    private static final String URL = "jdbc:mysql://localhost:3306/food?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    public static Connection getDBConnection() {
        Connection dbConnection;
        try {
            Class.forName(DRIVER);
            System.out.println("Driver connected");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Successfully Connected");
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static void init() throws SQLException {
        String createDb = "CREATE DATABASE IF NOT EXISTS shopmanager";
        String createShops = "CREATE TABLE IF NOT EXISTS Shops (" +
                                "id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                                "Title VARCHAR(30)" +
                                ");";
        String createCategories = "CREATE TABLE IF NOT EXISTS Categories (" +
                                "id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                                "shop_id INT(10) UNSIGNED NOT NULL ," +
                                "Title VARCHAR(20) NOT NULL ," +
                                "FOREIGN KEY(shop_id) REFERENCES Shops(id)" +
                                ");";
        String createItems = "CREATE TABLE IF NOT EXISTS Items (" +
                                "id INT(10) UNSIGNED NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
                                "category_id INT(10) UNSIGNED NOT NULL ," +
                                "Title VARCHAR(30) NOT NULL," +
                                "Price DECIMAL(20) NOT NULL," +
                                "State ENUM('Available', 'Absent', 'Expected')," +
                                "FOREIGN KEY(category_id) REFERENCES Categories(id)" +
                                ");";
        String fillShops = "INSERT IGNORE INTO Shops VALUES (1, 'Food Store'), (2, 'Clothing Store')";
        String fillCategories = "INSERT IGNORE INTO Categories (id, shop_id, Title)  VALUES" +
                                "(85, (SELECT id FROM Shops WHERE Title = 'Food Store'), 'Fruits')," +
                                "(58, (SELECT id FROM Shops WHERE Title = 'Food Store'), 'Vegetables')," +
                                "(96, (SELECT id FROM Shops WHERE Title = 'Food Store'), 'Drinks')," +
                                "(47, (SELECT id FROM Shops WHERE Title = 'Clothing Store'), 'Hats')," +
                                "(81, (SELECT id FROM Shops WHERE Title = 'Clothing Store'), 'Shoes');";
//        String fillItems = "INSERT IGNORE INTO Items VALUES (NULL, (SELECT id FROM Categories WHERE Title = 'Fruits'), 'Green Apple', 10.50, 'Available')" +
//                "ON UP";

        try(final Statement statement = getDBConnection().createStatement()) {
            statement.execute(createDb);
            statement.execute("USE shopmanager;");
            statement.execute(createShops);
            statement.execute(createCategories);
            statement.execute(createItems);
            statement.execute(fillShops);
            statement.execute(fillCategories);
//            statement.execute(fillItems);
            System.out.println("Tables created successfully");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error when creating tables");
        }
    }
}

