package com.caogen.view;

import com.caogen.core.exception.AppException;
import com.caogen.core.web.BaseController;
import com.caogen.core.web.PromptMessage;
import com.caogen.domain.Resource;
import com.caogen.domain.Role;
import com.caogen.service.ResourceService;
import com.caogen.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.*;

/**
 * 菜单相关
 */
@RestController
public class MenuController extends BaseController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    @RolesAllowed({"ROLE_menus:view", "ROLE_root"})
    public String list(Resource resource) {

        List<Resource> list;
        PromptMessage promptMessage;
        try {
            Collection<GrantedAuthority> grantedAuthorities
                    = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
                                                            .getAuthentication().getAuthorities();

            if (grantedAuthorities == null || grantedAuthorities.size() == 0) {
                throw new AppException("User has no any role.");
            }

            LOGGER.error("{} \n{}", grantedAuthorities, SecurityContextHolder.getContext().getAuthentication().getDetails());
            List<String> grant = new ArrayList<>();
            grantedAuthorities.forEach(grantedAuthority -> {
                grant.add(grantedAuthority.getAuthority().replace("ROLE_",""));
            });
            list = resourceService.selectByResourceLink(grant.toArray(new String[0]));
            promptMessage = PromptMessage.createSuccessPrompt("0000", "  加载菜单成功");
            promptMessage.setResult(list);
        } catch (AppException e) {
            promptMessage = PromptMessage.createErrorPrompt("0000", "加载菜单失败");
            LOGGER.error("[MSG] -> {}", e.getMessage());
        }
        Map<String, String> error = new HashMap<>();
        error.put("username", SecurityContextHolder.getContext().getAuthentication().getName());
        promptMessage.setError(error);
        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/menus", method = RequestMethod.POST)
    @RolesAllowed({"ROLE_menus:create", "ROLE_root"})
    public String create(Resource resource) {
        PromptMessage promptMessage;
        try {
            List<Resource> list = new ArrayList<>();
            LOGGER.debug(renderJson(resource));
            resourceService.insert(resource);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "添加菜单成功");
            list.add(resource);
            promptMessage.setResult(list);
        } catch (AppException e) {
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "添加菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/menus", method = RequestMethod.PUT)
    @RolesAllowed({"ROLE_menus:update", "ROLE_root"})
    public String update(@Valid Resource resource, BindingResult bindingResult) {
        PromptMessage promptMessage;
        try {
            if (bindingResult.hasErrors()) {
                promptMessage = PromptMessage.createErrorPrompt("0000", "更新菜单失败xxxxx");
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                for (FieldError field : fieldErrors) {

                    LOGGER.debug("{}={}", field.getField(), field.getDefaultMessage());

                }
                return this.renderJson(promptMessage);
            }
            List<Resource> list = new ArrayList<>();
            resourceService.update(resource);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "更新菜单成功");
            list.add(resource);
            promptMessage.setResult(list);
        } catch (AppException e) {
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "更新菜单失败");
        }

        return this.renderJson(promptMessage);
    }
    @RolesAllowed({"ROLE_menus:delete", "ROLE_root"})
    @RequestMapping(value = "/menus/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id) {
        PromptMessage promptMessage;
        try {
            resourceService.delete(id);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "删除菜单成功");
        } catch (AppException e) {
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "删除菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/menus/{roleId}", method = RequestMethod.GET)
    @RolesAllowed({"ROLE_menus:view", "ROLE_root"})
    public String getMenuByRoleId(@PathVariable("roleId") Long id) {
        List<Resource> list;
        PromptMessage promptMessage;
        try {
            list = resourceService.selectByRoleId(id);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "  加载菜单成功");
            promptMessage.setResult(list);
        } catch (AppException e) {
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "加载菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/menus/grant")
    @RolesAllowed({"ROLE_menus:grant", "ROLE_root"})
    public String grant(Long id, String mids) {
        PromptMessage promptMessage;
        try {
            resourceService.grant(id, mids);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "授权成功");
        } catch (AppException e) {
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "授权失败");
        }

        return this.renderJson(promptMessage);
    }
}
