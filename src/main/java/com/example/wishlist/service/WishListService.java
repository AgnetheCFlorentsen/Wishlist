package com.example.wishlist.service;

import com.example.wishlist.model.*;
import com.example.wishlist.repository.WishListRepository;
import com.example.wishlist.repository.WishListRepositoryDB;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {

    private WishListRepository repository;

    private WishListRepositoryDB repositoryDB;
    public WishListService (WishListRepositoryDB repositoryDB) {
        this.repositoryDB = repositoryDB;
    }

    public ArrayList<WishDTO> getWishes(){
        return repositoryDB.getWishes();
    }

    public ArrayList<WishListDTO> getWishLists(){
        return repositoryDB.getWishLists();
    }

    public List<WishList> getAllWishLists(ArrayList<WishListDTO> allWishLists, ArrayList<WishDTO> allWishes){
        return repositoryDB.getAllWishLists(allWishLists, allWishes);
    }

    public void saveAWishList(WishList wishList){
        repositoryDB.saveAWishList(wishList);
    }

    public void saveAWish(Wish wish){
        repositoryDB.saveAWish(wish);
    }

    public List<Wish> getWishes(String wishList) {
       return repositoryDB.getWishes(wishList);
    }

    public String getUsername(){
        return repositoryDB.getUsername();
    }

    public void deleteAWish(String wishList, String wish) {
        repositoryDB.deleteAWish(wishList, wish);
    }

    public void deleteWishlist(String wishList){
        repositoryDB.deleteWishlist(wishList);
    }

    public Wish getOneWish(List<Wish> wishes, String wish){
        return repositoryDB.getOneWish(wishes, wish);
    }

    public void updateAWish(Wish wish){
        repositoryDB.updateAWish(wish);
    }

    public boolean checkUser(User user) {
        return repositoryDB.checkUser(user);
    }

    public void login(User user) {
        repositoryDB.login(user);
    }

    public void createUser(User user) {
        repositoryDB.createUser(user);
    }

    public boolean checkIfReserved(String username, String wishlist, String wish) {
        return repositoryDB.checkIfReserved(username, wishlist, wish);
    }

    public void reserveWish(String username, String wishlist, String wish) {
        repositoryDB.reserveWish(username, wishlist, wish);
    }

   /* public void createWishList(WishList wishList){
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
    }*/
}
