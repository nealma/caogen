package com.caogen.view;

import com.caogen.core.exception.AppException;
import com.caogen.core.web.BaseController;
import com.caogen.core.web.MsgOut;
import com.caogen.domain.Param;
import com.caogen.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单相关
 */
@RestController
public class ParamController extends BaseController{

    @Autowired
    private ParamService paramService;

    @RequestMapping(value = "/params", method = RequestMethod.GET)
    @RolesAllowed({"ROLE_params:view", "ROLE_root"})
    public String list(Param param){
        LOGGER.debug("page={}, rows={}", param.getPage(), param.getRows());
        try {
            paramService.select(param);
        } catch (AppException e){
            e.printStackTrace();
        }
        return this.renderPageJson(param);
    }

    @RequestMapping(value = "/params", method = RequestMethod.POST)
    @RolesAllowed({"ROLE_params:create", "ROLE_root"})
    public String create(Param param){
        MsgOut o;
        try {
            List<Param> list = new ArrayList<>();
            paramService.insert(param);
            list.add(param);
            o = MsgOut.success("添加参数成功", list);
        } catch (AppException e){
            e.printStackTrace();
            o = MsgOut.error("添加菜单失败");
        }

        return this.renderJson(o);
    }

    @RequestMapping(value = "/params", method = RequestMethod.PUT)
    @RolesAllowed({"ROLE_params:update", "ROLE_root"})
    public String update(@Valid Param param, BindingResult bindingResult){
        MsgOut o;
        try {
            if(bindingResult.hasErrors()){
                o = MsgOut.success("更新参数失败");
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                for (FieldError field : fieldErrors) {
                    LOGGER.debug("{}={}",field.getField(), field.getDefaultMessage());
                }
                return this.renderJson(o);
            }
            List<Param> list = new ArrayList<>();
            paramService.update(param);
            list.add(param);
            o = MsgOut.success("添加参数成功", list);
        } catch (AppException e){
            e.printStackTrace();
            o = MsgOut.success("更新参数失败");
        }

        return this.renderJson(o);
    }

    @RequestMapping(value = "/params/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({"ROLE_params:delete", "ROLE_root"})
    public String delete(@PathVariable("id") Long id){
        MsgOut o;
        try {
            paramService.delete(id);
            o = MsgOut.success("删除参数成功");
        } catch (AppException e){
            e.printStackTrace();
            o = MsgOut.success("删除参数失败");
        }

        return this.renderJson(o);
    }
}
