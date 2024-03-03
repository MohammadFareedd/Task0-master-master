package com.example.Task0.dao;

import com.example.Task0.entity.Role;
import com.example.Task0.entity.Task;
import com.example.Task0.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    @Query(nativeQuery=true, value="SELECT u.* FROM role u WHERE u.role =:name")
    public Role findRoleByName(String name);

}
