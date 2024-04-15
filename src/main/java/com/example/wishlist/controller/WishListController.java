package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishDTO;
import com.example.wishlist.model.WishList;
import com.example.wishlist.model.WishListDTO;
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
    WishListRepositoryDB wdb = new WishListRepositoryDB();

    public WishListController(WishListService wishListService){
        this.wishListService = wishListService;
    }

    @GetMapping("")
    public String getFrontPage(){
        return "index";
    }

    @GetMapping("/lists")
    public String getWishLists(Model model){
        //List<WishList> wishLists = wishListService.getWishLists();
        ArrayList<WishDTO> wish = wdb.getWishes();
        ArrayList<WishListDTO> wishlist= wdb.getWishLists();
        List<WishList> wishLists = wdb.getAllWishLists(wishlist, wish);
        model.addAttribute("wishlists", wishLists);
       // System.out.println(wdb.getWishes());

        return "SeeLists";
    }

    @GetMapping("/createlist")
    public String createWishList(Model model){
        model.addAttribute("wishlist", new WishList());
        return "createWishList";
    }

    @PostMapping("/savelist")
    public String createWishList(@ModelAttribute WishList wishlist){
       // wishListService.createWishList(wishlist);
        wdb.saveAWishList(wishlist);
        return "redirect:/wishwonder";

    }

    @GetMapping("/createwish")
    public String addWish(Model model){
        model.addAttribute("wish", new Wish());
        ArrayList<WishDTO> wish = wdb.getWishes();
        ArrayList<WishListDTO> wishlist= wdb.getWishLists();
        List<WishList> wishLists = wdb.getAllWishLists(wishlist, wish);
        model.addAttribute("wishlists", wishLists);
        return "AddWish";
    }

    @PostMapping("/savewish")
    public String createWish(@ModelAttribute Wish wish){
        //wishListService.addWish(wish);
        wdb.saveAWish(wish);
      //  WishList wishlist = wishListService.getAWishList(wish.getWishList());
        return "redirect:/wishwonder/lists";
    }

    @GetMapping("/{name}/list")
    public String showAll(@PathVariable String name, Model model) {
        //List<Wish> wishes = wishListService.getWishes(name);
        List<Wish> wishes = wdb.getWishes(name);
        model.addAttribute("wishes", wishes);
        return "Show_Wishes";
    }

    @GetMapping(path = "/{wishlist}/{wish}/delete")
    public String deleteAWish (@PathVariable String wishlist, @PathVariable String wish, Model model){
        //WishList wishList1 = wishListService.getAWishList(wishlist);
        //Wish wishToDelete = wishListService.getAWish(wish, wishlist);
        //wishListService.deleteAWish(wishToDelete, wishList1);
        System.out.println(wishlist);
        wdb.deleteAWish(wishlist, wish);
        return "redirect:/wishwonder/{wishlist}/list";
    }

    @GetMapping(path = "/{wishlist}/delete")
    public String deleteAWishList (@PathVariable String wishlist, Model model){
        //WishList wishListToDelete = wishListService.getAWishList(wishlist);
       // wishListService.deleteAWishList(wishListToDelete);
        wdb.deleteWishlist(wishlist);
        return "redirect:/wishwonder/lists";
    }
    @GetMapping(path="/{wishlist}/{wish}/edit")
    public String updateWish (@PathVariable String wish, @PathVariable String wishlist, Model model){
       // Wish wishToUpdate = wishListService.getAWish(wish, wishlist);
        List<Wish> wishes = wdb.getWishes(wishlist);
        Wish wishToUpdate = wdb.getOneWish(wishes, wish);
        model.addAttribute("wish", wishToUpdate);
        return "UpdateWish";
    }

    @PostMapping(path="/update")
    public String updateWish(Wish wish){
       // wishListService.updateAWish(wish);
        wdb.updateAWish(wish);
        return "redirect:/wishwonder/lists";
    }

    /*@ExceptionHandler(Exception.class)
    public String handleError(Model model, Exception exception){
        model.addAttribute("message", "hej");
        return "errorpage";
    }*/

}
