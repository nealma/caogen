package com.caogen.security;

import com.caogen.core.exception.AppException;
import com.caogen.domain.Role;
import com.caogen.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 验证资源跟角色之间的关系
 * Created by neal on 9/18/16.
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RoleService roleService;

    /**
     * // authentication 为用户所被赋予的权限, configAttributes 为访问相应的资源应该具有的权限。
     * @param authentication
     * @param object
     * @param configAttributes
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

        Collection<GrantedAuthority> userHasRoles =
                (Collection<GrantedAuthority>) authentication.getAuthorities();

        LOGGER.info("CurrentUser={} CurrentHasRoles = {}", authentication.getName(), Arrays.asList(userHasRoles));

        //放行[超级管理员]角色
        Iterator<GrantedAuthority> iterator = userHasRoles.iterator();
        while (iterator.hasNext()){
            GrantedAuthority grantedAuthority = iterator.next();
            if("系统管理员".equals(grantedAuthority.getAuthority())){
                return;
            }
        }
        LOGGER.info("1 CurrentUser={} CurrentHasRoles = {}", authentication.getName(), Arrays.asList(userHasRoles));

        Collection<GrantedAuthority> uriHasRoles = getGrantedAuthoritys(object);
        if (uriHasRoles == null || uriHasRoles.size() == 0) {
            return;
        }

        Optional<Collection<GrantedAuthority>> grantedAuthoritiesForOptional =
                Optional.ofNullable(userHasRoles);

        try{
            grantedAuthoritiesForOptional.ifPresent(userHasRolesNotNull -> {
                userHasRolesNotNull.forEach(userHasRole -> {
                    uriHasRoles.forEach(uriHasRole -> {
                        LOGGER.info("userHasRole={}, uriHasRole={}", userHasRole, uriHasRole);
                        if (userHasRole.getAuthority().equals(uriHasRole.getAuthority())) {
                            throw new AppException("break");
                        }
                    });
                });
            });
        }catch(AppException be){
            return;
        }

        throw new AccessDeniedException("Access Denied.");
    }

    private Collection<GrantedAuthority> getGrantedAuthoritys(Object object) {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        String uri = new StringBuilder(filterInvocation.getRequestUrl()).deleteCharAt(0).toString();
        if("".equals(uri)){
            return null;
        }
        List<Role> uriHasRoles = roleService.selectByResourceURI(uri);
        LOGGER.info("fullRequestUrl={}, requestUrl={}, uriHasRoles={}",
                filterInvocation.getFullRequestUrl(),
                filterInvocation.getRequestUrl(),
                uriHasRoles);

        if (uriHasRoles == null || uriHasRoles.size() == 0) {
            return null;
        }
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        uriHasRoles.forEach(item -> {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(item.getName());
            grantedAuthorities.add(grantedAuthority);
        });
        return grantedAuthorities;
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
