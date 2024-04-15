package com.example.wishlist.repository;

import com.example.wishlist.model.Wish;
import com.example.wishlist.model.WishDTO;
import com.example.wishlist.model.WishList;
import com.example.wishlist.model.WishListDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishListRepositoryDB {
    @Value("jdbc:mysql://localhost:3306/WishList")
    private String db_url;
    @Value("root")
    private String username;
    @Value("bNtV!AgN7izA!Kw")
    private String pwd;


    public ArrayList<WishDTO> getWishes() {
        ArrayList<WishDTO> allWishes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/WishList", "root", "bNtV!AgN7izA!Kw")) {
            Statement stmt = connection.createStatement();
            String SQL = "SELECT * FROM wishes";
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                allWishes.add(new WishDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getInt(6), rs.getString(7)));
            }
        }
        catch (SQLException e){
            System.out.println("Cannot connect to database");
            e.printStackTrace();}
        return allWishes;
    }


    public void saveAWish(Wish wish) {
        String name = wish.getName();
        String description = wish.getDescription();
        Double price = wish.getPrice();
        String link = wish.getLink();
        int amount = wish.getAmount();
        String store = wish.getStore();


        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/WishList", "root", "bNtV!AgN7izA!Kw")) {
            String SQL = "SELECT ID FROM WISHLISTS WHERE NAME=?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, wish.getWishList());
            ResultSet rs = ps.executeQuery();
            int wishListID = 0;
            while (rs.next()) {
                wishListID = rs.getInt(1);
            }
            System.out.println(wishListID);

            String SQL1 = "insert into wishes (name, description, price, link, amount, store, wishlist_ID) values (?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps1 = connection.prepareStatement(SQL1);
            //ps.setInt(1, id);
            ps1.setString(1, name);
            ps1.setString(2, description);
            ps1.setDouble(3, price);
            ps1.setString(4, link);
            ps1.setInt(5, amount);
            ps1.setString(6, store);
            ps1.setInt(7, wishListID);

            int rs1 = ps1.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<WishListDTO> getWishLists() {
        ArrayList<WishListDTO> allWishLists = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/WishList", "root", "bNtV!AgN7izA!Kw")) {
            Statement stmt = connection.createStatement();
            String SQL = "SELECT * FROM WishLists";
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                allWishLists.add(new WishListDTO(rs.getInt(1), rs.getString(2), rs.getString(3)));
            }
        }
        catch (SQLException e){
            System.out.println("Cannot connect to database");
            e.printStackTrace();}
        return allWishLists;
    }

    public List<WishList> getAllWishLists (ArrayList<WishListDTO> allWishLists, ArrayList<WishDTO> allWishes) {
        List<WishList> wishListList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/WishList", "root", "bNtV!AgN7izA!Kw")) {
            for (WishListDTO w : allWishLists) {
                WishList wishList = new WishList(w.getName(), w.getDescription());

                int id = w.getID();
                String SQL = "SELECT * FROM WISHLIST_WISH WHERE WISHLIST_ID=?;";
                PreparedStatement ps = connection.prepareStatement(SQL);
                //ps.setInt(1, id);
                ps.setInt(1, 1);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    for (WishDTO wi : allWishes) {
                        if (rs.getInt(1) == wi.getID()) {
                            Wish wish = new Wish(wi.getName(), wi.getDescription(), wi.getPrice(), wi.getLink(), wi.getAmount(), wi.getStore());

                            wishList.addWish(wish);
                        }

                    }
                }
                wishListList.add(wishList);
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return wishListList;

    }
    }