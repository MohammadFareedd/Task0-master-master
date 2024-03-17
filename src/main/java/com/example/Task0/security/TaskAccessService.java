package com.example.Task0.security;

import com.example.Task0.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class TaskAccessService implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private TaskService taskService;
    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext object) {

        String a = (object.getVariables()).toString();
        System.out.println(a);

        int taskId;
        int userId;
        //update
        if (object.getRequest().getMethod().equals("PUT")){
            taskId= Integer.parseInt((object.getRequest().getParameter("id")));
            userId=taskService.findById(taskId).getUser().getId();

        }
        //new task
        else if (a.equals("{}")){
            userId= Integer.parseInt((object.getRequest().getParameter("id")));



        }
        //get task by id
        else {
         taskId= Integer.parseInt(a.split("=")[1].split("}")[0]);
         userId=taskService.findById(taskId).getUser().getId();

        }



        Authentication authentication = authenticationSupplier.get();


        return new AuthorizationDecision(hasUserId(authentication, userId)||authentication.getAuthorities().stream()
                .anyMatch(a2 -> a2.getAuthority().equals("admin")));

    }
    public boolean hasUserId(Authentication authentication, int userId) {


        return userId == Integer.parseInt(authentication.toString().split("id=")[1].split(" ")[0]);

    }
}

