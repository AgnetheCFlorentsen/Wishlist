package com.example.wishlist.repository;

import com.example.wishlist.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class WishListRepositoryDB {
    @Value("${spring.datasource.url}")
    private String db_url;
    @Value("${spring.datasource.username}")
    private String SQLusername;
    @Value("${spring.datasource.password}")
    private String pwd;

    private String wishListWithWishToUpdate = "";
    private User loggedInUser = new User("standard");

    public String getUsername() {
        return loggedInUser.getUsername();
    }

    public ArrayList<WishDTO> getWishes() {
        ArrayList<WishDTO> allWishes = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(db_url, SQLusername, pwd)) {
            Statement stmt = connection.createStatement();
            String SQL = "SELECT * FROM wishes";
            ResultSet rs = stmt.executeQuery(SQL);
            while (rs.next()) {
                allWishes.add(new WishDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getDouble(4), rs.getString(5), rs.getInt(6), rs.getString(7), rs.getInt(8), rs.getString(9)));
            }
        } catch (SQLException e) {
            System.out.println("Cannot connect to database");
            e.printStackTrace();
        }
        return allWishes;
    }


    public void saveAWish(Wish wish) {
        String name = wish.getName();
        String description = wish.getDescription();
        Double price = wish.getPrice();
        String link = wish.getLink();
        int amount = wish.getAmount();
        String store = wish.getStore();
        String reserved = wish.getReserved();
        String reservedBy = "not reserved";


        try (Connection connection = DriverManager.getConnection(db_url, SQLusername, pwd)) {
            String SQL = "SELECT ID FROM WISHLISTS WHERE NAME=?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, wish.getWishList());
            ResultSet rs = ps.executeQuery();
            int wishListID = 0;
            while (rs.next()) {
                wishListID = rs.getInt(1);
            }

            String SQL1 = "insert into wishes (name, description, price, link, amount, store, wishlist_ID, reserved, reserved_by) values (?, ?, ?, ?, ?, ?, ?, ?, ?);";
            PreparedStatement ps1 = connection.prepareStatement(SQL1);
            ps1.setString(1, name);
            ps1.setString(2, description);
            ps1.setDouble(3, price);
            ps1.setString(4, link);
            ps1.setInt(5, amount);
            ps1.setString(6, store);
            ps1.setInt(7, wishListID);
            ps1.setString(8, reserved);
            ps1.setString(9, reservedBy);

            int rs1 = ps1.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public ArrayList<WishListDTO> getWishLists() {
        ArrayList<WishListDTO> allWishLists = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(db_url, SQLusername, pwd)) {
            String SQL = "SELECT * FROM WishLists WHERE USERNAME=?;";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, loggedInUser.getUsername());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                allWishLists.add(new WishListDTO(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }
        } catch (SQLException e) {
            System.out.println("Cannot connect to database");
            e.printStackTrace();
        }
        return allWishLists;
    }

    public void saveAWishList(WishList wishList) {
        String name = wishList.getName();
        String description = wishList.getDescription();

        try (Connection connection = DriverManager.getConnection(db_url, SQLusername, pwd)) {
            String SQL = "insert into wishlists (name, description, username) values (?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, name);
            ps.setString(2, description);
            ps.setString(3, loggedInUser.getUsername());
            int rs = ps.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<WishList> getAllWishLists(ArrayList<WishListDTO> allWishLists, ArrayList<WishDTO> allWishes) {
        List<WishList> wishListList = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(db_url, SQLusername, pwd)) {
            for (WishListDTO w : allWishLists) {
                WishList wishList = new WishList(w.getName(), w.getDescription());

                int id = w.getID();
                String SQL = "SELECT * FROM WISHLISTS WHERE ID=?;";
                PreparedStatement ps = connection.prepareStatement(SQL);

                ps.setInt(1, id);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    for (WishDTO wi : allWishes) {
                        if (rs.getInt(1) == wi.getID()) {
                            Wish wish = new Wish(wi.getName(), wi.getDescription(), wi.getPrice(), wi.getLink(), wi.getAmount(), wi.getStore(), wi.getReserved());

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

    public List<Wish> getWishes(String wishList) {
        try (Connection connection = DriverManager.getConnection(db_url, SQLusername, pwd)) {
            String SQL = "SELECT * FROM WISHLISTS WHERE NAME=?;";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, wishList);
            ResultSet rs = ps.executeQuery();
            int wishListID = 0;
            while (rs.next()) {
                wishListID = rs.getInt(1);
            }

            String SQL1 = "SELECT * FROM WISHES WHERE WISHLIST_ID=?;";
            PreparedStatement ps1 = connection.prepareStatement(SQL1);
            ps1.setInt(1, wishListID);
            ResultSet rs1 = ps1.executeQuery();
            List<Wish> wishes = new ArrayList<>();
            while (rs1.next()) {
                Wish wish = new Wish(rs1.getString(2), rs1.getString(3), rs1.getDouble(4), rs1.getString(5), rs1.getInt(6), rs1.getString(7), rs1.getString(9));

                wish.setWishList(wishList);
                wishes.add(wish);
            }
            wishListWithWishToUpdate = wishList;
            return wishes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Wish getOneWish(List<Wish> wishes, String wish) {
        for (Wish w : wishes) {
            if (w.getName().equalsIgnoreCase(wish)) {
                return w;
            }
        }
        return null;
    }


    public void deleteAWish(String wishList, String wish) {
        int wishListID = 0;

        try (Connection connection = DriverManager.getConnection(db_url, SQLusername, pwd)) {
            String SQL = "SELECT * FROM WISHLISTS WHERE NAME=?;";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, wishList);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                wishListID = rs.getInt(1);
            }


            String SQL1 = "DELETE FROM WISHES WHERE NAME=? AND WISHLIST_ID=?";
            PreparedStatement ps1 = connection.prepareStatement(SQL1);
            ps1.setString(1, wish);
            ps1.setInt(2, wishListID);
            int rs1 = ps1.executeUpdate();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteWishlist(String wishList) {
        try (Connection connection = DriverManager.getConnection(db_url, SQLusername, pwd)) {


            String SQL = "SELECT * FROM WISHLISTS WHERE NAME=?;";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, wishList);
            ResultSet rs = ps.executeQuery();
            int wishListID = 0;
            while (rs.next()) {
                wishListID = rs.getInt(1);
            }
            String SQL1 = "DELETE FROM WISHES WHERE WISHLIST_ID=?;";
            PreparedStatement ps1 = connection.prepareStatement(SQL1);
            ps1.setInt(1, wishListID);
            int rs1 = ps1.executeUpdate();

            String SQL2 = "DELETE FROM WISHLISTS WHERE NAME=?;";
            PreparedStatement ps2 = connection.prepareStatement(SQL2);
            ps2.setString(1, wishList);
            int rs2 = ps2.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateAWish(Wish wish) {
        try (Connection connection = DriverManager.getConnection(db_url, SQLusername, pwd)) {
            String SQL = "SELECT * FROM WISHLISTS WHERE NAME=?;";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, wishListWithWishToUpdate);
            ResultSet rs = ps.executeQuery();
            int wishListID = 0;
            while (rs.next()) {
                wishListID = rs.getInt(1);
            }
            System.out.println(wishListID);
            String SQL1 = "UPDATE WISHES SET DESCRIPTION=?, PRICE=?, LINK=?, AMOUNT=?, STORE=? WHERE NAME=? AND WISHLIST_ID=?";
            PreparedStatement ps1 = connection.prepareStatement(SQL1);
            ps1.setString(1, wish.getDescription());
            ps1.setDouble(2, wish.getPrice());
            ps1.setString(3, wish.getLink());
            ps1.setInt(4, wish.getAmount());
            ps1.setString(5, wish.getStore());
            ps1.setString(6, wish.getName());
            ps1.setInt(7, wishListID);
            int rs1 = ps1.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUser(User user) {
        try (Connection connection = DriverManager.getConnection(db_url, SQLusername, pwd)) {
            String SQL = "INSERT INTO LOGIN (USERNAME, PASSWORD) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            int rs = ps.executeUpdate();
            loggedInUser.setUsername(user.getUsername());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void login(User user) {
        loggedInUser.setUsername(user.getUsername());
    }

    public boolean checkUser(User user) {
        try (Connection connection = DriverManager.getConnection(db_url, SQLusername, pwd)) {
            String SQL = "SELECT * FROM LOGIN WHERE USERNAME=?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, user.getUsername());
            String password = "";
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                password = rs.getString(2);
            }
            if (password.equals(user.getPassword())) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkIfReserved(String username, String wishlist, String wish) {
        String isReserved = "";
        String reservedBy = "";
        try (Connection connection = DriverManager.getConnection(db_url, SQLusername, pwd)) {
            String SQL = "SELECT ID FROM WISHLISTS WHERE NAME=? AND USERNAME=?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, wishlist);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            int wishlistID = 0;
            while (rs.next()) {
                wishlistID = rs.getInt(1);
            }
            String SQL1 = "SELECT RESERVED, RESERVED_BY FROM WISHES  WHERE NAME=? AND WISHLIST_ID=?";
            PreparedStatement ps1 = connection.prepareStatement(SQL1);
            ps1.setString(1, wish);
            ps1.setInt(2, wishlistID);
            ResultSet rs1 = ps1.executeQuery();

            while (rs1.next()) {
                isReserved = rs1.getString(1);
                reservedBy = rs1.getString(2);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (isReserved.equals("reserved"))
            if (!reservedBy.equals(loggedInUser.getUsername())) {
                return true;
            }

        return false;
    }

    public void reserveWish(String username, String wishlist, String wish) {
        try (Connection connection = DriverManager.getConnection(db_url, SQLusername, pwd)) {
            String reserved_by = "";
            String SQL = "SELECT ID FROM WISHLISTS WHERE NAME=? AND USERNAME=?";
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, wishlist);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            int wishlistID = 0;
            while (rs.next()) {
                wishlistID = rs.getInt(1);
            }
            String SQL1 = "SELECT RESERVED_BY FROM WISHES WHERE NAME=? AND WISHLIST_ID=?";
            PreparedStatement ps1 = connection.prepareStatement(SQL1);
            ps1.setString(1, wish);
            ps1.setInt(2, wishlistID);
            ResultSet rs1 = ps1.executeQuery();
            while (rs1.next()) {
                reserved_by = rs1.getString(1);
            }
            System.out.println(reserved_by);
            if (reserved_by.equals(loggedInUser.getUsername())){

            String SQL2 = "UPDATE WISHES SET RESERVED=?, RESERVED_BY=? WHERE NAME=? AND WISHLIST_ID=?";
            PreparedStatement ps2 = connection.prepareStatement(SQL2);
                ps2.setString(1, "not reserved");
                ps2.setString(2, "not reserved");
                ps2.setString(3, wish);
                ps2.setInt(4, wishlistID);
                int rs2 = ps2.executeUpdate();}
             else {
                String SQL2 = "UPDATE WISHES SET RESERVED=?, RESERVED_BY=? WHERE NAME=? AND WISHLIST_ID=?";
                PreparedStatement ps2 = connection.prepareStatement(SQL2);
                ps2.setString(1, "reserved");
                ps2.setString(2, loggedInUser.getUsername());
                ps2.setString(3, wish);
                ps2.setInt(4, wishlistID);
                int rs2 = ps2.executeUpdate();
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
}

