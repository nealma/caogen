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

import java.util.List;

/**
 * 入口
 *
 */
@RestController
public class HomeController  extends BaseController {

    @RequestMapping(value = {"", "/", "/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        LOGGER.info("{}", "home");
        LOGGER.debug("[Security Context Holder]{}", SecurityContextHolder.getContext().getAuthentication().getName());
        return new ModelAndView("login");
    }

    @RequestMapping(value = {"index"}, method = RequestMethod.GET)
    public ModelAndView index() {
        LOGGER.info("{}", "index");
        return new ModelAndView("index");
    }

    @RequestMapping(value = {"home"}, method = RequestMethod.GET)
    public ModelAndView home(Authentication authentication) {

        LOGGER.debug("[Security Context Holder]{}", SecurityContextHolder.getContext().getAuthentication());
        if(authentication != null){
            for (GrantedAuthority authority : authentication.getAuthorities()) {
                String role = authority.getAuthority();
                LOGGER.info("{}", "home " + role);
            }
        }

        return new ModelAndView("home/home");

    }
}