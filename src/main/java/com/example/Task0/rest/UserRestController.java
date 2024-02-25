package com.example.Task0.rest;

import com.example.Task0.entity.Task;
import com.example.Task0.entity.User;
import com.example.Task0.exceptions.IdNotFoundError;
import com.example.Task0.logmessages.LogMessages;
import com.example.Task0.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class UserRestController {
    private UserService userService;

    //Constructor dependency injection for user service and task service
    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;

    }
    //Get request for getting all users
    @GetMapping("/users")
    public List<User> findAllUsers(){
        LogMessages.logger.info("Users are showed");
        return userService.findAll();

    }
    //Post request for adding new user
    @PostMapping("/users")
    public User addUser(@RequestBody User theUser){

        LogMessages.logger.info("New user was inserted");

        return userService.save(theUser);

    }
    //Put request for update a user
    @PutMapping("/users")
    public User updateUser(@RequestBody User theUser){
        if (userService.findById(theUser.getId())==null){
            LogMessages.logger.error("You try to update non existing id");
            throw new IdNotFoundError("Non existing id");}

        LogMessages.logger.info("User with Id:"+theUser.getId()+" is updated");

        return userService.save(theUser);
    }
    //Delete request for deleting a user depending on its id
    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable int id){
        if (userService.findById(id)==null){
            LogMessages.logger.error("You try to delete non existing id");
            throw new IdNotFoundError("Non existing id");}
        userService.deleteUserById(id);
        LogMessages.logger.info("User with Id:"+id+" is deleted");
        return "User with Id:"+id+" is deleted";

    }
    @GetMapping("/users/{id}")
    public User searchById(@PathVariable int id){
        User temp=userService.findById(id);
        if (temp==null){
            LogMessages.logger.error("You try to find non existing user");
            throw new IdNotFoundError("Non existing id");}

        LogMessages.logger.info("User with Id:"+id+" is found");
        return temp;
    }
}
