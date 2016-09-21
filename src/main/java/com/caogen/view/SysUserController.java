package com.caogen.view;

import com.caogen.core.web.BaseController;
import com.caogen.core.web.MsgOut;
import com.caogen.domain.Role;
import com.caogen.domain.SysUser;
import com.caogen.service.RoleService;
import com.caogen.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关
 */
@RestController
public class SysUserController extends BaseController{

    @Autowired private SysUserService sysUserService;

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String getUser(SysUser sysUser){
        Long userId = sysUser.getId();
        LOGGER.warn("userId={}", userId);
        return this.renderJson(MsgOut.success("Get User Info", sysUserService.selectByPK(userId)));
    }

}
