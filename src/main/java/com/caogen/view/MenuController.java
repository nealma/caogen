package com.caogen.view;

import com.caogen.core.exception.AppException;
import com.caogen.core.web.BaseController;
import com.caogen.core.web.PromptMessage;
import com.caogen.domain.Resource;
import com.caogen.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单相关
 */
@RestController
public class MenuController extends BaseController{

    @Autowired
    private ResourceService resourceService;

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public String list(Resource resource){
        List<Resource> list = null;
        PromptMessage promptMessage = null;
        try {
            list = resourceService.findList();
            promptMessage = PromptMessage.createSuccessPrompt("0000", "  加载菜单成功");
            promptMessage.setResult(list);
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "加载菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/menus", method = RequestMethod.POST)
    public String create(Resource resource){
        PromptMessage promptMessage = null;
        try {
            List<Resource> list = new ArrayList<Resource>();
            resourceService.insert(resource);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "添加菜单成功");
            list.add(resource);
            promptMessage.setResult(list);
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "添加菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/menus", method = RequestMethod.PUT)
    public String update(@Valid Resource resource, BindingResult bindingResult){
        PromptMessage promptMessage = null;
        try {
            if(bindingResult.hasErrors()){
                promptMessage = PromptMessage.createErrorPrompt("0000", "更新菜单失败");
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                for (FieldError field : fieldErrors) {

                    LOGGER.debug("{}={}",field.getField(), field.getDefaultMessage());

                }
                return this.renderJson(promptMessage);
            }
            List<Resource> list = new ArrayList<Resource>();
            resourceService.update(resource);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "更新菜单成功");
            list.add(resource);
            promptMessage.setResult(list);
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "更新菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/menus/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id){
        PromptMessage promptMessage = null;
        try {
            resourceService.delete(id);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "删除菜单成功");
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "删除菜单失败");
        }

        return this.renderJson(promptMessage);
    }
}
