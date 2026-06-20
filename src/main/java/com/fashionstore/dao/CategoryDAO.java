package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.Category;

public interface CategoryDAO {
    boolean save(Category category);
    boolean update(Category category);
    boolean deleteById(int categoryId);
    Category findById(int categoryId);
    Category findByName(String categoryName);
    List<Category> findAll();
    List<Category> findAllActive();
    boolean existsByName(String categoryName);
}