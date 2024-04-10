package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository

public class WishListRepository {

    private List<WishList> wishlistlist = new ArrayList<WishList>();

    public WishListRepository() {
        WishList wishList = new WishList("Testerlist", List.of());
        wishlistlist.add(wishList);
    }

    public void createWishList(WishList wishList) {
        wishlistlist.add(wishList);
    }

    public List<WishList> getWishLists() {
        /*List<String> wishlistlistnames = new ArrayList<String>();
        for (WishList w : wishlistlist){
            wishlistlistnames.add(w.getName());
        }*/
        return wishlistlist;
    }

    public void addWish(Wish wish) {
        for (WishList w : wishlistlist) {
            if (w.getName().equalsIgnoreCase(wish.getWishList())) {
                w.addWish(wish);
            }
        }
    }

    public List<Wish> getWishes(String wishList) {
        for (WishList w : wishlistlist) {
            if (w.getName().equalsIgnoreCase(wishList)) {
                return w.getWishes();

            }
        }
        return null;
    }

   /* public Wish getAWish(String wish, String wishList) {
        for (WishList w : wishlistlist) {
            if (w.getName().equalsIgnoreCase(wishList)) {
                for (Wish wi : w.getWishes()) {
                    if (wi.getName().equalsIgnoreCase(wish)){

                    }

                }
            }
        }
    }*/

    public WishList getAWishList(String wishList) {
        for (WishList w : wishlistlist) {
            if (w.getName().equalsIgnoreCase(wishList)) {
                return w;
            }
        }
        return null;
    }

    public void deleteAWishList(WishList wishList){
        wishlistlist.remove(wishList);
    }
}



