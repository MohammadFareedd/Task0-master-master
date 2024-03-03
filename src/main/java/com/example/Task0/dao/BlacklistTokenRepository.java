package com.example.Task0.dao;

import com.example.Task0.entity.BlacklistToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken,Integer> {
    @Query(nativeQuery=true, value="SELECT u.* FROM Blacklist_token u WHERE u.name =:n")
    public BlacklistToken findBlacklistTokenByName(String n);
}
