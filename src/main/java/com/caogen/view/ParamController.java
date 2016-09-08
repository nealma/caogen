package com.caogen.view;

import com.caogen.core.domain.Page;
import com.caogen.core.exception.AppException;
import com.caogen.core.web.BaseController;
import com.caogen.core.web.PromptMessage;
import com.caogen.domain.Param;
import com.caogen.service.ParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public String create(Param param){
        PromptMessage promptMessage;
        try {
            List<Param> list = new ArrayList<>();
            paramService.insert(param);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "添加菜单成功");
            list.add(param);
            promptMessage.setResult(list);
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "添加菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/params", method = RequestMethod.PUT)
    public String update(@Valid Param param, BindingResult bindingResult){
        PromptMessage promptMessage;
        try {
            if(bindingResult.hasErrors()){
                promptMessage = PromptMessage.createErrorPrompt("0000", "更新菜单失败xxxxx");
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                for (FieldError field : fieldErrors) {

                    LOGGER.debug("{}={}",field.getField(), field.getDefaultMessage());

                }
                return this.renderJson(promptMessage);
            }
            List<Param> list = new ArrayList<>();
            paramService.update(param);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "更新菜单成功");
            list.add(param);
            promptMessage.setResult(list);
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "更新菜单失败");
        }

        return this.renderJson(promptMessage);
    }

    @RequestMapping(value = "/params/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id){
        PromptMessage promptMessage;
        try {
            paramService.delete(id);
            promptMessage = PromptMessage.createSuccessPrompt("0000", "删除菜单成功");
        } catch (AppException e){
            e.printStackTrace();
            promptMessage = PromptMessage.createErrorPrompt("0000", "删除菜单失败");
        }

        return this.renderJson(promptMessage);
    }
}
