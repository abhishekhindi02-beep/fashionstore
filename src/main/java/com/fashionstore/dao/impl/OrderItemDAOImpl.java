package com.fashionstore.dao.impl;

import java.sql.*;
import java.util.*;
import com.fashionstore.dao.OrderItemDAO;
import com.fashionstore.model.OrderItem;
import com.fashionstore.util.DBConnection;

public class OrderItemDAOImpl implements OrderItemDAO {

    public List<OrderItem> findByOrderId(int orderId) {
        List<OrderItem> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM order_items WHERE order_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, orderId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                OrderItem o = new OrderItem();
                o.setProductName(rs.getString("product_name"));
                o.setQuantity(rs.getInt("quantity"));
                list.add(o);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // stubs
    public boolean save(OrderItem o){return false;}
    public boolean saveAll(List<OrderItem> list){return false;}
    public boolean deleteByOrderId(int id){return false;}
}
