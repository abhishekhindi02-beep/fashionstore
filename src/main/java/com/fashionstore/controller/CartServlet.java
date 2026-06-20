package com.fashionstore.controller;

import java.io.IOException;
import java.util.*;

import com.fashionstore.dao.*;
import com.fashionstore.dao.impl.*;
import com.fashionstore.model.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    // 🔹 HANDLE ALL ACTIONS (ADD / REMOVE / UPDATE)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        String action = request.getParameter("action");

        CartDAO cartDAO = new CartDAOImpl();
        Cart cart = cartDAO.findByUserId(user.getUserId());

        if (cart == null) {
            cartDAO.createCart(user.getUserId());
            cart = cartDAO.findByUserId(user.getUserId());
        }

        CartItemDAO cartItemDAO = new CartItemDAOImpl();

        // 🔥 REMOVE ITEM
        if ("remove".equals(action)) {

            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            cartItemDAO.deleteById(cartItemId);

            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // 🔥 INCREASE / DECREASE QUANTITY
        if ("increase".equals(action) || "decrease".equals(action)) {

            int cartItemId = Integer.parseInt(request.getParameter("cartItemId"));
            CartItem item = cartItemDAO.findById(cartItemId);

            int qty = item.getQuantity();

            if ("increase".equals(action)) {
                qty++;
            } else if ("decrease".equals(action) && qty > 1) {
                qty--;
            }

            cartItemDAO.updateQuantity(cartItemId, qty);

            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        // 🔥 DEFAULT = ADD TO CART
        int productId = Integer.parseInt(request.getParameter("productId"));
        String sizeLabel = request.getParameter("sizeLabel");

        ProductDAO productDAO = new ProductDAOImpl();
        Product product = productDAO.findById(productId);

        CartItem existingItem = cartItemDAO
                .findByCartIdAndProductIdAndSizeLabel(cart.getCartId(), productId, sizeLabel);

        if (existingItem != null) {
            int newQty = existingItem.getQuantity() + 1;
            cartItemDAO.updateQuantity(existingItem.getCartItemId(), newQty);
        } else {
            CartItem item = new CartItem();
            item.setCartId(cart.getCartId());
            item.setProductId(productId);
            item.setSizeLabel(sizeLabel);
            item.setQuantity(1);
            item.setUnitPrice(product.getPrice());

            cartItemDAO.save(item);
        }

        response.sendRedirect(request.getContextPath() + "/cart");
    }

    // 🔹 SHOW CART PAGE
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        CartDAO cartDAO = new CartDAOImpl();
        Cart cart = cartDAO.findByUserId(user.getUserId());

        CartItemDAO cartItemDAO = new CartItemDAOImpl();

        List<CartItem> items = null;

        if (cart != null) {
            items = cartItemDAO.findByCartId(cart.getCartId());
        }

        // 🔥 FETCH PRODUCT NAMES
        ProductDAO productDAO = new ProductDAOImpl();
        Map<Integer, Product> productMap = new HashMap<>();

        if (items != null) {
            for (CartItem item : items) {
                Product p = productDAO.findById(item.getProductId());
                productMap.put(item.getProductId(), p);
            }
        }

        request.setAttribute("cartItems", items);
        request.setAttribute("productMap", productMap);

        request.getRequestDispatcher("/WEB-INF/views/cart.jsp")
               .forward(request, response);
    }
}