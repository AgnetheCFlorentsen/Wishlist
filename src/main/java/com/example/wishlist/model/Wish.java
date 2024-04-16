package com.example.wishlist.model;

public class Wish {

    private String name;

    private String description;

    private double price;

    private String link;

    private int amount;
    private String store;

    private String wishList;

    public String reserved;

    public Wish (String name, String description, double price, String link, int amount, String store, String reserved){
        this.amount = 1;
        this.name = name;
        this.description = description;
        this.price = price;
        this.store=store;
        this.link =link;
        this.reserved = reserved;

    }

    public Wish(){
        this.amount = 1;
        this.reserved = "Not reserved";
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

    public String getReserved(){
        return reserved;
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

    public void setIsReserved(String reserved){
        this.reserved=reserved;

    }

    public void setWishList(String wishList){
        this.wishList = wishList;
    }

}
