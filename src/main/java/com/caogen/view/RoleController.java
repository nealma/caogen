package com.caogen.view;

import com.caogen.core.exception.AppException;
import com.caogen.core.web.BaseController;
import com.caogen.core.web.PromptMessage;
import com.caogen.domain.Role;
import com.caogen.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public String list(Role role){
        List<Role> list;
        PromptMessage promptMessage;
        try {
            role.setPage(1);
            role.setRows(1000);
            list = roleService.select(role);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "  加载菜单成功");
            promptMessage.setResult(list);
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "加载菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public String create(Role role){
        PromptMessage promptMessage;
        try {
            List<Role> list = new ArrayList<>();
            LOGGER.debug(renderJson(role));
            roleService.insert(role);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "添加菜单成功");
            list.add(role);
            promptMessage.setResult(list);
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "添加菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/roles", method = RequestMethod.PUT)
    public String update(@Valid Role role, BindingResult bindingResult){
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
            List<Role> list = new ArrayList<>();
            roleService.update(role);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "更新菜单成功");
            list.add(role);
            promptMessage.setResult(list);
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "更新菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/roles/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id){
        PromptMessage promptMessage;
        try {
            roleService.delete(id);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "删除菜单成功");
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "删除菜单失败");
        }

        return this.renderJson(promptMessage);
    }

}
