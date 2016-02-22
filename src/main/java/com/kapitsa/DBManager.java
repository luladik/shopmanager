package com.kapitsa;
//Created by Luladik on 2/21/2016.

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {

    private static final String URL = "jdbc:mysql://localhost:3306/food?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "test";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    public static Connection getDBConnection() {
        Connection dbConnection;
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return dbConnection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    //Creating and filling database
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
                                "Price DECIMAL(20,2) NOT NULL," +
                                "Status ENUM('Available', 'Absent', 'Expected')," +
                                "FOREIGN KEY(category_id) REFERENCES Categories(id)" +
                                ");";
        String fillShops = "INSERT IGNORE INTO Shops VALUES (1, 'Food Store'), (2, 'Clothing Store')";
        String fillCategories = "INSERT IGNORE INTO Categories (id, shop_id, Title)  VALUES" +
                                "(85, (SELECT id FROM Shops WHERE Title = 'Food Store'), 'Fruits')," +
                                "(58, (SELECT id FROM Shops WHERE Title = 'Food Store'), 'Vegetables')," +
                                "(96, (SELECT id FROM Shops WHERE Title = 'Food Store'), 'Drinks')," +
                                "(47, (SELECT id FROM Shops WHERE Title = 'Clothing Store'), 'Hats')," +
                                "(81, (SELECT id FROM Shops WHERE Title = 'Clothing Store'), 'Shoes');";
        String fillItems = "INSERT IGNORE INTO Items (id, category_id, Title, Price, Status) VALUES" +
                "(47, (SELECT id FROM Categories WHERE Title = 'Hats'), 'Baseball cap', 250.00, 'Available')," +
                "(12, (SELECT id FROM Categories WHERE Title = 'Hats'), 'Brixton', 700.00, 'Expected')," +
                "(23, (SELECT id FROM Categories WHERE Title = 'Hats'), 'Asos', 480.00, 'Available')," +
                "(69, (SELECT id FROM Categories WHERE Title = 'Hats'), 'New Era', 300.00, 'Expected')," +
                "(87, (SELECT id FROM Categories WHERE Title = 'Shoes'), 'Nike Sneakers', 1900.00, 'Expected')," +
                "(57, (SELECT id FROM Categories WHERE Title = 'Shoes'), 'Converse', 2320.00, 'Absent')," +
                "(35, (SELECT id FROM Categories WHERE Title = 'Shoes'), 'Vans', 1800.00, 'Available')," +
                "(85, (SELECT id FROM Categories WHERE Title = 'Shoes'), 'Dino Monti Oxfords', 4200.00, 'Absent')," +
                "(45, (SELECT id FROM Categories WHERE Title = 'Shoes'), 'Nike Run', 1600.00, 'Available')," +
                "(56, (SELECT id FROM Categories WHERE Title = 'Shoes'), 'Adidas ZX750', 2150.00, 'Absent')," +
                "(22, (SELECT id FROM Categories WHERE Title = 'Shoes'), 'Timberland Boots', 3500.00, 'Available')," +
                "(30, (SELECT id FROM Categories WHERE Title = 'Shoes'), 'Keen Boots', 2000.00, 'Available')," +
                "(447, (SELECT id FROM Categories WHERE Title = 'Drinks'), 'Sprite', 9.20, 'Expected')," +
                "(357, (SELECT id FROM Categories WHERE Title = 'Drinks'), 'Apple juice', 11.05, 'Absent')," +
                "(147, (SELECT id FROM Categories WHERE Title = 'Drinks'), 'Jack Daniels', 420.50, 'Absent')," +
                "(333, (SELECT id FROM Categories WHERE Title = 'Drinks'), 'Pepsi', 15.45, 'Available')," +
                "(877, (SELECT id FROM Categories WHERE Title = 'Drinks'), 'Jim Beam', 350.00, 'Available')," +
                "(355, (SELECT id FROM Categories WHERE Title = 'Vegetables'), 'Cucumber', 30.45, 'Absent')," +
                "(782, (SELECT id FROM Categories WHERE Title = 'Vegetables'), 'Tomato', 10.50, 'Expected')," +
                "(855, (SELECT id FROM Categories WHERE Title = 'Vegetables'), 'Green Apple', 10.50, 'Available')," +
                "(186, (SELECT id FROM Categories WHERE Title = 'Vegetables'), 'Potato', 5.50, 'Expected')," +
                "(275, (SELECT id FROM Categories WHERE Title = 'Fruits'), 'Grapefruit', 20.15, 'Available')," +
                "(568, (SELECT id FROM Categories WHERE Title = 'Fruits'), 'Orange', 13.55, 'Absent')," +
                "(963, (SELECT id FROM Categories WHERE Title = 'Fruits'), 'Green Apple', 10.50, 'Available')," +
                "(874, (SELECT id FROM Categories WHERE Title = 'Fruits'), 'Banana', 35.00, 'Expected');";

        try(final Statement statement = getDBConnection().createStatement()) {
            statement.execute(createDb);
            statement.execute("USE shopmanager;");
            statement.execute(createShops);
            statement.execute(createCategories);
            statement.execute(createItems);
            statement.execute(fillShops);
            statement.execute(fillCategories);
            statement.execute(fillItems);
            System.out.println("Tables created successfully");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error when creating tables");
        }
    }
}

