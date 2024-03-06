package com.example.Task0.rest;

import com.example.Task0.entity.Task;
import com.example.Task0.exceptions.IdNotFoundError;
import com.example.Task0.logmessages.LogMessages;
import com.example.Task0.service.TaskService;
import com.example.Task0.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

public class TaskController {

    private TaskService taskService;
    private UserService userService;
    @Autowired
    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    //Constructor dependency injection for user service and task service


    //Get request for getting all tasks
    @GetMapping("/tasks")
    public List<Task> findAllTasks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size,@RequestParam(defaultValue = "id") String sort){
        LogMessages.logger.info("Tasks are showed");
        return taskService.findAll(page,size,sort).getContent();
    }

    //Post request for add new task
    @PostMapping("/tasks")
    public Task addTask(@RequestBody Task theTask){


        if (userService.findById(theTask.getUser().getId())==null){
            LogMessages.logger.error("Non existing foreign key");
            throw new IdNotFoundError("Non existing foreign key");
        }


        if(theTask.getStart().compareTo(theTask.getEnd())>=0)throw new IdNotFoundError("Invalid start and end "+theTask.getStart()+" "+theTask.getEnd());
        Task task=taskService.save(theTask);
        if (task==null) throw new IdNotFoundError("there is an existing task in this period "+theTask.getStart()+" "+theTask.getEnd());

        LogMessages.logger.info("New Task was inserted");
        return task;


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
        if(theTask.getStart().compareTo(theTask.getEnd())>=0)throw new IdNotFoundError("Invalid start and end "+theTask.getStart()+" "+theTask.getEnd());

        Task task=taskService.save(theTask);
        if (task==null) throw new IdNotFoundError("there is an existing task in this period "+theTask.getStart()+" "+theTask.getEnd());


        LogMessages.logger.info("Task with Id:"+theTask.getId()+" is updated");
        return task;

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
    @GetMapping("/tasks/user/{id}")
    public List<Task> userTasks(@PathVariable int id,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "2") int size,@RequestParam(defaultValue = "id") String sort){
        Page<Task> temp = taskService.findUserTask(id,page,size,sort);


        LogMessages.logger.info("Tasks for user:"+id+" are found");
        return temp.getContent();
    }

}
