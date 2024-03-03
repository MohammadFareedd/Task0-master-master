package com.example.Task0.service;

import com.example.Task0.dao.BlacklistTokenRepository;
import com.example.Task0.entity.BlacklistToken;

public interface BlacklistTokenService {
    BlacklistToken findBlacklistTokenByName(String name);
     void addNew(String name);
}
