package com.kapitsa.shop;
//Created by Luladik on 2/21/2016.

public class ShopFactory {

    public static final String food = null;

    public static Shop getShop(String type) {
        if (type.equals("Food")) {
            return FoodShop.getInstance();
        } else if (type.equals("Clothing")) {
            return ClothingShop.getInstance();
        } return null; //compiler will be happy
    }

    //Написать фабричный метод, который будет возвращать классы магазинов.
    public static Class<? extends Shop> getShopClass(String type) {
        if (type.equals("Food")) {
            return FoodShop.class;
        } else if (type.equals("Clothing")) {
            return ClothingShop.class;
        } return null;
    }
}
