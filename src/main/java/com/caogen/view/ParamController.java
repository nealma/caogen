package com.caogen.view;

import com.caogen.core.web.BaseController;
import com.caogen.core.web.MsgOut;
import com.caogen.domain.Param;
import com.caogen.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
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
        MsgOut o = MsgOut.success();
        paramService.select(param);
        o.setRows(param.getResult());
        o.setTotal(param.getTotal());
        return this.renderJson(o);
    }

    @RequestMapping(value = "/params", method = RequestMethod.POST)
    @RolesAllowed({"ROLE_params:create", "ROLE_root"})
    public String create(Param param){
        MsgOut o;
        List<Param> list = new ArrayList<>();
        paramService.insert(param);
        list.add(param);
        o = MsgOut.success(list);
        return this.renderJson(o);
    }

    @RequestMapping(value = "/params", method = RequestMethod.PUT)
    @RolesAllowed({"ROLE_params:update", "ROLE_root"})
    public String update(@Valid Param param){
        MsgOut o;
        List<Param> list = new ArrayList<>();
        paramService.update(param);
        list.add(param);
        o = MsgOut.success(list);
        return this.renderJson(o);
    }

    @RequestMapping(value = "/params/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({"ROLE_params:delete", "ROLE_root"})
    public String delete(@PathVariable("id") Long id){
        MsgOut o;
        paramService.delete(id);
        o = MsgOut.success();
        return this.renderJson(o);
    }
}
