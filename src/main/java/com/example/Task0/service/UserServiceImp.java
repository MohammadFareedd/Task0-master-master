package com.example.Task0.service;

import com.example.Task0.dao.UserRepository;
import com.example.Task0.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//Service class for dealing with user repository
@Service
public class UserServiceImp implements UserService{
    private UserRepository userRepository;
    @Autowired
    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User save(User theUser) {
        return userRepository.save(theUser);
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findById(int id) {
        Optional<User> temp=userRepository.findById(id);
        if(temp.isPresent()){
            User user=temp.get();
            return user;

        }
        return null;
    }
}
