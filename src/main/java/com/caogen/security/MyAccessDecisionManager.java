package com.caogen.security;

import com.caogen.core.exception.AppException;
import com.caogen.domain.Role;
import com.caogen.service.RoleService;
import com.mysql.jdbc.log.Log;
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
 * 权限控制
 * Created by neal on 9/18/16.
 */
@Component
public class MyAccessDecisionManager implements AccessDecisionManager {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private RoleService roleService;

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
        LOGGER.info("1 authentication={}", authentication.getAuthorities());

        Collection<GrantedAuthority> uriHasRoles = getGrantedAuthoritys(object);
        if (uriHasRoles == null || uriHasRoles.size() == 0) {
            return;
        }
        Collection<GrantedAuthority> userHasRoles =
                (Collection<GrantedAuthority>) authentication.getAuthorities();
        if (userHasRoles != null && userHasRoles.size() > 0) {

            try{
                userHasRoles.forEach(userHasRole -> {
                    uriHasRoles.forEach(uriHasRole -> {
                        LOGGER.info("userHasRole={}, uriHasRole={}", userHasRole, uriHasRole);
                        if (userHasRole.getAuthority().equals(uriHasRole.getAuthority())) {
                            LOGGER.info("3 authentication={}", authentication.getAuthorities());
                            throw new AppException("break");
                        }
                    });
                });
            }catch(AppException be){
                return;
            }

        }
        LOGGER.info("2 authentication={}", authentication.getAuthorities());
        throw new AccessDeniedException("Access Denied.");
    }

    private Collection<GrantedAuthority> getGrantedAuthoritys(Object object) {
        FilterInvocation filterInvocation = (FilterInvocation) object;
        List<Role> uriHasRoles = roleService.selectByResourceURI(
                new StringBuilder(filterInvocation.getRequestUrl()).deleteCharAt(0).toString());
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
