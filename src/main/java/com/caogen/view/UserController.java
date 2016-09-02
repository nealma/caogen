package com.caogen.view;

import com.caogen.core.web.BaseController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关
 */
@RestController
public class UserController extends BaseController{
    @RequestMapping("/users/{name}")
    public String hello(@PathVariable String name){
        return "hello " + name + "!";
    }
}
