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
public class CustomMapperimp implements CustomnMapper  {
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDTO toDto(User user) {



        return modelMapper.map(user,UserDTO.class);
    }
    public TaskDTO toDto(Task task) {
        return modelMapper.map(task,TaskDTO.class);

    }



    @Override
    public User updateCustomerFromDto(UserDTO dto, User entity) {
//       return (modelMapper.map(dto,User.class));
        ModelMapper mapper = new ModelMapper();
        mapper.typeMap(UserDTO.class, User.class)
                .addMappings(mp -> mp.skip(User::setPassword));
        return (mapper.map(dto,User.class));
//        return mapper;

    }
}
