package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.CartItem;

public interface CartItemDAO {
    boolean save(CartItem cartItem);
    boolean updateQuantity(int cartItemId, int quantity);
    boolean deleteById(int cartItemId);
    boolean deleteByCartId(int cartId);
    CartItem findById(int cartItemId);
    CartItem findByCartIdAndProductIdAndSizeLabel(int cartId, int productId, String sizeLabel);
    List<CartItem> findByCartId(int cartId);
    int countByCartId(int cartId);
}
