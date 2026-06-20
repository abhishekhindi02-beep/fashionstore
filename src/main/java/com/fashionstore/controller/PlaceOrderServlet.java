package com.fashionstore.controller;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import com.fashionstore.dao.*;
import com.fashionstore.dao.impl.*;
import com.fashionstore.model.*;
import com.fashionstore.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/place-order")
public class PlaceOrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        User user = (User) session.getAttribute("user");

        String address = request.getParameter("deliveryAddress");
        String payment = request.getParameter("paymentMethod");

        CartDAO cartDAO = new CartDAOImpl();
        Cart cart = cartDAO.findByUserId(user.getUserId());

        CartItemDAO cartItemDAO = new CartItemDAOImpl();
        List<CartItem> items = cartItemDAO.findByCartId(cart.getCartId());

        double total = 0;

        try (Connection con = DBConnection.getConnection()) {

            // 🔥 1. INSERT INTO ORDERS
            String orderSql = "INSERT INTO orders (user_id, total_amount, payment_method, order_status, delivery_address) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(orderSql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1, user.getUserId());
            ps.setDouble(2, total); // temp (we update later)
            ps.setString(3, payment);
            ps.setString(4, "PLACED"); // default status
            ps.setString(5, address);

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int orderId = rs.getInt(1);

            // 🔥 2. INSERT ORDER ITEMS
            String itemSql = "INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price, subtotal, size_label) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps2 = con.prepareStatement(itemSql);

            ProductDAO productDAO = new ProductDAOImpl();

            for (CartItem item : items) {

                Product p = productDAO.findById(item.getProductId());

                double subtotal = item.getQuantity() * item.getUnitPrice();
                total += subtotal;

                ps2.setInt(1, orderId);
                ps2.setInt(2, item.getProductId());
                ps2.setString(3, p.getProductName());
                ps2.setInt(4, item.getQuantity());
                ps2.setDouble(5, item.getUnitPrice());
                ps2.setDouble(6, subtotal);
                ps2.setString(7, item.getSizeLabel());

                ps2.addBatch();
            }

            ps2.executeBatch();

            // 🔥 3. UPDATE TOTAL IN ORDERS
            String updateSql = "UPDATE orders SET total_amount=? WHERE order_id=?";
            PreparedStatement ps3 = con.prepareStatement(updateSql);

            ps3.setDouble(1, total);
            ps3.setInt(2, orderId);
            ps3.executeUpdate();

            // 🔥 4. CLEAR CART
            cartItemDAO.deleteByCartId(cart.getCartId());

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect(request.getContextPath() + "/order-success");
    }
}
