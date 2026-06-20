package com.fashionstore.dao.impl;

import java.sql.*;
import java.util.*;

import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.model.CartItem;
import com.fashionstore.util.DBConnection;

public class CartItemDAOImpl implements CartItemDAO {

    @Override
    public boolean save(CartItem item) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "INSERT INTO cart_items (cart_id, product_id, size_label, quantity, unit_price) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, item.getCartId());
            ps.setInt(2, item.getProductId());
            ps.setString(3, item.getSizeLabel());
            ps.setInt(4, item.getQuantity());
            ps.setDouble(5, item.getUnitPrice());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateQuantity(int cartItemId, int quantity) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "UPDATE cart_items SET quantity=? WHERE cart_item_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, quantity);
            ps.setInt(2, cartItemId);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteById(int cartItemId) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "DELETE FROM cart_items WHERE cart_item_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, cartItemId);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByCartId(int cartId) {
        try (Connection con = DBConnection.getConnection()) {

            String sql = "DELETE FROM cart_items WHERE cart_id=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, cartId);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public CartItem findById(int cartItemId) {

        CartItem item = null;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM cart_items WHERE cart_item_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cartItemId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                item = new CartItem();
                item.setCartItemId(rs.getInt("cart_item_id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setSizeLabel(rs.getString("size_label"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("unit_price"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return item;
    }

    @Override
    public CartItem findByCartIdAndProductIdAndSizeLabel(int cartId, int productId, String sizeLabel) {

        CartItem item = null;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM cart_items WHERE cart_id=? AND product_id=? AND size_label=?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, cartId);
            ps.setInt(2, productId);
            ps.setString(3, sizeLabel);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                item = new CartItem();
                item.setCartItemId(rs.getInt("cart_item_id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setSizeLabel(rs.getString("size_label"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("unit_price"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return item;
    }

    @Override
    public List<CartItem> findByCartId(int cartId) {

        List<CartItem> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM cart_items WHERE cart_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cartId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CartItem item = new CartItem();
                item.setCartItemId(rs.getInt("cart_item_id"));
                item.setCartId(rs.getInt("cart_id"));
                item.setProductId(rs.getInt("product_id"));
                item.setSizeLabel(rs.getString("size_label"));
                item.setQuantity(rs.getInt("quantity"));
                item.setUnitPrice(rs.getDouble("unit_price"));

                list.add(item);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public int countByCartId(int cartId) {

        int count = 0;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT COUNT(*) FROM cart_items WHERE cart_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, cartId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                count = rs.getInt(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}