package com.example.Task0.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.hibernate.annotations.Formula;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Entity class for the user table in the database
@Entity
@AllArgsConstructor
@Builder
@Table(name = "user")

public class User{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "age")
    private int age;
    @Transient
    private String id_UserName;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<Task> task;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private List<BlacklistToken> blacklistTokens;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User() {
    }

    public User(String userName, String email, String password,int age) {

        this.userName = userName;
        this.email = email;
        this.password =password;
        this.age=age;
        this.task = new ArrayList<>();
        this.blacklistTokens=new ArrayList<>();

    }

    public User(String userName, String email, String password, int age, Collection<Role> roles) {

        this.userName = userName;
        this.email = email;
        this.password =password;
        this.age = age;
        this.task = new ArrayList<>();
        this.blacklistTokens=new ArrayList<>();
        this.roles = roles;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }



    public void setTask(List<Task> task) {
        this.task = task;
    }

    public void setBlacklistTokens(List<BlacklistToken> blacklistTokens) {
        this.blacklistTokens = blacklistTokens;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getId_UserName() {
        this.id_UserName=id+" "+this.userName;
        return id_UserName;
    }



    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                ", task=" + task +
                ", roles=" + roles +
                '}';
    }
}

