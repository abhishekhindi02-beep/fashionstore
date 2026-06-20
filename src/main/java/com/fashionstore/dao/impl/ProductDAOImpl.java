package com.fashionstore.dao.impl;

import java.sql.*;
import java.util.*;
import com.fashionstore.dao.ProductDAO;
import com.fashionstore.model.Product;
import com.fashionstore.util.DBConnection;

public class ProductDAOImpl implements ProductDAO {

    public List<Product> findAllActive() {
        List<Product> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM products WHERE is_active=1";
            ResultSet rs = con.prepareStatement(sql).executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setPrice(rs.getDouble("price"));
                p.setImageUrl(rs.getString("image_url"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Product> findByCategoryId(int categoryId) {
        List<Product> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM products WHERE category_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, categoryId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                list.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // stubs
    public boolean save(Product p){return false;}
    public boolean update(Product p){return false;}
    public boolean deleteById(int id){return false;}
    @Override
    public Product findById(int id) {

        Product p = null;

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT * FROM products WHERE product_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Product();

                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setPrice(rs.getDouble("price"));
                p.setImageUrl(rs.getString("image_url"));
                p.setDescription(rs.getString("description"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return p;
    }
    public List<Product> findAll(){return null;}
    public List<Product> findByCategoryName(String name){return null;}
    public List<Product> searchByName(String keyword){return null;}
    public List<Product> filterByPriceRange(double min, double max){return null;}
    public List<Product> filterByCategoryAndKeyword(int cat, String key){return null;}
    public List<Product> filterProducts(Integer c, String k, Double min, Double max){return null;}
}
