package com.example.wishlist.model;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service

public class WishList {

    private String name;
    private String description;

    private List<Wish>wishes;

    public WishList (String name, List<Wish>wishes) {
        this.name = name;
        this.wishes = new ArrayList<>();
    }
    public WishList(){
        this.wishes = new ArrayList<>();
    }

    public WishList(String name, String description) {
        this.name = name;
        this.description = description;
        this.wishes = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Wish> getWishes(){
        return wishes;
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



    public void addWish(Wish wish){
        wishes.add(wish);
    }

    public void deleteWish(Wish wish){
       wishes.remove(wish);
    }

}
