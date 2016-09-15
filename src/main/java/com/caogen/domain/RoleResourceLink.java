package com.caogen.domain;

import com.caogen.core.domain.Page;

public class RoleResourceLink extends Page {
    /**
     *  自增主键,所属表字段为t_sys_role_resource_link.id
     */
    private Long id;

    /**
     *  角色id,所属表字段为t_sys_role_resource_link.role_id
     */
    private Long roleId;

    /**
     *  用户id,所属表字段为t_sys_role_resource_link.resource_id
     */
    private Long resourceId;

    /**
     *  备注,所属表字段为t_sys_role_resource_link.remark
     */
    private String remark;

    /**
     *  创建时间,所属表字段为t_sys_role_resource_link.ctime
     */
    private Long ctime;

    /**
     *  最后更新时间,所属表字段为t_sys_role_resource_link.mtime
     */
    private Long mtime;

    /**
     *  状态 0:正常 1:删除,所属表字段为t_sys_role_resource_link.rstatus
     */
    private Byte rstatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getCtime() {
        return ctime;
    }

    public void setCtime(Long ctime) {
        this.ctime = ctime;
    }

    public Long getMtime() {
        return mtime;
    }

    public void setMtime(Long mtime) {
        this.mtime = mtime;
    }

    public Byte getRstatus() {
        return rstatus;
    }

    public void setRstatus(Byte rstatus) {
        this.rstatus = rstatus;
    }
}