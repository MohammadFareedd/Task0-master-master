package com.example.Task0.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

//Class to handle the exception that occurs in the program
@ControllerAdvice
public class ExceptionHandlerr {
    //Handle the non existing id
    @ExceptionHandler
    public ResponseEntity<TaskManagerErrorResponse> handle(IdNotFoundError e){
        TaskManagerErrorResponse temp=new TaskManagerErrorResponse(HttpStatus.NOT_FOUND.value(),e.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(temp,HttpStatus.NOT_FOUND);
    }
    //Handle when mapping non existing url
    @ExceptionHandler
    public ResponseEntity<TaskManagerErrorResponse> handle(NoHandlerFoundException e){
        TaskManagerErrorResponse temp=new TaskManagerErrorResponse(HttpStatus.NOT_FOUND.value(),e.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(temp,HttpStatus.NOT_FOUND);
    }
    //Handle other type of errors
   @ExceptionHandler
    public ResponseEntity<TaskManagerErrorResponse> handle(Exception e){
        TaskManagerErrorResponse temp=new TaskManagerErrorResponse(HttpStatus.BAD_REQUEST.value(),e.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(temp,HttpStatus.BAD_REQUEST);
    }






}
