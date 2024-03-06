package com.example.Task0.service;



import com.example.Task0.entity.Task;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TaskService {
    Page<Task> findAll(int page,int size,String sort);
    Task save(Task theUser);
    void deleteUserById(int id);
    Task findById(int id);
    Page<Task> findUserTask(int id,int page,int size,String sort);
}
