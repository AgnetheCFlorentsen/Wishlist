package com.example.wishlist.model;

public class Wish {

    private String name;

    private String description;

    private double price;

    private String link;

    private int amount;
    private String store;

    private String wishList;

    public Wish (String name, String description, double price, String store, String wishList){
        this.amount = 1;
        this.name = name;
        this.description = description;
        this.price = price;
        this.store=store;
        this.wishList=wishList;

    }

    public Wish(){
        this.amount = 1;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
       this.link = link;

       }



    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getWishList(){
        return wishList;
    }

    public void setWishList(String wishList){
        this.wishList = wishList;
    }
}
