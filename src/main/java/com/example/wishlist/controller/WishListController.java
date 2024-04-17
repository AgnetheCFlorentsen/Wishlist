package com.example.wishlist.controller;

import com.example.wishlist.model.*;
import com.example.wishlist.repository.WishListRepositoryDB;
import com.example.wishlist.service.WishListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path="wishwonder")
public class WishListController {


    private WishListService wishListService;

    public WishListController(WishListService wishListService){
        this.wishListService = wishListService;
    }

    @GetMapping("")
    public String getFrontPage(){
        return "index";
    }

    @GetMapping("/index")
    public String getFrontPageLoggedIn(){
        return "index2";
    }

    @GetMapping("/lists")
    public String getWishLists(Model model){
        //List<WishList> wishLists = wishListService.getWishLists();
        ArrayList<WishDTO> wish = wishListService.getWishes();
        ArrayList<WishListDTO> wishlist= wishListService.getWishLists();
        List<WishList> wishLists = wishListService.getAllWishLists(wishlist, wish);
        model.addAttribute("wishlists", wishLists);
       // System.out.println(wdb.getWishes());

        return "SeeLists";
    }

    @GetMapping("/createlist")
    public String createWishList(Model model){
        WishList wishList = new WishList();
        model.addAttribute("wishlist", wishList);
        return "createWishList";
    }

    @PostMapping("/savelist")
    public String createWishList(@ModelAttribute WishList wishlist){
       // wishListService.createWishList(wishlist);
        wishListService.saveAWishList(wishlist);
        return "redirect:/wishwonder";

    }

    @GetMapping("/createwish")
    public String addWish(Model model){
        model.addAttribute("wish", new Wish());
        ArrayList<WishDTO> wish = wishListService.getWishes();
        ArrayList<WishListDTO> wishlist= wishListService.getWishLists();
        List<WishList> wishLists = wishListService.getAllWishLists(wishlist, wish);
        model.addAttribute("wishlists", wishLists);
        return "AddWish";
    }

    @PostMapping("/savewish")
    public String createWish(@ModelAttribute Wish wish){
        //wishListService.addWish(wish);
        wishListService.saveAWish(wish);
      //  WishList wishlist = wishListService.getAWishList(wish.getWishList());
        return "redirect:/wishwonder/lists";
    }

    @GetMapping("/{name}/list")
    public String showAll(@PathVariable String name, Model model) {
        //List<Wish> wishes = wishListService.getWishes(name);
        List<Wish> wishes = wishListService.getWishes(name);
        model.addAttribute("username", wishListService.getUsername());
        model.addAttribute("wishes", wishes);
        model.addAttribute("wishlist", name);
        return "Show_Wishes";
    }

    @GetMapping(path = "/{wishlist}/{wish}/delete")
    public String deleteAWish (@PathVariable String wishlist, @PathVariable String wish, Model model){
        //WishList wishList1 = wishListService.getAWishList(wishlist);
        //Wish wishToDelete = wishListService.getAWish(wish, wishlist);
        //wishListService.deleteAWish(wishToDelete, wishList1);
        wishListService.deleteAWish(wishlist, wish);
        return "redirect:/wishwonder/{wishlist}/list";
    }

    @GetMapping(path = "/{wishlist}/delete")
    public String deleteAWishList (@PathVariable String wishlist, Model model){
        //WishList wishListToDelete = wishListService.getAWishList(wishlist);
       // wishListService.deleteAWishList(wishListToDelete);
        wishListService.deleteWishlist(wishlist);
        return "redirect:/wishwonder/lists";
    }
    @GetMapping(path="/{wishlist}/{wish}/edit")
    public String updateWish (@PathVariable String wish, @PathVariable String wishlist, Model model){
       // Wish wishToUpdate = wishListService.getAWish(wish, wishlist);
        List<Wish> wishes = wishListService.getWishes(wishlist);
        Wish wishToUpdate = wishListService.getOneWish(wishes, wish);
        model.addAttribute("wish", wishToUpdate);
        return "UpdateWish";
    }

    @PostMapping(path="/update")
    public String updateWish(Wish wish){
       // wishListService.updateAWish(wish);
        wishListService.updateAWish(wish);
        return "redirect:/wishwonder/lists";
    }

    @GetMapping(path="/login")
    public String login(Model model){
        model.addAttribute("user", new User());
        return "Login";
    }

    @PostMapping("/postlogin")
    public String login(@ModelAttribute User user, Model model) {
        if (!wishListService.checkUser(user)){
            model.addAttribute("loginError", "Username or password is incorrect");
            return "Login";}
        else {
        wishListService.login(user);
        return "redirect:/wishwonder/index";}
    }

    @GetMapping(path="/createuser")
    public String createUser(Model model){
        model.addAttribute("user", new User());
        return "SignUp";
    }

    @PostMapping("/saveuser")
    public String createUser(@ModelAttribute User user) {
        wishListService.createUser(user);
        return "redirect:/wishwonder/index";
    }

    @GetMapping("/{username}/{wishlist}")
    public String shareList(@PathVariable String username, @PathVariable String wishlist, Model model){
        List<Wish> wishes = wishListService.getWishes(wishlist);
        model.addAttribute("wishes", wishes);
        return "Sharewishlist";
    }

    @GetMapping("/{username}/{wishlist}/{wish}")
    public String reserveWish(@PathVariable String username, @PathVariable String wishlist, @PathVariable String wish, Model model){
    //public String reserveWish(@PathVariable String username, @PathVariable String wishlist, @PathVariable String wish, Model model){
        List<Wish> wishes = wishListService.getWishes(wishlist);
        model.addAttribute("wishes", wishes);
        if (wishListService.getUsername().equals("standard")){
            model.addAttribute("userError", "You have to be logged in to reserve an item");
            return "Sharewishlist";}
        else if (wishListService.checkIfReserved(username, wishlist, wish)) {
            model.addAttribute("reserveError", "This item is already reserved");
        return "Sharewishlist";}
        else {
            wishListService.reserveWish(username, wishlist, wish);
            return "redirect:/wishwonder/{username}/{wishlist}";
        }



        }
    }




