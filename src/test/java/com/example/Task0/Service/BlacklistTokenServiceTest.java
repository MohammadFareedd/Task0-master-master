package com.example.Task0.Service;

import com.example.Task0.dao.BlacklistTokenRepository;
import com.example.Task0.entity.BlacklistToken;
import com.example.Task0.entity.User;
import com.example.Task0.service.BlacklistTokenServiceImp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BlacklistTokenServiceTest {
    @Mock
    private BlacklistTokenRepository blacklistTokenRepository;
    @InjectMocks
    private BlacklistTokenServiceImp blacklistTokenServiceImp;
    @Test
    public void findBlacklistTokenByName_Test(){
        User user= Mockito.mock(User.class);
        BlacklistToken blacklistToken=new BlacklistToken("CMCDVCFER",user);
        when(blacklistTokenRepository.findBlacklistTokenByName(Mockito.anyString())).thenReturn(blacklistToken);
        BlacklistToken temp=blacklistTokenServiceImp.findBlacklistTokenByName("CMCDVCFER");
        assertThat(temp).isNotNull();
    }
    @Test
    public void deleteToken_Test(){
        String name="token";
        User user= Mockito.mock(User.class);
        BlacklistToken blacklistToken=new BlacklistToken(1,name,user);
        when(blacklistTokenRepository.findBlacklistTokenByName(Mockito.anyString())).thenReturn(blacklistToken);
        doNothing().when(blacklistTokenRepository).deleteById(Mockito.anyInt());
        assertAll(()->blacklistTokenServiceImp.deleteToken(name));

    }
    @Test
    public void addNew_Test(){
        User user= Mockito.mock(User.class);
        String name="token";
        BlacklistToken blacklistToken=new BlacklistToken(name,user);
        when(blacklistTokenRepository.save(Mockito.any(BlacklistToken.class))).thenReturn(blacklistToken);
        assertAll(()->blacklistTokenServiceImp.addNew(name,user));


    }
    @Test
    public void findAllValidToken_Test(){
        User user= Mockito.mock(User.class);
        BlacklistToken blacklistToken1=new BlacklistToken("CMCDVCFER",user);
        BlacklistToken blacklistToken2=new BlacklistToken("asewfef",user);
        BlacklistToken blacklistToken3=new BlacklistToken("rgqgeqr",user);
        List<BlacklistToken>list=new ArrayList<>();
        list.add(blacklistToken1);
        list.add(blacklistToken2);
        list.add(blacklistToken3);
        when(blacklistTokenRepository.findAllVaildToken(Mockito.anyInt())).thenReturn(list);
        List<BlacklistToken>temp=blacklistTokenServiceImp.findAllVaildToken(1);
        assertEquals(temp.size(),3);
    }

}
