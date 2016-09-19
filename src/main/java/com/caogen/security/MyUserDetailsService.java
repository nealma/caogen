package com.caogen.security;

import com.caogen.dao.RoleMapper;
import com.caogen.dao.SysUserMapper;
import com.caogen.dao.UserRoleLinkMapper;
import com.caogen.domain.Role;
import com.caogen.domain.SysUser;
import com.caogen.domain.UserRoleLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * SPRING SECURITY用户登录处理
 * Created by neal on 9/14/16.
 */
@Service("MyUserDetailsServiceImpl")
public class MyUserDetailsService implements UserDetailsService {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleLinkMapper userRoleLinkMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LOGGER.info("loadUserByUsername --> [{}]", username);

        SysUser sysUser = new SysUser();
        sysUser.setUsername(username);
        List<SysUser> userList = sysUserMapper.select(sysUser);
        if (userList == null || userList.size() == 0) {
            throw new UsernameNotFoundException("username not found.");
        }
        sysUser = userList.get(0);
        UserRoleLink userRoleLink = new UserRoleLink();
        userRoleLink.setUserId(sysUser.getId());

        List<UserRoleLink> userRoleLinks = userRoleLinkMapper.select(userRoleLink);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

        Optional<List<UserRoleLink>> userRoleLinksOptional = Optional.of(userRoleLinks);
        userRoleLinksOptional.ifPresent(userRoleLinks1 -> {
            userRoleLinks1.forEach(userRoleLink1 -> {
                Role role = roleMapper.selectByPK(userRoleLink1.getRoleId());
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
                grantedAuthorities.add(grantedAuthority);
            });
        });
        return new User(username, sysUser.getPassword(), true, true, true, true, grantedAuthorities);
    }

}
