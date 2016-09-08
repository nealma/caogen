package com.caogen.core.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.caogen.core.domain.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller层父类，包括一些常用的方法
 */
public abstract class BaseController {

    protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    protected String renderJson(Object object){
        return JSON.toJSONString(object);
    }

    protected String renderPageJson(Page object) {
        JSONObject json = new JSONObject();
        json.put("total", object.getTotal());
        json.put("rows", object.getResult());
        return JSON.toJSONString(json);
    }

    protected HttpServletRequest getRequest(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return servletRequestAttributes.getRequest();
    }

    protected HttpServletResponse getResponse(){
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getResponse();
    }
}