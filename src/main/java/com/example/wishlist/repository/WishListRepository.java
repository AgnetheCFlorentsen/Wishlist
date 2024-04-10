package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository

public class WishListRepository {

    private List<WishList> wishlistlist =new ArrayList<WishList>();

    public void createWishList(WishList wishList){
        wishlistlist.add(wishList);
    }

    public List<WishList> getWishLists(){
        /*List<String> wishlistlistnames = new ArrayList<String>();
        for (WishList w : wishlistlist){
            wishlistlistnames.add(w.getName());
        }*/
        return wishlistlist;
    }

    public void addWish(Wish wish){
        for (WishList w : wishlistlist){
            if (wish.getWishList()==w.getName()){
                w.addWish(wish);
            }
        }
        }




