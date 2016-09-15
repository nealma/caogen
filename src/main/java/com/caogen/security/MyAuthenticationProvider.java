package com.caogen.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * 自定义验证方式
 * Created by neal on 9/14/16.
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider{

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private MyUserDetailsService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        UserDetails user = userService.loadUserByUsername(username);

        LOGGER.info(">>>>>>>>>>>>>>>>>>>>>>>>>>{}<<<<<<<<<<<<<<<<<<<<<<<<", user.getUsername());
        if(user == null){
            throw new BadCredentialsException("Username not found.");
        }
        LOGGER.info(">>>>>>>>>{}:{}<<<<<<<<<", password, user.getPassword());
        //加密过程在这里体现
        if (!new BCryptPasswordEncoder().matches(password, user.getPassword())) {
            throw new BadCredentialsException("Wrong password.");
        }

        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        return new UsernamePasswordAuthenticationToken(user, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
