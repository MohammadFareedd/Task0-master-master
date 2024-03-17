package com.example.Task0.rest;
import com.example.Task0.dto.Mapper;
import com.example.Task0.dto.UserDTO;
import com.example.Task0.entity.BlacklistToken;
import com.example.Task0.security.Jwt;
import com.example.Task0.entity.User;
import com.example.Task0.exceptions.IdNotFoundError;
import com.example.Task0.logmessages.LogMessages;
import com.example.Task0.service.BlacklistTokenService;
import com.example.Task0.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController

public class UserController {
    private UserService userService;
    private PasswordEncoder passwordEncoder ;
    private BlacklistTokenService blacklistTokenService;
    private Mapper mapper;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder, BlacklistTokenService blacklistTokenService, Mapper mapper) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.blacklistTokenService = blacklistTokenService;
        this.mapper = mapper;
    }
    //Constructor dependency injection for user service and task service



    //Get request for getting all users
    @GetMapping("/users")
  //  @Secured("admin")
        public List<UserDTO> findAllUsers(){
        LogMessages.logger.info("Users are showed");
        return userService.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(toList());

    }
    //Post request for adding new user
    @PostMapping("/users")
    public String register(@RequestBody User theUser){

        LogMessages.logger.info("New user was inserted");

        userService.save(theUser);
        String token= Jwt.generateToken(theUser.getEmail());

        blacklistTokenService.addNew(token,theUser);
        return token;

    }
    //Put request for update a user
    @PutMapping("/users")
    public UserDTO updateUser(@RequestBody User theUser){

        if (userService.findById(theUser.getId())==null){
            LogMessages.logger.error("You try to update non existing id");
            throw new IdNotFoundError("Non existing id");}

        LogMessages.logger.info("User with Id:"+theUser.getId()+" is updated");

        return mapper.toDto(userService.save(theUser));
    }
    @PutMapping("/userupdate")
    public UserDTO updateUser(@RequestBody UserDTO userDTO){
        User theUser=userService.findByUserEmail(userDTO.getEmail());

        if (theUser==null){

            LogMessages.logger.error("You try to update non existing id");
            throw new IdNotFoundError("Non existing id");}

        LogMessages.logger.info("User with Id:"+theUser.getId()+" is updated");

        userService.save(mapper.toUser(userDTO,theUser));

        return userDTO;
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
    public UserDTO searchById(@PathVariable int id){
        User temp=userService.findById(id);
        if (temp==null){
            LogMessages.logger.error("You try to find non existing user");
            throw new IdNotFoundError("Non existing id");}

        LogMessages.logger.info("User with Id:"+id+" is found");
        return mapper.toDto(temp);
    }

    @PostMapping("/login")

    public String login(@RequestBody String request) {
        String email=request.split(",")[0].split(":")[1].replaceAll("\"","");
        if (userService.findByUserEmail(email)==null)
            return "Non existing user please register";
        String password=((request.split(",")[1].split(":")[1].replaceAll("\"","").split("}")[0]));
      if ((passwordEncoder.matches(password,userService.findByUserEmail((email)).getPassword()))){

        String token= Jwt.generateToken(email);
        System.out.println(passwordEncoder.encode(token));
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
