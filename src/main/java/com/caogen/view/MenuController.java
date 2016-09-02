package com.caogen.view;

import com.caogen.core.web.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 菜单相关
 */
@RestController
public class MenuController extends BaseController{

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public String hello(@PathVariable String name){
        return "hello " + name + "!";
    }

    @RequestMapping(value = "/menus", method = RequestMethod.POST)
    public String hello(@PathVariable String name){
        return "hello " + name + "!";
    }
}
