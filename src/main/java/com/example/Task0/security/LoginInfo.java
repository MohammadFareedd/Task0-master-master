package com.example.Task0.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoginInfo extends User {
    private int id;
    public LoginInfo(String username, String password, Collection<? extends GrantedAuthority> authorities,int id) {
        super(username, password, authorities);
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return
                "id=" + id+" " +super.toString();

    }
}
