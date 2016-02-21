package com.kapitsa.shop;
//Created by Luladik on 2/21/2016.

class ClothingShop extends Shop {

    private ClothingShop() {super();}

    protected static synchronized Shop getInstance() {

        if (instance == null) {
            instance = new ClothingShop();
        }
        return instance;
    }

}
