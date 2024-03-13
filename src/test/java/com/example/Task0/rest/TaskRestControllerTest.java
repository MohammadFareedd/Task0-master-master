package com.example.Task0.rest;

import com.example.Task0.entity.Task;
import com.example.Task0.entity.User;
import com.example.Task0.security.AuthenticationFilter;
import com.example.Task0.service.TaskService;
import com.example.Task0.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.util.*;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class TaskRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TaskService taskService;
    @MockBean
    private UserService userService;
    @MockBean
    private AuthenticationFilter authenticationFilter;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void getAll() throws Exception {
        List<Task> list = new ArrayList<>();
        Task task1 = new Task(1, "nice", false, new Date(), new Date(), new User());
        Task task2 = new Task(2, "nice", false, new Date(), new Date(), new User());
        list.add(task1);
        list.add(task2);
        Page<Task> taskPage = Mockito.mock(Page.class);


        when(taskService.findAll(Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString())).thenReturn(taskPage);
        ResultActions response = mockMvc.perform(get("/tasks")
                .contentType(MediaType.APPLICATION_JSON));


        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk());}


        @Test
        public void addTask () throws Exception {
            User user = new User();

            Task task1 = new Task(1, "nice", false, new Date(), new GregorianCalendar(2025, 0,1).getTime(), user);


            when(userService.findById(Mockito.anyInt())).thenReturn(user);
            when(taskService.save(Mockito.any(Task.class))).thenReturn(task1);

            ResultActions response = mockMvc.perform(post("/tasks")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(task1)));



            // Asserting the response expectations
            response.andExpect(MockMvcResultMatchers.status().isOk());


        }
    @Test
    public void addTaskinvaild () throws Exception {
        User user = new User();

        Task task1 = new Task(1, "nice", false, new Date(), new GregorianCalendar(2024, 0,1).getTime(), user);


        when(userService.findById(Mockito.anyInt())).thenReturn(user);
        when(taskService.save(Mockito.any(Task.class))).thenReturn(task1);

        ResultActions response = mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task1)));



        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNotFound());


    }
    @Test
    public void addTaskExistTask () throws Exception {
        User user = new User();

        Task task1 = new Task(1, "nice", false, new Date(), new GregorianCalendar(2025, 0,1).getTime(), user);


        when(userService.findById(Mockito.anyInt())).thenReturn(user);
        when(taskService.save(Mockito.any(Task.class))).thenReturn(null);

        ResultActions response = mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task1)));



        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNotFound());


    }
    @Test
    public void addTaskNoUser () throws Exception {
        User user = new User();

        Task task1 = new Task(1, "nice", false, new Date(), new GregorianCalendar(2025, 0,1).getTime(), user);


        when(userService.findById(Mockito.anyInt())).thenReturn(null);
        when(taskService.save(Mockito.any(Task.class))).thenReturn(task1);

        ResultActions response = mockMvc.perform(post("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task1)));



        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNotFound());


    }
    @Test
    public void updateTask () throws Exception {
        User user = new User();

        Task task1 = new Task(1, "nice", false, new Date(), new GregorianCalendar(2025, 0,1).getTime(), user);

        when(taskService.findById(Mockito.anyInt())).thenReturn(task1);
        when(userService.findById(Mockito.anyInt())).thenReturn(user);
        when(taskService.save(Mockito.any(Task.class))).thenReturn(task1);

        ResultActions response = mockMvc.perform(put("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task1)));



        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk());


    }
    @Test
    public void updatedTaskinvaild () throws Exception {
        User user = new User();

        Task task1 = new Task(1, "nice", false, new Date(), new GregorianCalendar(2024, 0,1).getTime(), user);

        when(taskService.findById(Mockito.anyInt())).thenReturn(task1);

        when(userService.findById(Mockito.anyInt())).thenReturn(user);
        when(taskService.save(Mockito.any(Task.class))).thenReturn(task1);

        ResultActions response = mockMvc.perform(put("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task1)));



        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNotFound());


    }
    @Test
    public void updateTaskExistTask () throws Exception {
        User user = new User();

        Task task1 = new Task(1, "nice", false, new Date(), new GregorianCalendar(2025, 0,1).getTime(), user);

        when(taskService.findById(Mockito.anyInt())).thenReturn(task1);
        when(userService.findById(Mockito.anyInt())).thenReturn(user);
        when(taskService.save(Mockito.any(Task.class))).thenReturn(null);

        ResultActions response = mockMvc.perform(put("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task1)));



        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNotFound());


    }
    @Test
    public void updateTaskNoUser () throws Exception {
        User user = new User();

        Task task1 = new Task(1, "nice", false, new Date(), new GregorianCalendar(2025, 0,1).getTime(), user);

        when(taskService.findById(Mockito.anyInt())).thenReturn(task1);

        when(userService.findById(Mockito.anyInt())).thenReturn(null);
        when(taskService.save(Mockito.any(Task.class))).thenReturn(task1);

        ResultActions response = mockMvc.perform(put("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task1)));



        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNotFound());


    }
    @Test
    public void updateTaskNoTask () throws Exception {
        User user = new User();

        Task task1 = new Task(1, "nice", false, new Date(), new GregorianCalendar(2025, 0,1).getTime(), user);

        when(taskService.findById(Mockito.anyInt())).thenReturn(null);

        when(userService.findById(Mockito.anyInt())).thenReturn(user);
        when(taskService.save(Mockito.any(Task.class))).thenReturn(task1);

        ResultActions response = mockMvc.perform(put("/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(task1)));



        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isNotFound());


    }
    @Test
    public void deleteTest() throws Exception {
        int id=1;
        Task task1 = new Task(1, "nice", false, new Date(), new GregorianCalendar(2025, 0,1).getTime(), new User());



        when(taskService.findById(id)).thenReturn(task1);
        doNothing().when(taskService).deleteUserById(id);
        ResultActions response = mockMvc.perform(delete("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON));



        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk());


    }
    @Test
    public void deleteTestnull() throws Exception {
        int id=1;
        when(taskService.findById(id)).thenReturn(null);
        doNothing().when(taskService).deleteUserById(id);
        ResultActions response = mockMvc.perform(delete("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());


    }
    @Test
    public void SearchbyId() throws Exception {
        int id=1;
        Task task1 = new Task(1, "nice", false, new Date(), new GregorianCalendar(2025, 0,1).getTime(), new User());

        when(taskService.findById(id)).thenReturn(task1);
        ResultActions response = mockMvc.perform(get("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk());


    }
    @Test
    public void SearchbyIdNoTask() throws Exception {
        int id=1;

        when(taskService.findById(id)).thenReturn(null);
        ResultActions response = mockMvc.perform(get("/tasks/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isNotFound());


    }
    @Test
    public void getAllForUser() throws Exception {
        int id=1;

        Page<Task> taskPage = Mockito.mock(Page.class);


        when(taskService.findUserTask(Mockito.anyInt(),Mockito.anyInt(), Mockito.anyInt(), Mockito.anyString())).thenReturn(taskPage);
        ResultActions response = mockMvc.perform(get("/tasks/user/1")
                .contentType(MediaType.APPLICATION_JSON));


        // Asserting the response expectations
        response.andExpect(MockMvcResultMatchers.status().isOk());}

    }


