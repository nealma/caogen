package com.caogen.domain;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SecureUser extends User {
    /**
     *  自增主键,所属表字段为t_sys_user.id
     */
    private Long id;

    /**
     *  名称,所属表字段为t_sys_user.username
     */
    private String username;

    /**
     *  密码,所属表字段为t_sys_user.password
     */
    private String password;

    public SecureUser(String username, String password, Collection<GrantedAuthority> authorities) {
        super(username, password, true, true, true, true, authorities);
    }
}