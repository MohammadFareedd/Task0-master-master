package com.example.Task0.Service;

import com.example.Task0.dao.TaskRepository;
import com.example.Task0.entity.Task;
import com.example.Task0.entity.User;
import com.example.Task0.service.TaskServiceImp;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskServiceImp taskServiceImp;
    @Test
    public void TaskService_Save_ReturnService(){
        User user=Mockito.mock(User.class);
        Date start=Mockito.mock(Date.class);
        Date end=Mockito.mock(Date.class);
        Task task=new Task("nice",false,start,end,user);
        when(taskRepository.checkTaskDate(Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(new ArrayList<Task>());
        when(taskRepository.save(Mockito.any(Task.class))).thenReturn(task);
        taskServiceImp.save(task);
        assertThat(task).isNotNull();



    }

    @Test
    public void UserService_FindAll_ReturnUsers(){

        Page<Task> taskPage = Mockito.mock(Page.class);


        when(taskRepository.findAll(Mockito.any(Pageable.class))).thenReturn(taskPage);

        Page<Task> temp = taskServiceImp.findAll(1, 10, "id");

        //assertEquals(temp.getSize(),0);
        assertThat(temp.getContent()).isNotNull();

    }
    @Test
    public void findUserTask_Test(){
        Page<Task> taskPage = Mockito.mock(Page.class);
        User user=Mockito.mock(User.class);

        when(taskRepository.userTask(Mockito.anyInt(),Mockito.any(Pageable.class))).thenReturn(taskPage);

        Page<Task> temp = taskServiceImp.findUserTask(user.getId(),1, 10, "id");

        //assertEquals(temp.getSize(),0);
        assertThat(temp.getContent()).isNotNull();

    }
    @Test
    public  void  findTaskById_Test(){
        User user=Mockito.mock(User.class);
        Date start=Mockito.mock(Date.class);
        Date end=Mockito.mock(Date.class);
        Task task=new Task(1,"nice",false,start,end,user);
        when(taskRepository.findById(Mockito.anyInt())).thenReturn(Optional.ofNullable(task));
        Task t=taskServiceImp.findById(1);
        assertThat(t).isNotNull();

    }
    @Test
    public void deleteTaskById(){
        int id=1;
        doNothing().when(taskRepository).deleteById(Mockito.anyInt());
        assertAll(()->taskServiceImp.deleteUserById(id));


    }

}
