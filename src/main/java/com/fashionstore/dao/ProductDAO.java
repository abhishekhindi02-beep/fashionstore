package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Product;

public interface ProductDAO {
    boolean save(Product product);
    boolean update(Product product);
    boolean deleteById(int productId);
    Product findById(int productId);
    List<Product> findAll();
    List<Product> findAllActive();
    List<Product> findByCategoryId(int categoryId);
    List<Product> findByCategoryName(String categoryName);
    List<Product> searchByName(String keyword);
    List<Product> filterByPriceRange(double minPrice, double maxPrice);
    List<Product> filterByCategoryAndKeyword(int categoryId, String keyword);
    List<Product> filterProducts(Integer categoryId, String keyword, Double minPrice, Double maxPrice);
}
