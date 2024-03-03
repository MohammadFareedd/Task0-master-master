package com.example.Task0.service;

import com.example.Task0.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.memory.UserAttribute;
//import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User>findAll();
    User save(User theUser);
    void deleteUserById(int id);
    User findById(int id);
     User findByUserEmail(String email);


}
