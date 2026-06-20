package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.ProductSize;

public interface ProductSizeDAO {
    boolean save(ProductSize productSize);
    boolean update(ProductSize productSize);
    boolean deleteById(int productSizeId);
    ProductSize findById(int productSizeId);
    ProductSize findByProductIdAndSizeLabel(int productId, String sizeLabel);
    List<ProductSize> findByProductId(int productId);
    List<ProductSize> findAvailableByProductId(int productId);
    boolean updateStockQuantity(int productSizeId, int stockQuantity);
}