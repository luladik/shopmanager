package com.kapitsa;
//Created by Luladik on 2/21/2016.

import com.kapitsa.shop.Item;
import com.kapitsa.shop.Shop;
import com.kapitsa.shop.ShopFactory;
import com.kapitsa.shop.StatusEnum;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    private static boolean foodShopIsActive = true;
    private static boolean clothingShopIsActive = true;

    public static void main(String[] args) throws SQLException {

        final Connection connection = DBManager.getDBConnection();
        final Statement statement = connection.createStatement();

        DBManager.init();

        final Shop foodShop = ShopFactory.getShop("Food");
        final Shop clothingShop = ShopFactory.getShop("Clothing");

        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(10000);
                    foodShop.addItem(new Item("Coconut", 60.00, StatusEnum.Available), "Fruits");
                    foodShop.addItem(new Item("Pineapple", 75.50, StatusEnum.Expected), "Fruits");
                    foodShop.addItem(new Item("Milk", 22.70, StatusEnum.Available), "Drinks");
                    foodShop.addItem(new Item("Carrot", 9.70, StatusEnum.Absent), "Vegetables");
                    statement.executeUpdate("UPDATE shopmanager.Items SET State = 'Absent' WHERE category_id = " +
                            "(SELECT id FROM Categories WHERE Title = 'Drinks')");

                    //"В какой-то из категорий изменить статусы всех товаров на «Absent»..."
                    ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Items WHERE category_id <>\n" +
                            "(SELECT id FROM Categories WHERE Title = 'Drinks')");

                    //"...половине товаров, из оставшихся категорий, изменить статус на «Expected»."
                    //Using limit is a much better way than random in this case
                    int count = -2;
                    while (resultSet.next())
                        count = resultSet.getInt(1)/2;
                    if (count == -2)
                        System.out.println("Something went wrong! (count of untouched rows)");
                    statement.executeUpdate("UPDATE shopmanager.Items SET Items.State = 'Expected' WHERE category_id <>" +
                            "(SELECT id FROM Categories WHERE Title = 'Drinks')" +
                            "ORDER BY id LIMIT " + count);

                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                foodShopIsActive = false;
                if (!clothingShopIsActive) {
                    System.out.println("Everything went well. SUCCESS!");
                }
            }
        }).start();


        new Thread(new Runnable() {
            public void run() {
                try {
                    clothingShop.addItem(new Item("Puma Sneakers", 1550.00, StatusEnum.Expected ), "Shoes");
                    clothingShop.addItem(new Item("Nike Free Run", 2180.00, StatusEnum.Absent), "Shoes");
                    clothingShop.addItem(new Item("Western Leather Hat", 540, StatusEnum.Available  ), "Hats");
                    statement.executeUpdate("UPDATE shopmanager.Items SET State = 'Absent' WHERE category_id = " +
                        "(SELECT id FROM Categories WHERE Title = 'Shoes')");

                    //"В какой-то из категорий изменить статусы всех товаров на «Absent»..."
                    ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM Items WHERE category_id <>\n" +
                            "(SELECT id FROM Categories WHERE Title = 'Shoes')");

                    //"...половине товаров, из оставшихся категорий, изменить статус на «Expected»."
                    //Using limit is a much better way than random in this case
                    int count = -2;
                    while (resultSet.next())
                        count = resultSet.getInt(1)/2;
                    if (count == -2)
                        System.out.println("Something went wrong! (count of untouched rows)");
                    statement.executeUpdate("UPDATE shopmanager.Items SET Items.State = 'Expected' WHERE category_id <>" +
                            "(SELECT id FROM Categories WHERE Title = 'Shoes')" +
                            "ORDER BY id LIMIT " + count);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                clothingShopIsActive = false;
                if (!foodShopIsActive)
                    System.out.println("Everything went well. SUCCESS!");
            }
        }).start();

    }
}
