package com.example.Task0.service;

import com.example.Task0.dao.TaskRepository;
import com.example.Task0.entity.Task;
import com.example.Task0.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//Service class for dealing with task repository
@Service
public class TaskServiveImp implements TaskService{
    private TaskRepository taskRepository;
    @Autowired
    public TaskServiveImp(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

    }

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task save(Task theUser) {
        return taskRepository.save(theUser);
    }

    @Override
    public void deleteUserById(int id) {
        taskRepository.deleteById(id);
    }

    @Override
    public Task findById(int id) {

            Optional<Task> temp=taskRepository.findById(id);
            if(temp.isPresent()){

                return temp.get();

            }
            return null;
        }
    }

