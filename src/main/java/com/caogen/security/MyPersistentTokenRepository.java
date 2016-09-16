package com.caogen.security;

import com.caogen.dao.RememberMeMapper;
import com.caogen.domain.RememberMe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.*;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 记住我持久化方式
 * Created by neal on 9/16/16.
 */
@Component
public class MyPersistentTokenRepository implements PersistentTokenRepository {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RememberMeMapper remembermeMapper;

    @Override
    public void createNewToken(PersistentRememberMeToken persistentRememberMeToken) {
        RememberMe rememberMe = new RememberMe();
        rememberMe.setUsername(persistentRememberMeToken.getUsername());
        rememberMe.setSeries(persistentRememberMeToken.getSeries());
        rememberMe.setDate(persistentRememberMeToken.getDate());
        rememberMe.setTokenValue(persistentRememberMeToken.getTokenValue());
        remembermeMapper.insert(rememberMe);
    }

    @Override
    public void updateToken(String s, String s1, Date date) {
        RememberMe rememberMe = new RememberMe();
        rememberMe.setUsername("");
        rememberMe.setSeries(s);
        rememberMe.setTokenValue(s1);
        rememberMe.setDate(date);
        remembermeMapper.updateByPK(rememberMe);
    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String s) {
        RememberMe rememberMe = remembermeMapper.selectByPK(s);
        PersistentRememberMeToken persistentRememberMeToken =
                new PersistentRememberMeToken(rememberMe.getUsername(),
                        rememberMe.getSeries(),
                        rememberMe.getTokenValue(),
                        rememberMe.getDate()
                );
        return persistentRememberMeToken;
    }

    @Override
    public void removeUserTokens(String s) {
        remembermeMapper.deleteByPK(s);
    }
}
