package com.caogen.view;

import com.alibaba.fastjson.JSON;
import com.caogen.core.exception.AppException;
import com.caogen.core.web.BaseController;
import com.caogen.core.web.MsgOut;
import com.caogen.domain.Resource;
import com.caogen.service.ResourceService;
import com.caogen.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 菜单相关
 */
@RestController
public class MenuController extends BaseController {

    @Autowired private ResourceService resourceService;
    @Autowired private RoleService roleService;

    /**
     *
     * @api {get} /menus 获取菜单列表
     * @apiSampleRequest /menus
     * @apiExample {curl} Example usage:
     *     curl -i http://localhost:8080/menus
     * @apiPermission admin
     *
     * @apiName list
     * @apiGroup Menu
     * @apiVersion 0.1.0
     * @apiDescription 当前登录用户拥有的菜单
     *
     * @apiParam   {int}   page    页码
     * @apiParam   {int}   rows    页大小
     *
     * @apiSuccess {String} code    结果码
     * @apiSuccess {String} msg     消息说明
     * @apiSuccess {String} type    结果类型
     * @apiSuccess {String} title   提示标题
     * @apiSuccess {Object} data    分页数据
     * @apiSuccess {int}    total   总记录数
     *
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     *     code: '200',
     *     msg:  'success',
     *     total: 1,
     *     data:  {},
     *     type:  'SUCCESS',
     *     title: '成功'
     * }
     *
     * @apiError Menu 对应<code>ID</code>的菜单没有数据
     * @apiErrorExample Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     *     code: '404',
     *     msg:  'User Not Found',
     *     type: 'ERROR',
     *     title: '错误'
     * }
     */
    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    @RolesAllowed({"ROLE_menus:view", "ROLE_root"})
    public String list() {
        LOGGER.error("sessionId = {}", JSON.toJSON(RequestContextHolder.getRequestAttributes().getSessionId()));
        List<Resource> list;
        MsgOut o;
        try {
            Collection<GrantedAuthority> grantedAuthorities
                    = (Collection<GrantedAuthority>) SecurityContextHolder.getContext()
                                                            .getAuthentication().getAuthorities();

            if (grantedAuthorities == AuthorityUtils.NO_AUTHORITIES) {
               o = MsgOut.error("Access Deny.");
            }

            List<String> grant = new ArrayList<>();
            grantedAuthorities.forEach(grantedAuthority -> {
                grant.add(grantedAuthority.getAuthority().replace("ROLE_",""));
            });
            list = resourceService.selectByResourceLink(grant.toArray(new String[0]));
            o = MsgOut.success("加载菜单成功", list);
        } catch (AppException e) {
            o = MsgOut.error("加载菜单失败");
            e.printStackTrace();
        }
        o.setError(SecurityContextHolder.getContext().getAuthentication().getName());
        return this.renderJson(o);
    }

    /**
     *
     * @api {post} /menus 创建新菜单
     * @apiName  create
     * @apiHeader {String} access-key Users unique access-key.
     * @apiHeaderExample {json} Header-Example:
     *     {
     *       "Accept-Encoding": "Accept-Encoding: gzip, deflate"
     *     }
     * @apiGroup Menu
     * @apiVersion 0.1.0
     * @apiDescription 创建一个新菜单
     *
     * @apiParam   {String}  name   名称
     * @apiParam   {String}  link   菜单url
     * @apiParam   {Long}    pid    父级菜单ID
     *
     * @apiSuccess {String} code    结果码
     * @apiSuccess {String} msg     消息说明
     * @apiSuccess {String} type    结果类型
     * @apiSuccess {String} title   提示标题
     * @apiSuccess {Object} data    分页数据
     * @apiSuccess {int}    total   总记录数
     *
     * @apiSuccessExample Success-Response:
     * HTTP/1.1 200 OK
     * {
     *     code: '200',
     *     msg:  'success',
     *     total: 1,
     *     data:  {},
     *     type:  'SUCCESS',
     *     title: '成功'
     * }
     *
     * @apiError Menu 对应<code>ID</code>的菜单没有数据
     * @apiErrorExample Error-Response:
     * HTTP/1.1 404 Not Found
     * {
     *     code: '404',
     *     msg:  'User Not Found',
     *     type: 'ERROR',
     *     title: '错误'
     *
     * }
     */
    @RequestMapping(value = "/menus", method = RequestMethod.POST)
    @RolesAllowed({"ROLE_menus:create", "ROLE_root"})
    public String create(Resource resource) {
        MsgOut o;
        try {
            List<Resource> list = new ArrayList<>();
            LOGGER.debug(renderJson(resource));
            resourceService.insert(resource);
            list.add(resource);
            o = MsgOut.success("添加菜单成功", list);
        } catch (AppException e) {
            e.printStackTrace();
            o = MsgOut.error("添加菜单失败");
        }

        return this.renderJson(o);
    }

    @RequestMapping(value = "/menus", method = RequestMethod.PUT)
    @RolesAllowed({"ROLE_menus:update", "ROLE_root"})
    public String update(@Valid Resource resource, BindingResult bindingResult) {
        MsgOut o;
        try {
            if (bindingResult.hasErrors()) {
                o = MsgOut.error("更新加菜单失败");
                List<FieldError> fieldErrors = bindingResult.getFieldErrors();
                for (FieldError field : fieldErrors) {
                    LOGGER.debug("{}={}", field.getField(), field.getDefaultMessage());
                }
                return this.renderJson(o);
            }
            List<Resource> list = new ArrayList<>();
            resourceService.update(resource);
            list.add(resource);
            o = MsgOut.success("更新菜单成功", list);
        } catch (AppException e) {
            e.printStackTrace();
            o = MsgOut.error("更新菜单失败");
        }
        return this.renderJson(o);
    }
    @RolesAllowed({"ROLE_menus:delete", "ROLE_root"})
    @RequestMapping(value = "/menus/{id}", method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Long id) {
        MsgOut o;
        try {
            resourceService.delete(id);
            o = MsgOut.success("删除菜单成功");
        } catch (AppException e) {
            e.printStackTrace();
            o = MsgOut.error("删除菜单失败");
        }

        return this.renderJson(o);
    }

    @RequestMapping(value = "/menus/{roleId}", method = RequestMethod.GET)
    @RolesAllowed({"ROLE_menus:view", "ROLE_root"})
    public String getMenuByRoleId(@PathVariable("roleId") Long id) {
        List<Resource> list;
        MsgOut o;
        try {
            list = resourceService.selectByRoleId(id);
            o = MsgOut.success("获取角色资源成功", list);
            o.setData(list);
        } catch (AppException e) {
            e.printStackTrace();
            o = MsgOut.error("获取角色资源失败");
        }

        return this.renderJson(o);
    }

    @RequestMapping(value = "/menus/grant")
    @RolesAllowed({"ROLE_menus:grant", "ROLE_root"})
    public String grant(Long id, String mids) {
        MsgOut o;
        try {
            resourceService.grant(id, mids);
            o = MsgOut.success("授权成功");
        } catch (AppException e) {
            e.printStackTrace();
            o = MsgOut.error("授权失败");
        }

        return this.renderJson(o);
    }
}
