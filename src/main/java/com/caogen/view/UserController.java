package com.caogen.view;

import com.caogen.core.web.BaseController;
import com.caogen.domain.Role;
import com.caogen.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关
 */
@RestController
public class UserController extends BaseController{

    @Autowired
    private RoleService roleService;

    @RequestMapping("/users/{name}")
    public String hello(@PathVariable String name){
        Role role = new Role();
        role.setName(name);
        role.setPid(2L);
        roleService.insert(role);
//        roleService.deleteByPK(role.getId());
        return this.renderJson(roleService.select(role));
    }

}
