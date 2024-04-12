package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
@Repository

public class WishListRepository {

    private String wishListWithWishToUpdate ="";

    private List<WishList> wishlistlist = new ArrayList<WishList>();

    public WishListRepository() {
        WishList wishList = new WishList("Testerlist", List.of());
        wishlistlist.add(wishList);
    }

    public void createWishList(WishList wishList) {
        wishlistlist.add(wishList);
    }

    public List<WishList> getWishLists() {
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
                System.out.println(w.getDescription());
                return w.getWishes();

            }
        }
        return null;
    }

   public Wish getAWish(String wish, String wishList) {
        wishListWithWishToUpdate = wishList;
        for (WishList w : wishlistlist) {
            if (w.getName().equalsIgnoreCase(wishList)) {
                for (Wish wi : w.getWishes()) {
                    if (wi.getName().equalsIgnoreCase(wish)){
                        return wi;
                    }

                }
            }
        }
        return null;
    }

    public void deleteAWish(Wish wish, WishList wishList){
        wishList.deleteWish(wish);

    }

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

    public void updateWish(Wish wish) {
        for (WishList w : wishlistlist) {
            if (w.getName().equalsIgnoreCase(wishListWithWishToUpdate)) {
                for (Wish wi : w.getWishes()) {
                    if (wi.getName().equalsIgnoreCase(wish.getName())) {
                        wi.setDescription(wish.getDescription());
                        System.out.println(wi.getDescription());
                        wi.setAmount(wish.getAmount());
                        wi.setStore(wish.getStore());
                        wi.setLink(wish.getLink());
                        wi.setPrice(wish.getPrice());
                    }
                }
            }
        }
    }
}



