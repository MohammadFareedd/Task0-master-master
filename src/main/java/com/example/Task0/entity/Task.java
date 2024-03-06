package com.example.Task0.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

//@Entity class for the task table in the database
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "description")
    private String description;
    @Column(name = "completed")
    private boolean completed;
    @Column (name = "start")
    private Date start;
    @Column (name = "end")
    private Date end;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"task","userName","email","password","age"})
    private User user;



    public Task() {
    }

    public Task( String description, boolean completed, Date start, Date end, User user) {

        this.description = description;
        this.completed = completed;
        this.start = start;
        this.end = end;
        this.user = user;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getStart() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return (simpleDateFormat.format(this.start));
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public String getEnd() {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return (simpleDateFormat.format(this.end));
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                ", user=" + user +

                '}';
    }
}
