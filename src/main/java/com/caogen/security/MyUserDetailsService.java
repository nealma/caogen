package com.caogen.security;

import com.caogen.dao.RoleMapper;
import com.caogen.dao.SysUserMapper;
import com.caogen.dao.UserRoleLinkMapper;
import com.caogen.domain.Role;
import com.caogen.domain.SysUser;
import com.caogen.domain.UserRoleLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义查询用户信息
 * Created by neal on 9/14/16.
 */
@Service("MyUserDetailsServiceImpl")
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleLinkMapper userRoleLinkMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser sysUser = new SysUser();
        sysUser.setUsername(s);
        sysUser.setRows(1);
        sysUser.setPage(1);
        List<SysUser> userList = sysUserMapper.select(sysUser);
        if (userList == null || userList.size() == 0) {
            throw new UsernameNotFoundException("username not found.");
        }
        sysUser = userList.get(0);
        UserRoleLink userRoleLink = new UserRoleLink();
        userRoleLink.setUserId(sysUser.getId());
        userRoleLink.setPage(1);
        userRoleLink.setRows(Integer.MAX_VALUE);

        List<UserRoleLink> userRoleLinks = userRoleLinkMapper.select(userRoleLink);
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if (userRoleLinks != null && userRoleLinks.size() > 0) {
            userRoleLinks.forEach(userRoleLink1 -> {
                Role role = roleMapper.selectByPK(userRoleLink1.getRoleId());
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
                grantedAuthorities.add(grantedAuthority);
            });
        }

        org.springframework.security.core.userdetails.User securityUser
                = new org.springframework.security.core.userdetails.User(
                sysUser.getUsername(), sysUser.getPassword(), grantedAuthorities);
        UserDetails userDetails = securityUser;
        return userDetails;
    }

}
