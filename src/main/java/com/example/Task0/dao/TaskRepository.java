package com.example.Task0.dao;

import com.example.Task0.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
//Task Repository to deal with the database
public interface TaskRepository extends JpaRepository<Task,Integer> {
}
