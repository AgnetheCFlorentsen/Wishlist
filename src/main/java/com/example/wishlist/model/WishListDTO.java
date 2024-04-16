package com.example.wishlist.model;

public class WishListDTO {
    private int ID;
    private String name;
    private String description;

    private String username;

    public WishListDTO(int ID, String name, String description, String username) {
        this.ID = ID;
        this.name = name;
        this.description = description;
        this.username = username;
    }

    @Override
    public String toString() {
        return "WishListDTO{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
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
}
