package com.fashionstore.controller;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.ProductSizeDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.dao.impl.ProductSizeDAOImpl;
import com.fashionstore.model.Product;
import com.fashionstore.model.ProductSize;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/product-details")
public class ProductDetailsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        if (idParam == null || idParam.isEmpty()) {
            response.sendRedirect("products");
            return;
        }

        int productId = Integer.parseInt(idParam);

        ProductDAO productDAO = new ProductDAOImpl();
        ProductSizeDAO productSizeDAO = new ProductSizeDAOImpl();

        Product product = productDAO.findById(productId);
        
        System.out.println("Product ID: " + productId);
        System.out.println("Product Object: " + product);
        List<ProductSize> sizeList = productSizeDAO.findByProductId(productId);

        request.setAttribute("product", product);
        request.setAttribute("sizeList", sizeList);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/product-details.jsp");
        rd.forward(request, response);
    }
}
