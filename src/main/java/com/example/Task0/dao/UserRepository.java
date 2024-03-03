package com.example.Task0.dao;

import com.example.Task0.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//User Repository to deal with the database
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(nativeQuery=true, value="SELECT u.* FROM user u WHERE u.email =:email")
    public User findUserByEmail(String email);

}
