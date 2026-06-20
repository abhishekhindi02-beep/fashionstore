package com.fashionstore.dao.impl;

import java.sql.*;
import com.fashionstore.dao.CartDAO;
import com.fashionstore.model.Cart;
import com.fashionstore.util.DBConnection;

public class CartDAOImpl implements CartDAO {

    public Cart findByUserId(int userId) {
        Cart cart = null;

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM cart WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                cart = new Cart();
                cart.setCartId(rs.getInt("cart_id"));
                cart.setUserId(userId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cart;
    }

    // stubs
    @Override
    public boolean createCart(int userId) {

        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO cart (user_id) VALUES (?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, userId);

            ps.executeUpdate();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    public boolean deleteByUserId(int userId){return false;}
    public boolean clearCart(int cartId){return false;}
}