package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Order;

public interface OrderDAO {
    int save(Order order);
    boolean updateOrderStatus(int orderId, String orderStatus);
    boolean updatePaymentMethod(int orderId, String paymentMethod);
    Order findById(int orderId);
    List<Order> findByUserId(int userId);
    List<Order> findAll();
}
