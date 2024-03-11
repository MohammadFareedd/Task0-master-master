package com.example.Task0.Service;

import com.example.Task0.dao.UserRepository;
import com.example.Task0.entity.Role;
import com.example.Task0.entity.User;

import com.example.Task0.service.UserServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImp userService;
    @Test
    public void UserService_FindAll_ReturnUsers(){
        User user1=new User("k","l","hh",9);
        User user2=new User("k","l1","hh",9);
        User user3=new User("k","l2","hh",9);
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        list.add(user3);
        when(userRepository.findAll()).thenReturn(list);
        List<User> users = userService.findAll();
        assertEquals(3,users.size());
        verify(userRepository ,times(1)).findAll();

    }
    @Test
    public void  UserService_LoadByName_Test(){

        String email="l";
       Role r1=new Role("admin");
       Role r2=new Role("leader");
       Collection<Role> roles=Mockito.mock(Collection.class);

        User user1=new User("k","l","hh",9,roles);
        when(userRepository.findUserByEmail(email)).thenReturn(user1);

        UserDetails temp = userService.loadUserByUsername(email);
        assertEquals(temp.getUsername(),email);

    }

    @Test
    public void  UserService_LoadByName_TestNull(){

        String email="l";

        when(userRepository.findUserByEmail(email)).thenReturn(null);

        UserDetails temp = userService.loadUserByUsername(email);
        assertThat(temp).isNull();

    }
    @Test
    public void Save_Test(){
        User user=new User("Mohammad","Mohammad@gmail.com","test",21);
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        User temp=userService.save(user);
        assertThat(temp).isNotNull();

    }
    @Test
    public void deleteById_Test(){
        int id=1;
        doNothing().when(userRepository).deleteById(id);
        assertAll(()->userService.deleteUserById(id));

    }
    @Test
    public void findUserByEmail_Test(){
        String email="mohammad@gmail.com";
        User user=new User("mohammad",email,"test",21);
        when(userRepository.findUserByEmail(Mockito.anyString())).thenReturn(user);
        User temp=userService.findByUserEmail(email);
        assertThat(temp).isNotNull();
    }
    @Test
    public void findUserById_Test(){
        int id=0;
        User user=new User("mohammad","mohammad@gmail.com","test",21);
        when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(user));
        User temp=userService.findById(id);
        assertThat(temp).isNotNull();
    }
    @Test
    public void findUserByIdNullCase_Test(){
        int id=0;
        when(userRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(null));
        User temp=userService.findById(id);
        assertThat(temp).isNull();
    }


}
