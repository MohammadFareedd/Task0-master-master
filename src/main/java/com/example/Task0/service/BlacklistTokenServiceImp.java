package com.example.Task0.service;

import com.example.Task0.dao.BlacklistTokenRepository;
import com.example.Task0.entity.BlacklistToken;
import com.example.Task0.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void deleteToken(String name) {



         blacklistTokenRepository.deleteById(blacklistTokenRepository.findBlacklistTokenByName(name).getId());
    }

    @Override
    public void addNew(String name, User user) {
        blacklistTokenRepository.save(new BlacklistToken(name,user));

    }

    @Override
    public List<BlacklistToken> findAllVaildToken(int id) {
        return blacklistTokenRepository.findAllVaildToken(id);
    }
}
