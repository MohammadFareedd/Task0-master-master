package com.example.Task0.dto;

import com.example.Task0.entity.Task;
import com.example.Task0.entity.User;
import org.mapstruct.*;


@Mapper(componentModel = "spring")

public interface CustomnMapper  {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "age", target = "age")

    User updateCustomerFromDto(UserDTO dto, @MappingTarget User entity);
    @Mapping(source = "userName", target = "userName")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "age", target = "age")
    @Mapping(target = "password", ignore = true)
    UserDTO toDto(User user);



}
