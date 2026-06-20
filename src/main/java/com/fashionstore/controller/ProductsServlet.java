package com.fashionstore.controller;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/products")
public class ProductsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ProductDAO dao = new ProductDAOImpl();

        // Fetch all products
        List<Product> productList = dao.findAllActive();

        request.setAttribute("productList", productList);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/products.jsp");
        rd.forward(request, response);
    }
}
