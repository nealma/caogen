package com.caogen.domain;

import com.caogen.core.domain.Page;

import java.util.List;

public class SysUser extends Page {
    /**
     *  自增主键,所属表字段为t_sys_user.id
     */
    private Long id;

    /**
     *  名称,所属表字段为t_sys_user.username
     */
    private String username;

    /**
     *  密码,所属表字段为t_sys_user.password
     */
    private String password;

    /**
     *  部门,所属表字段为t_sys_user.department
     */
    private String department;

    /**
     *  手机号,所属表字段为t_sys_user.phone
     */
    private String phone;

    /**
     *  职位,所属表字段为t_sys_user.position
     */
    private String position;

    /**
     *  备注,所属表字段为t_sys_user.remark
     */
    private String remark;

    /**
     *  邮箱,所属表字段为t_sys_user.email
     */
    private String email;

    /**
     *  创建时间,所属表字段为t_sys_user.ctime
     */
    private Long ctime;

    /**
     *  最后更新时间,所属表字段为t_sys_user.mtime
     */
    private Long mtime;

    /**
     *  状态 0:正常 1:删除,所属表字段为t_sys_user.rstatus
     */
    private Byte rstatus;

    private List<Resource> resource;

    public List<Resource> getResource() {
        return resource;
    }

    public void setResource(List<Resource> resource) {
        this.resource = resource;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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