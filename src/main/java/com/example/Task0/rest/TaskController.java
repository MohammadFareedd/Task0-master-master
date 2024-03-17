package com.example.Task0.rest;

import com.example.Task0.dto.Mapper;
import com.example.Task0.dto.TaskDTO;
import com.example.Task0.entity.Task;
import com.example.Task0.exceptions.IdNotFoundError;
import com.example.Task0.logmessages.LogMessages;
import com.example.Task0.service.TaskService;
import com.example.Task0.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController

public class TaskController {

    private TaskService taskService;
    private UserService userService;
    private Mapper mapper;

    @Autowired
    public TaskController(TaskService taskService, UserService userService, Mapper mapper) {
        this.taskService = taskService;
        this.userService = userService;
        this.mapper = mapper;
    }
    //Constructor dependency injection for user service and task service


    //Get request for getting all tasks
    @GetMapping("/tasks")
    public List<TaskDTO> findAllTasks(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size, @RequestParam(defaultValue = "id") String sort){
        LogMessages.logger.info("Tasks are showed");
        return taskService.findAll(page,size,sort).getContent().stream()
                .map(mapper::toDto)
                .collect(toList());
    }

    //Post request for add new task
    @PostMapping("/tasks")
    public TaskDTO addTask(@RequestBody Task theTask){


        if (userService.findById(theTask.getUser().getId())==null){
            LogMessages.logger.error("Non existing foreign key");
            throw new IdNotFoundError("Non existing foreign key");
        }


        if(theTask.getStart().compareTo(theTask.getEnd())>=0)throw new IdNotFoundError("Invalid start and end "+theTask.getStart()+" "+theTask.getEnd());
        Task task=taskService.save(theTask);
        if (task==null) throw new IdNotFoundError("there is an existing task in this period "+theTask.getStart()+" "+theTask.getEnd());

        LogMessages.logger.info("New Task was inserted");
        return mapper.toDto(task);


    }
    //Put request fot update a task
    @PutMapping("/tasks")
    public TaskDTO updateTask(@RequestBody Task theTask){
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
        return mapper.toDto(task);

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
    public TaskDTO searchById(@PathVariable int id){
        Task temp=taskService.findById(id);
        if (temp==null){
            LogMessages.logger.error("You try to find non existing task");
            throw new IdNotFoundError("Non existing id");}

        LogMessages.logger.info("Task with Id:"+id+" is found");
        return mapper.toDto(temp);
    }
    @GetMapping("/tasks/user/{id}")
    public List<TaskDTO> userTasks(@PathVariable int id,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = Integer.MAX_VALUE+"") int size,
                                @RequestParam(defaultValue = "id") String sort){
        Page<Task> temp = taskService.findUserTask(id,page,size,sort);


        LogMessages.logger.info("Tasks for user:"+id+" are found");
        return temp.getContent().stream()
                .map(mapper::toDto)
                .collect(toList());
    }

}
