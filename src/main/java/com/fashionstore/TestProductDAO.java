package com.fashionstore;

import java.util.List;

import com.fashionstore.dao.ProductDAO;
import com.fashionstore.dao.impl.ProductDAOImpl;
import com.fashionstore.model.Product;

public class TestProductDAO {

    public static void main(String[] args) {

        ProductDAO dao = new ProductDAOImpl();

        List<Product> products = dao.findAllActive();

        System.out.println("------ Product List ------");

        for (Product p : products) {
            System.out.println(
                p.getProductId() + " | " +
                p.getProductName() + " | " +
                p.getPrice()
            );
        }
    }
}