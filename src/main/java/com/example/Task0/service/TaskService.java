package com.example.Task0.service;



import com.example.Task0.entity.Task;

import java.util.List;

public interface TaskService {
    List<Task> findAll();
    Task save(Task theUser);
    void deleteUserById(int id);
    Task findById(int id);
}
