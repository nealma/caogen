package com.caogen.security;

import com.caogen.dao.RoleMapper;
import com.caogen.dao.SysUserMapper;
import com.caogen.dao.UserRoleLinkMapper;
import com.caogen.domain.Resource;
import com.caogen.domain.SysUser;
import com.caogen.domain.UserRoleLink;
import com.caogen.service.ResourceService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    private ResourceService resourceService;
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

        List<Long> roleIds = new ArrayList<>();

        Optional<List<UserRoleLink>> userRoleLinksOptional = Optional.ofNullable(userRoleLinks);
        userRoleLinksOptional.ifPresent(userRoleLinks1 -> {
            userRoleLinks1.forEach(userRoleLink1 -> {
                roleIds.add(userRoleLink1.getRoleId());
                if(userRoleLink1.getRoleId() == 1L){
                    GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_root");//root角色特权
                    grantedAuthorities.add(grantedAuthority);
                }
            });
        });


//        List<Role> roles = roleMapper.selectBatch(roleIds);//V1.0
        List<Resource> resources = resourceService.selectByRoleId(roleIds.toArray(new Long[0]));

        if(resources != null && resources.size() > 0){
            resources.forEach(resource -> {
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_"+resource.getLink());//必须ROLE_为前缀
                grantedAuthorities.add(grantedAuthority);
            });
        }

        LOGGER.info("grantedAuthorities --> {}", grantedAuthorities);
        return new User(username, sysUser.getPassword(), true, true, true, true, grantedAuthorities);
    }

}
