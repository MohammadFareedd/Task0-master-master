package com.example.Task0.dto;

import com.example.Task0.dto.TaskDTO;
import com.example.Task0.dto.UserDTO;
import com.example.Task0.entity.Task;
import com.example.Task0.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapper {
    @Autowired
    private ModelMapper modelMapper;

    public UserDTO toDto(User user) {



        return modelMapper.map(user,UserDTO.class);
    }
    public TaskDTO toDto(Task task) {
        return modelMapper.map(task,TaskDTO.class);

    }
    public User toUser(UserDTO userDTO,User user) {
        user.setEmail(userDTO.getEmail());
        user.setAge(userDTO.getAge());
        user.setUserName(userDTO.getName());
        return user;
    }



}
