package com.fashionstore.dao.impl;

import java.sql.*;
import java.util.*;
import com.fashionstore.dao.ProductSizeDAO;
import com.fashionstore.model.ProductSize;
import com.fashionstore.util.DBConnection;

public class ProductSizeDAOImpl implements ProductSizeDAO {

    public List<ProductSize> findByProductId(int productId) {
        List<ProductSize> list = new ArrayList<>();

        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM product_sizes WHERE product_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, productId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductSize ps1 = new ProductSize();
                ps1.setSizeLabel(rs.getString("size_label"));
                ps1.setStockQuantity(rs.getInt("stock_quantity"));
                list.add(ps1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    // stubs
    public boolean save(ProductSize p){return false;}
    public boolean update(ProductSize p){return false;}
    public boolean deleteById(int id){return false;}
    public ProductSize findById(int id){return null;}
    public ProductSize findByProductIdAndSizeLabel(int pid,String s){return null;}
    public List<ProductSize> findAvailableByProductId(int pid){return null;}
    public boolean updateStockQuantity(int id,int q){return false;}
}
