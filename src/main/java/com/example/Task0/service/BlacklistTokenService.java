package com.example.Task0.service;

import com.example.Task0.dao.BlacklistTokenRepository;
import com.example.Task0.entity.BlacklistToken;
import com.example.Task0.entity.User;

import java.util.List;

public interface BlacklistTokenService {
    BlacklistToken findBlacklistTokenByName(String name);
    void deleteToken( String name);
    void addNew( String name,User user);
    List<BlacklistToken> findAllVaildToken(int id);
}
