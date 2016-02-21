package com.kapitsa;
//Created by Luladik on 2/21/2016.

import com.kapitsa.shop.Shop;
import com.kapitsa.shop.ShopFactory;

import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws SQLException {

        DBManager.init();

        Shop foodShop = ShopFactory.getShop("Food");
        Shop clothingShop = ShopFactory.getShop("Clothing");





    }
}
