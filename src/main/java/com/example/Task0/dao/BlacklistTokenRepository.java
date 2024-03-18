package com.example.Task0.dao;

import com.example.Task0.entity.BlacklistToken;

import com.example.Task0.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BlacklistTokenRepository extends JpaRepository<BlacklistToken,Integer> {
    @Query(nativeQuery=true, value="SELECT u.* FROM blacklist_token u  where u.name =:name")
    public BlacklistToken findBlacklistTokenByName(String name);
    @Query(nativeQuery=true, value="SELECT u.* FROM blacklist_token u WHERE   u.user_id =:id")
    public List<BlacklistToken> findAllVaildToken(int id);
}
