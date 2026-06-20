package com.fashionstore.dao.impl;

import java.sql.*;
import java.util.*;
import com.fashionstore.dao.CategoryDAO;
import com.fashionstore.model.Category;
import com.fashionstore.util.DBConnection;

public class CategoryDAOImpl implements CategoryDAO {

    public boolean save(Category c) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO categories(category_name, description, is_active) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getCategoryName());
            ps.setString(2, c.getDescription());
            ps.setBoolean(3, c.isActive());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Category> findAllActive() {
        List<Category> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM categories WHERE is_active=1";
            ResultSet rs = con.prepareStatement(sql).executeQuery();

            while (rs.next()) {
                Category c = new Category();
                c.setCategoryId(rs.getInt("category_id"));
                c.setCategoryName(rs.getString("category_name"));
                list.add(c);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // remaining methods stub
    public boolean update(Category c){return false;}
    public boolean deleteById(int id){return false;}
    public Category findById(int id){return null;}
    public Category findByName(String name){return null;}
    public List<Category> findAll(){return null;}
    public boolean existsByName(String name){return false;}
}
