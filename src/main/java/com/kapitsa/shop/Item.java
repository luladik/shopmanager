package com.kapitsa.shop;
//Created by Luladik on 2/21/2016.

public class Item {

    private int id;
    private String title;
    private double price;
    private StatusEnum status;

    public Item() {

    }

    public Item(String title, double price, StatusEnum status) {
        this.title = title;
        this.price = price;
        this.status = status;
    }

    public String getStatus() {
        return String.valueOf(status);
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
