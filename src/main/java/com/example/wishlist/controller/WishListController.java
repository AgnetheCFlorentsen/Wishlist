package com.example.wishlist.controller;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishList;
import com.example.wishlist.service.WishListService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/lists")
    public String getWishLists(Model model){
        List<WishList> wishLists = wishListService.getWishLists();
        model.addAttribute("wishlists", wishLists);
        return "SeeLists";
    }

    @GetMapping("/createlist")
    public String createWishList(Model model){
        model.addAttribute("wishlist", new WishList());
        return "CreateWishList";
    }

    @PostMapping("/savelist")
    public String createWishList(@ModelAttribute WishList wishlist){
        wishListService.createWishList(wishlist);
        return "redirect:/wishwonder";

    }

    @GetMapping("/createwish")
    public String addWish(Model model){
        model.addAttribute("wish", new Wish());
        model.addAttribute("wishlists", wishListService.getWishLists());
        return "AddWish";
    }

    @PostMapping("/savewish")
    public String createWish(@ModelAttribute Wish wish){
        wishListService.addWish(wish);
        return "redirect:/wishwonder";
    }

    @GetMapping("/{name}/list")
    public String showAll(@PathVariable String name, Model model) {
        List<Wish> wishes = wishListService.getWishes(name);
        System.out.println(name);
        model.addAttribute("wishes", wishes);
        return "Show_Wishes";
    }

    @GetMapping(path = "/{wishlist}/{wish}/delete")
    public String deleteAWish (@PathVariable String wishlist, @PathVariable String wish, Model model){
       // wishListService.deleteAWish(wish, wishlist);
        return "redirect:/wishwonder";
    }

    @GetMapping(path = "/{wishlist}/delete")
    public String deleteAWishList (@PathVariable String wishlist, Model model){
        WishList wishListToDelete = wishListService.getAWishList(wishlist);
        wishListService.deleteAWishList(wishListToDelete);
        return "redirect:/wishwonder";
    }
}
