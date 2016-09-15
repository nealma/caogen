package com.caogen.service;

import com.caogen.core.service.BaseService;
import com.caogen.dao.ResourceMapper;
import com.caogen.dao.RoleResourceLinkMapper;
import com.caogen.domain.Resource;
import com.caogen.domain.Role;
import com.caogen.domain.RoleResourceLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by neal on 9/3/16.
 */
@Service
public class ResourceService implements BaseService<Resource>{

    @Autowired
    private ResourceMapper resourceMapper;
    @Autowired
    private RoleResourceLinkMapper roleResourceLinkMapper;

    public Resource insert(Resource resource){
        resourceMapper.insert(resource);
        return resource;
    }

    public Resource update(Resource resource){
        resourceMapper.updateByPK(resource);
        return resource;
    }

    public int delete(Long id){

        return resourceMapper.deleteByPK(id);
    }

    public Resource selectByPK(Long id) {
        return resourceMapper.selectByPK(id);
    }

    public List<Resource> select(Resource resource){
        return resourceMapper.select(resource);
    }

    public List<Resource> selectByRoleId(Long roleId){
        if(roleId == null || roleId == 0){
            return  null;
        }
        RoleResourceLink roleResourceLink = new RoleResourceLink();
        roleResourceLink.setPage(1);
        roleResourceLink.setRoleId(roleId);
        roleResourceLink.setRows(1000);
        List<RoleResourceLink> roleResourceLinks = roleResourceLinkMapper.select(roleResourceLink);

        if(roleResourceLinks == null || roleResourceLinks.size() == 0){
            return null;
        }
        List<Long> resourceIds = new ArrayList<>();
        roleResourceLinks.forEach(item -> {
            resourceIds.add(item.getResourceId());

        });
        return resourceMapper.selectBatch(resourceIds);
    }

    public void grant(Long roleId, String menuIds){

        if (menuIds == null){
            return;
        }
        roleResourceLinkMapper.deleteByRoleId(roleId);
        Arrays
        .asList(menuIds.split(","))
        .stream()
        .map(item -> Long.valueOf(item))
        .forEach(resourceId ->
        {
            RoleResourceLink roleResourceLink = new RoleResourceLink();
            roleResourceLink.setRoleId(roleId);
            roleResourceLink.setResourceId(resourceId);
            roleResourceLinkMapper.insert(roleResourceLink);

        });
    }
}
