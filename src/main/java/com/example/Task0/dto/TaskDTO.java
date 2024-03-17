package com.example.Task0.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {
    private String description;
    private boolean completed;
    private String start;
    private String end;
    private int  userId;
}