package com.caogen.service;

import com.caogen.core.service.BaseService;
import com.caogen.dao.ResourceMapper;
import com.caogen.dao.RoleMapper;
import com.caogen.dao.RoleResourceLinkMapper;
import com.caogen.domain.Param;
import com.caogen.domain.Resource;
import com.caogen.domain.Role;
import com.caogen.domain.RoleResourceLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by neal on 9/7/16.
 */
@Service
public class RoleService implements BaseService<Role> {
    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private ResourceMapper resourceMapper;

    @Autowired
    private RoleResourceLinkMapper roleResourceLinkMapper;

    public Role insert(Role role) {
        roleMapper.insert(role);
        return role;
    }

    public int delete(Long id) {
        return roleMapper.deleteByPK(id);
    }

    public Role update(Role role) {
        roleMapper.updateByPK(role);
        return role;
    }

    public Role selectByPK(Long id) {
        return roleMapper.selectByPK(id);
    }

    public List<Role> select(Role role) {
        role.setTotal(roleMapper.count(role));
        role.setResult(roleMapper.select(role));
        return role.getResult();
    }

    public List<Role> selectByResourceURI(String uri) {

        Resource resource = new Resource();
        resource.setLink(uri);
        List<Resource> resources = resourceMapper.select(resource);
        if (resources == null || resources.size() == 0) {
            return null;
        }
        resource = resources.get(0);
        RoleResourceLink roleResourceLink = new RoleResourceLink();
        roleResourceLink.setResourceId(resource.getId());
        List<RoleResourceLink> roleResourceLinks = roleResourceLinkMapper.select(roleResourceLink);

        if (roleResourceLinks == null || roleResourceLinks.size() == 0) {
            return null;
        }

        List<Long> roleIds = new ArrayList<>();
        roleResourceLinks.forEach(item -> {
            roleIds.add(item.getRoleId());
        });

        return roleMapper.selectBatch(roleIds);
    }
}
