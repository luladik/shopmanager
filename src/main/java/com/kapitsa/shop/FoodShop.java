package com.kapitsa.shop;
//Created by Luladik on 2/21/2016.

class FoodShop extends Shop {

    private FoodShop() {super();}

    protected static synchronized Shop getInstance() {

        if (instance == null) {
            instance = new FoodShop();
        }
        return instance;
    }

    public void run() {
        System.out.println("Running thread " + thread.getName() + "... in Food Shop");

    }




}
