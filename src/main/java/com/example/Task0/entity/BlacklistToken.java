package com.example.Task0.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table (name = "Blacklist_token")
public class BlacklistToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"task","userName","email","password","age"})
    private User user;

    public BlacklistToken() {
    }

    public BlacklistToken(String name,  User user) {

        this.name = name;

        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }



    @Override
    public String toString() {
        return "BlacklistToken{" +
                "id=" + id +
                ", name='" + name + '\'' +

                ", user=" + user +
                '}';
    }
}
