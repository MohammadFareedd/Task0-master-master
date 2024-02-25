package com.example.Task0.exceptions;

public class IdNotFoundError extends  RuntimeException{


    public IdNotFoundError(String message) {
        super(message);
    }

    public IdNotFoundError(String message, Throwable cause) {
        super(message, cause);
    }

    public IdNotFoundError(Throwable cause) {
        super(cause);
    }


}
