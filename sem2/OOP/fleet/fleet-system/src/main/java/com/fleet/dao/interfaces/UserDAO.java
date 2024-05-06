package com.fleet.dao.interfaces;

import com.fleet.models.User;

import java.util.List;

public interface UserDAO {
    void saveUser(User user);

    User getUserById(String userId);

    List<User> getAllUsers();

    void updateUser(User user);

    void deleteUser(Long id);
}
