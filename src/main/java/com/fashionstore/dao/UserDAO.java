package com.fashionstore.dao;

import java.util.List;
import com.fashionstore.model.User;

public interface UserDAO {
    boolean save(User user);
    boolean update(User user);
    boolean deleteById(int userId);
    User findById(int userId);
    User findByEmail(String email);
    User login(String email, String password);
    List<User> findAll();
    boolean existsByEmail(String email);
}