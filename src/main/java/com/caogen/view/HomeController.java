package com.caogen.view;

import com.caogen.core.web.BaseController;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 入口
 *
 */
@RestController
public class HomeController  extends BaseController {

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public ModelAndView home() {
        LOGGER.info("{}", "home");
        ModelMap modelMap = new ModelMap();
        return new ModelAndView("login", modelMap);
    }

    @RequestMapping(value = {"index"}, method = RequestMethod.GET)
    public ModelAndView index() {
        LOGGER.info("{}", "index");
        ModelMap modelMap = new ModelMap();
        return new ModelAndView("index", modelMap);
    }

    @RequestMapping(value = {"home"}, method = RequestMethod.GET)
    public ModelAndView  index1(ModelAndView modelAndView) {
        LOGGER.info("{}", "home");
        ModelMap modelMap = new ModelMap();
        return new ModelAndView("home/home", modelMap);

    }
}