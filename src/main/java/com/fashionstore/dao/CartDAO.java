package com.fashionstore.dao;

import com.fashionstore.model.Cart;

public interface CartDAO {
    Cart findByUserId(int userId);
    boolean createCart(int userId);
    boolean deleteByUserId(int userId);
    boolean clearCart(int cartId);
}