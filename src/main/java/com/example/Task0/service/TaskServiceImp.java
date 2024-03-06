package com.example.Task0.service;

import com.example.Task0.dao.TaskRepository;
import com.example.Task0.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
//Service class for dealing with task repository
@Service
public class TaskServiceImp implements TaskService{
    private TaskRepository taskRepository;
    @Autowired
    public TaskServiceImp(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;

    }

    @Override
    public Page<Task> findAll(int page,int size,String sort) {

        Pageable pageable = PageRequest.of( page, size, Sort.Direction.ASC,sort);
        return taskRepository.findAll(pageable);
    }
    @Override
    public Page<Task> findUserTask(int id,int page,int size,String sort) {

        PageRequest pageable = PageRequest.of(page, size,Sort.Direction.ASC,sort);
        return  (taskRepository.userTask(id,pageable));
    }

    @Override
    public Task save(Task theUser) {
        if (taskRepository.checkTaskDate(theUser.getUser().getId(),theUser.getStart(),theUser.getEnd()).size()==0)
            return taskRepository.save(theUser);
        return null;
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

