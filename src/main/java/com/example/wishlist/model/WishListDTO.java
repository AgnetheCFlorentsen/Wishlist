package com.example.wishlist.model;

public class WishListDTO {
    private int ID;
    private String name;
    private String description;

    public WishListDTO(int ID, String name, String description) {
        this.ID = ID;
        this.name = name;
        this.description = description;
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
