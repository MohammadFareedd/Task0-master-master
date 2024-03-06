package com.example.Task0.dao;

import com.example.Task0.entity.Task;
import com.example.Task0.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//Task Repository to deal with the database
public interface TaskRepository extends JpaRepository<Task,Integer> {
    @Query(nativeQuery=true, value="SELECT t.* FROM task t WHERE t.user_id =:id and (t.end>=:start and t.start<=:start or t.end>=:end and t.start<=:end or t.end<=:end and t.start>=:start) ")
    public List<Task> checkTaskDate(int id, String start, String end);
}
