package com.example.wishlist.model;

public class WishDTO {
    private int ID;
    private String name;
    private String description;
    private double price;
    private String link;
    private int amount;
    private String store;

    private int wishlist_ID;

    private String reserved;


    public WishDTO(int ID, String name, String description, double price, String link, int amount, String store, int wishlist_ID, String reserved) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.link = link;
        this.amount = amount;
        this.store = store;
        this.wishlist_ID = wishlist_ID;
        this.reserved = reserved;
    }

    @Override
    public String toString() {
        return "WishDTO{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", link='" + link + '\'' +
                ", amount=" + amount +
                ", store='" + store + '\'' +
                '}';
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public String getLink() {
        return link;
    }

    public int getAmount() {
        return amount;
    }

    public String getStore() {
        return store;
    }

    public String getReserved(){
        return reserved;
    }
}
