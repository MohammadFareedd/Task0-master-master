package com.example.Task0.dao;

import com.example.Task0.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
//User Repository to deal with the database
public interface UserRepository extends JpaRepository<User,Integer> {
}
