package com.caogen.view;

import com.caogen.core.web.BaseController;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * 入口
 *
 */
@RestController
public class PageController extends BaseController {

    /**
     * 登录页面
     * @return
     */
    @RequestMapping(value = {"", "/", "/login"}, method = RequestMethod.GET)
    @PermitAll
    public ModelAndView login() {
        LOGGER.info("|--> {}", this.getRequest().getRequestURI());
        return new ModelAndView("login");
    }

    /**
     * 成功登录后页面
     * @return
     */
    @RequestMapping(value = "index", method = RequestMethod.GET)
    @PermitAll
    public ModelAndView index() {
        LOGGER.info("|--> {}", this.getRequest().getRequestURI());
        return new ModelAndView("index");
    }

    /**
     * 主页
     * @param
     * @return
     */
    @RequestMapping(value = "home", method = RequestMethod.GET)
    @PermitAll
    public ModelAndView home() {
        LOGGER.info("|--> {}", this.getRequest().getRequestURI());
        return new ModelAndView("home/home");

    }

    /**
     * 角色页
     * @param
     * @return
     */
    @RequestMapping(value = "role", method = RequestMethod.GET)
    @RolesAllowed({"ROLE_role", "ROLE_root"})
    public ModelAndView role() {
        LOGGER.info("|--> {}", this.getRequest().getRequestURI());
        return new ModelAndView("sys/role");
    }

    /**
     * 菜单页
     * @return
     */
    @RequestMapping(value = "menu", method = RequestMethod.GET)
    @RolesAllowed({"ROLE_menu", "ROLE_root"})
    public ModelAndView menu() {
        LOGGER.info("|--> {}", this.getRequest().getRequestURI());
        return new ModelAndView("sys/menu");
    }

    /**
     * 系统参数页
     * @return
     */
    @RequestMapping(value = "param", method = RequestMethod.GET)
    @RolesAllowed({"ROLE_param", "ROLE_root"})
    public ModelAndView param() {
        LOGGER.info("|--> {}", this.getRequest().getRequestURI());
        return new ModelAndView("sys/param");
    }
}