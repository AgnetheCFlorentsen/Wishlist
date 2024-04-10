package com.example.wishlist.service;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;
import com.example.wishlist.repository.WishListRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishListService {

    private WishListRepository repository;
    public WishListService (WishListRepository repository) {
        this.repository = repository;
    }

    public void createWishList(WishList wishList){
        repository.createWishList(wishList);
    }

    public List<WishList> getWishLists(){
       return repository.getWishLists();
    }

    public void addWish(Wish wish){
        repository.addWish(wish);
    }

    public List<Wish> getWishes(String wishList){
        return repository.getWishes(wishList);
    }

    public Wish getAWish(String wish, String wishList){
        return repository.getAWish(wish, wishList);
    }

    public void deleteAWish(Wish wish, WishList wishList){
        repository.deleteAWish(wish, wishList);
    }

    public WishList getAWishList(String wishList){
        return repository.getAWishList(wishList);
    }
    public void deleteAWishList (WishList wishList) {
        repository.deleteAWishList(wishList);
    }

    public void updateAWish(Wish wish){
        repository.updateWish(wish);
    }
}
