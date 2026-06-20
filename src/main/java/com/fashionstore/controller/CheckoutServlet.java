package com.fashionstore.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fashionstore.dao.CartDAO;
import com.fashionstore.dao.CartItemDAO;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.CartDAOImpl;
import com.fashionstore.dao.impl.CartItemDAOImpl;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Cart;
import com.fashionstore.model.CartItem;
import com.fashionstore.model.Product;
import com.fashionstore.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {

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

        if (cart == null) {
            response.sendRedirect(request.getContextPath() + "/cart");
            return;
        }

        CartItemDAO cartItemDAO = new CartItemDAOImpl();
        List<CartItem> items = cartItemDAO.findByCartId(cart.getCartId());

        ProductDAO productDAO = new ProductDAOImpl();
        Map<Integer, Product> productMap = new HashMap<>();

        double grandTotal = 0;

        for (CartItem item : items) {
            Product p = productDAO.findById(item.getProductId());
            productMap.put(item.getProductId(), p);
            grandTotal += item.getQuantity() * item.getUnitPrice();
        }

        request.setAttribute("cartItems", items);
        request.setAttribute("productMap", productMap);
        request.setAttribute("grandTotal", grandTotal);

        request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
    }
}