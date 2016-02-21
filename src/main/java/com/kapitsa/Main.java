package com.kapitsa;
//Created by Luladik on 2/21/2016.

import com.kapitsa.shop.Item;
import com.kapitsa.shop.Shop;
import com.kapitsa.shop.ShopFactory;
import com.kapitsa.shop.StatusEnum;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        DBManager.init();

        final Shop foodShop = ShopFactory.getShop("Food");
        final Shop clothingShop = ShopFactory.getShop("Clothing");

        new Thread(new Runnable() {
            public void run() {
                try {
                    foodShop.addItem(new Item("Coconut", 60.00, StatusEnum.Available), "Fruits");
                    foodShop.addItem(new Item("Pineapple", 75.50, StatusEnum.Expected), "Fruits");
                    foodShop.addItem(new Item("Milk", 22.70, StatusEnum.Available), "Drinks");
                    foodShop.addItem(new Item("Carrot", 9.70, StatusEnum.Absent), "Vegetables");
//                    for (foodShop.getItemByTitle())
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(new Runnable() {
            public void run() {
                try {
                    clothingShop.addItem(new Item("Puma Sneakers", 1550.00, StatusEnum.Expected ), "Shoes");
                    clothingShop.addItem(new Item("Nike Free Run", 2180.00, StatusEnum.Absent), "Shoes");
                    clothingShop.addItem(new Item("Western Leather Hat", 540, StatusEnum.Available  ), "Hats");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        System.out.println("FINISH >>> EVERYTHING WORKS FINE");

    }
}
