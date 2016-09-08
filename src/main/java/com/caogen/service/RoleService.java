package com.caogen.service;

import com.caogen.core.service.BaseService;
import com.caogen.dao.RoleMapper;
import com.caogen.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by neal on 9/7/16.
 */
@Service
public class RoleService implements BaseService<Role>{

    @Autowired
    private RoleMapper roleMapper;

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
        return roleMapper.select(role);
    }

}
