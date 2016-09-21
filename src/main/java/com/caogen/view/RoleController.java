package com.caogen.view;

import com.caogen.core.exception.AppException;
import com.caogen.core.web.BaseController;
import com.caogen.core.web.MsgOut;
import com.caogen.domain.Role;
import com.caogen.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单相关
 */
@RestController
public class RoleController extends BaseController{

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    @RolesAllowed({"ROLE_roles:view", "ROLE_root"})
    public String list(Role role){
        List<Role> list;
        MsgOut o;
        try {
            list = roleService.select(role);
            o = MsgOut.success("加载角色成功", list);
        } catch (AppException e){
            e.printStackTrace();
            o = MsgOut.error("加载角色失败");
        }

        return this.renderJson(o);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    @RolesAllowed({"ROLE_roles:create", "ROLE_root"})
    public String create(Role role){
        MsgOut o;
        try {
            List<Role> list = new ArrayList<>();
            LOGGER.debug(renderJson(role));
            roleService.insert(role);
            list.add(role);
            o = MsgOut.success("添加角色成功", list);
        } catch (AppException e){
            e.printStackTrace();
            o = MsgOut.error("添加角色失败");
        }

        return this.renderJson(o);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.PUT)
    @RolesAllowed({"ROLE_roles:update", "ROLE_root"})
    public String update(@Valid Role role, BindingResult bindingResult){
        MsgOut o;
        try {
            if(bindingResult.hasErrors()){
                o = MsgOut.error("更新角色失败");
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                for (FieldError field : fieldErrors) {
                    LOGGER.debug("{}={}",field.getField(), field.getDefaultMessage());
                }
                return this.renderJson(o);
            }
            List<Role> list = new ArrayList<>();
            roleService.update(role);
            list.add(role);
            o = MsgOut.success("更新角色成功", list);
        } catch (AppException e){
            e.printStackTrace();
            o = MsgOut.error("更新角色失败");
        }

        return this.renderJson(o);
    }

    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({"ROLE_roles:delete", "ROLE_root"})
    public String delete(@PathVariable("id") Long id){
        MsgOut o;
        try {
            roleService.delete(id);
            o = MsgOut.success("删除角色成功");
        } catch (AppException e){
            e.printStackTrace();
            o = MsgOut.error("删除角色失败");
        }

        return this.renderJson(o);
    }

}
