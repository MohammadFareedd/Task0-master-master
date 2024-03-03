package com.example.Task0.security;


import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import java.util.function.Supplier;

@Component
public class UserAccessService implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authenticationSupplier, RequestAuthorizationContext object) {

        int userId;
        String a = (object.getVariables()).toString();
        if (object.getRequest().getMethod().equals("PUT")){
            userId= Integer.parseInt((object.getRequest().getParameter("id")));

        }
        else {
             userId= Integer.parseInt(a.split("=")[1].split("}")[0]);
        }





        Authentication authentication = authenticationSupplier.get();



        return new AuthorizationDecision(hasUserId(authentication, userId)||authentication.getAuthorities().stream()
                .anyMatch(a2 -> a2.getAuthority().equals("admin")));

    }
    public boolean hasUserId(Authentication authentication, int userId) {




        return userId == Integer.parseInt(authentication.toString().split("id=")[1].split(" ")[0]);

    }
}

