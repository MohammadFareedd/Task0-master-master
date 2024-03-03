package com.example.Task0.rest;


import com.example.Task0.entity.BlacklistToken;
import com.example.Task0.security.Jwt;
import com.example.Task0.entity.User;
import com.example.Task0.exceptions.IdNotFoundError;
import com.example.Task0.logmessages.LogMessages;
import com.example.Task0.security.LoginInfo;
import com.example.Task0.service.BlacklistTokenService;
import com.example.Task0.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder ;
    private BlacklistTokenService blacklistTokenService;
    @Autowired

    public UserController(UserService userService, PasswordEncoder passwordEncoder, BlacklistTokenService blacklistTokenService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.blacklistTokenService = blacklistTokenService;
    }


    //Constructor dependency injection for user service and task service



    //Get request for getting all users
    @GetMapping("/users")
  //  @Secured("admin")
    public List<User> findAllUsers(){
        LogMessages.logger.info("Users are showed");
        return userService.findAll();

    }
    //Post request for adding new user
    @PostMapping("/users")
    public String register(@RequestBody User theUser){

        LogMessages.logger.info("New user was inserted");

       userService.save(theUser);
        return Jwt.generateToken(theUser.getEmail());
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

    @PostMapping("/login")

    public String login(@RequestBody String request) {
        String email=request.split(",")[0].split(":")[1].replaceAll("\"","");
        if (userService.findByUserEmail(email)==null)
            return "Non existing user please register";
        String password=((request.split(",")[1].split(":")[1].replaceAll("\"","").split("}")[0]));
      if ((passwordEncoder.matches(password,userService.findByUserEmail((email)).getPassword()))){

        String token= Jwt.generateToken(email);
          blacklistTokenService.addNew(token,userService.findByUserEmail((email)));
      return token;}
        else{
            return  "Password is Wrong";
        }

    }
    @PostMapping("/logoutt")
    public String logout(HttpServletRequest request){
        String a = request.getHeader("Authorization");
            String token = (a.split((" ")))[1];
            blacklistTokenService.deleteToken(token);
            return "Logged out successfully";}
    @PostMapping("/logoutall")
    public String logoutall(HttpServletRequest request){
        String a = request.getHeader("Authorization");
        String curnnet_token = (a.split((" ")))[1];
        List<BlacklistToken> list = blacklistTokenService.findAllVaildToken(blacklistTokenService.findBlacklistTokenByName(curnnet_token).getUser().getId());
        for (BlacklistToken token:list){
        blacklistTokenService.deleteToken(token.getName());}
        return "Logged out successfully from all accounts";}




}
