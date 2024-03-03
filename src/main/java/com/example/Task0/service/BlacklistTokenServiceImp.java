package com.example.Task0.service;

import com.example.Task0.dao.BlacklistTokenRepository;
import com.example.Task0.entity.BlacklistToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlacklistTokenServiceImp implements BlacklistTokenService {
    private BlacklistTokenRepository blacklistTokenRepository;

    @Autowired
    public BlacklistTokenServiceImp(BlacklistTokenRepository blacklistTokenRepository) {
        this.blacklistTokenRepository = blacklistTokenRepository;
    }

    @Override
    public BlacklistToken findBlacklistTokenByName(String name) {

        return blacklistTokenRepository.findBlacklistTokenByName(name);
    }

    @Override
    public void addNew(String name) {

        BlacklistToken temp = new BlacklistToken(name);
         blacklistTokenRepository.save(temp);
    }
}
