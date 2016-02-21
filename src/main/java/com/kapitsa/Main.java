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

        Shop shop = ShopFactory.getShop("Clothing");
        if (shop != null) {
            shop.addItem(new Item("Coca-Cola", 20.50, StatusEnum.Expected), "Drinks");
            shop.addItem(new Item("Apple", 30.2, StatusEnum.Absent), "Fruits");
        }




    }
}
