package com.example.Task0.rest;

import com.example.Task0.entity.BlacklistToken;
import com.example.Task0.entity.User;
import com.example.Task0.security.AuthenticationFilter;
import com.example.Task0.service.BlacklistTokenService;
import com.example.Task0.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@WebMvcTest(controllers = UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @MockBean
    private BlacklistTokenService blacklistTokenService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private AuthenticationFilter authenticationFilter;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void register() throws Exception {
        User user=new User("Mohammad","mohammad@gmail.com","test",21);
        given(userService.save(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));

        ResultActions response = mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user)));


        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk());


    }
    @Test
    public void findAll() throws Exception {
        //given(userService.save(ArgumentMatchers.any())).willAnswer(invocation -> invocation.getArgument(0));
        List<User> l=new ArrayList<>();
        User user=new User("Mohammad","mohammad@gmail.com","test",21);
        User user1=new User("ali","ali@gmail.com","test",21);
        l.add(user);
        l.add(user1);
        when(userService.findAll()).thenReturn(l);
        ResultActions response = mockMvc.perform(get("/users")
                .contentType(MediaType.APPLICATION_JSON));


        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk());

    }
    @Test
    public void update() throws Exception {
        User user1=new User("ali","ali@gmail.com","test",21);

        when(userService.findById(user1.getId())).thenReturn(user1);
        ResultActions response = mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1)));


        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk());


    }
    @Test
    public void updatenullCase() throws Exception {
        User user1=new User("ali","ali@gmail.com","test",21);

        when(userService.findById(user1.getId())).thenReturn(null);
        ResultActions response = mockMvc.perform(put("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(user1)));


        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNotFound());


    }
    @Test
    public void deleteTest() throws Exception {
        int id=1;
        User user1=new User("ali","ali@gmail.com","test",21);



        when(userService.findById(id)).thenReturn(user1);
        doNothing().when(userService).deleteUserById(id);
        ResultActions response = mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON));



        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk());


    }
    @Test
    public void deleteTestnull() throws Exception {
        int id=1;
        when(userService.findById(id)).thenReturn(null);
        doNothing().when(userService).deleteUserById(id);
        ResultActions response = mockMvc.perform(delete("/users/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());


    }
    @Test
    public void SearchbyId() throws Exception {
        int id=1;
        User user1=new User("ali","ali@gmail.com","test",21);
        when(userService.findById(id)).thenReturn(user1);
        ResultActions response = mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());


    }
    @Test
    public void SearchbyIdNullCase() throws Exception {
        int id=1;
        when(userService.findById(id)).thenReturn(null);
        ResultActions response = mockMvc.perform(get("/users/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());


    }
    @Test
    public void loginTest() throws Exception {

        String email="ali@gmail.com";
        User user1=new User("ali",email,"test",21);
        String req="{\n" +
                "    \"email\":\"ali@gmail.com\",\n" +
                "    \"password\":\"test\"}";

        when(userService.findByUserEmail(email)).thenReturn(user1);
        when(passwordEncoder.matches(Mockito.anyString(),Mockito.anyString())).thenReturn(true);
        ResultActions response = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content((req)));


        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk());
                       //.andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(user1.getUserName())));


    }
    @Test
    public void loginTestInvaildEmail() throws Exception {

        String email="ali@gmail.com";
        User user1=new User("ali",email,"test",21);
        String req="{\n" +
                "    \"email\":\"ali@gmail.com\",\n" +
                "    \"password\":\"test\"}";

        when(userService.findByUserEmail(email)).thenReturn(null);
        ResultActions response = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content((req)));


        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk());
        //.andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(user1.getUserName())));


    }
    @Test
    public void loginTestInvaildPassword() throws Exception {

        String email="ali@gmail.com";
        User user1=new User("ali",email,"test",21);
        when(userService.findByUserEmail(email)).thenReturn(user1);
        String req="{\n" +
                "    \"email\":\"ali@gmail.com\",\n" +
                "    \"password\":\"tes\"}";


        ResultActions response = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content((req)));


        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk());
       // .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(user1.getUserName())));

    }
    @Test
    public void logoutTest() throws Exception {


        ResultActions response = mockMvc.perform(post("/logoutt")
                .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization","a temp token"));




        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk());
        // .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(user1.getUserName())));

    }
    @Test
    public void logoutAllTest() throws Exception {
        List<BlacklistToken>blacklistTokens=new ArrayList<>();
        BlacklistToken token=new BlacklistToken("a temp",new User());
        blacklistTokens.add(token);
        when(blacklistTokenService.findBlacklistTokenByName(Mockito.anyString())).thenReturn(token);

        when(blacklistTokenService.findAllVaildToken(0)).thenReturn(blacklistTokens);



        ResultActions response = mockMvc.perform(post("/logoutall")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","a temp"));




        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk());
        // .andExpect(MockMvcResultMatchers.jsonPath("$.type", CoreMatchers.is(user1.getUserName())));

    }









}
