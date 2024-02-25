package com.example.Task0.rest;

import com.example.Task0.entity.Task;
import com.example.Task0.exceptions.IdNotFoundError;
import com.example.Task0.logmessages.LogMessages;
import com.example.Task0.service.TaskService;
import com.example.Task0.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class TaskRestController {

    private TaskService taskService;
    private UserService userService;
    @Autowired
    public TaskRestController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    //Constructor dependency injection for user service and task service


    //Get request for getting all tasks
    @GetMapping("/tasks")
    public List<Task> findAllTasks(){
        LogMessages.logger.info("Tasks are showed");
        return taskService.findAll();
    }

    //Post request for add new task
    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task theTask){


        if (userService.findById(theTask.getUser().getId())==null){
            LogMessages.logger.error("Non existing foreign key");
            throw new IdNotFoundError("Non existing foreign key");
        }
        LogMessages.logger.info("New Task was inserted");
        return taskService.save(theTask);

    }
    //Put request fot update a task
    @PutMapping("/tasks")
    public Task updateTask(@RequestBody Task theTask){
        if (taskService.findById(theTask.getId())==null){
            LogMessages.logger.error("You try to update non existing id");
            throw new IdNotFoundError("Non existing id");}
        if (userService.findById(theTask.getUser().getId())==null){
            LogMessages.logger.error("non existing foreign key");
            throw new IdNotFoundError("Non existing foreign key");
        }
        LogMessages.logger.info("Task with Id:"+theTask.getId()+" is updated");
        return taskService.save(theTask);
    }

    //Delete request for deleting a task depending on its id
    @DeleteMapping("/tasks/{id}")
    public String deleteTask(@PathVariable int id){
        if (taskService.findById(id)==null){
            LogMessages.logger.error("You try to delete non existing id");
            throw new IdNotFoundError("Non existing id");}
        taskService.deleteUserById(id);
        LogMessages.logger.info("Task with Id:"+id+" is deleted");
        return "Task with Id:"+id+" is deleted";
    }
    @GetMapping("/tasks/{id}")
    public Task searchById(@PathVariable int id){
        Task temp=taskService.findById(id);
        if (temp==null){
            LogMessages.logger.error("You try to find non existing task");
            throw new IdNotFoundError("Non existing id");}

        LogMessages.logger.info("Task with Id:"+id+" is found");
        return temp;
    }

}
