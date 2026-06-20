package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.OrderItem;

public interface OrderItemDAO {
    boolean save(OrderItem orderItem);
    boolean saveAll(List<OrderItem> orderItems);
    List<OrderItem> findByOrderId(int orderId);
    boolean deleteByOrderId(int orderId);
}