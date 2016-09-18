package com.caogen.view;

import com.caogen.core.exception.AppException;
import com.caogen.core.web.BaseController;
import com.caogen.core.web.PromptMessage;
import com.caogen.domain.Resource;
import com.caogen.domain.Role;
import com.caogen.domain.RoleResourceLink;
import com.caogen.service.ResourceService;
import com.caogen.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单相关
 */
@RestController
public class MenuController extends BaseController{

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public String list(Resource resource){
        String authority = SecurityContextHolder.getContext().getAuthentication().getAuthorities().iterator().next().getAuthority();
        List<Resource> list;
        PromptMessage promptMessage;
        try {
            //根据角色过滤菜单
            Role role = new Role();
            role.setName(authority);
            role.setPage(1);
            role.setRows(10);
            List<Role> roles = roleService.select(role);
            role = roles.size() > 0 ? roleService.select(role).get(0) : role;

            list = resourceService.selectByRoleId(role.getId());
//            resource.setRows(1000);
//            list = resourceService.select(resource);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "  加载菜单成功");
            promptMessage.setResult(list);
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "加载菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/menus", method = RequestMethod.POST)
    public String create(Resource resource){
        PromptMessage promptMessage;
        try {
            List<Resource> list = new ArrayList<>();
            LOGGER.debug(renderJson(resource));
            resourceService.insert(resource);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "添加菜单成功");
            list.add(resource);
            promptMessage.setResult(list);
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "添加菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/menus", method = RequestMethod.PUT)
    public String update(@Valid Resource resource, BindingResult bindingResult){
        PromptMessage promptMessage;
        try {
            if(bindingResult.hasErrors()){
                promptMessage = PromptMessage.createErrorPrompt("0000", "更新菜单失败xxxxx");
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                for (FieldError field : fieldErrors) {

                    LOGGER.debug("{}={}",field.getField(), field.getDefaultMessage());

                }
                return this.renderJson(promptMessage);
            }
            List<Resource> list = new ArrayList<>();
            resourceService.update(resource);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "更新菜单成功");
            list.add(resource);
            promptMessage.setResult(list);
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "更新菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/menus/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id){
        PromptMessage promptMessage;
        try {
            resourceService.delete(id);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "删除菜单成功");
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "删除菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/menus/{roleId}", method = RequestMethod.GET)
    public String getMenuByRoleId(@PathVariable("roleId") Long id){
        List<Resource> list;
        PromptMessage promptMessage;
        try {
            list = resourceService.selectByRoleId(id);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "  加载菜单成功");
            promptMessage.setResult(list);
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "加载菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/menus/grant")
    public String delete(Long id, String mids){
        PromptMessage promptMessage;
        try {
            resourceService.grant(id, mids);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "授权成功");
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "授权失败");
        }

        return this.renderJson(promptMessage);
    }
}
