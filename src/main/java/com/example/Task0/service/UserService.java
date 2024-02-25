package com.example.Task0.service;

import com.example.Task0.entity.User;

import java.util.List;

public interface UserService {
    List<User>findAll();
    User save(User theUser);
    void deleteUserById(int id);
    User findById(int id);


}
