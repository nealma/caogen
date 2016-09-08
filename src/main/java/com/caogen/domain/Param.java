package com.caogen.domain;

import com.caogen.core.domain.Page;

public class Param extends Page {
    /**
     *  自增主键,所属表字段为t_sys_param.id
     */
    private Long id;

    /**
     *  名称,所属表字段为t_sys_param.name
     */
    private String name;

    /**
     *  父角色id,所属表字段为t_sys_param.value
     */
    private String value;

    /**
     *  备注,所属表字段为t_sys_param.remark
     */
    private String remark;

    /**
     *  创建时间,所属表字段为t_sys_param.ctime
     */
    private Long ctime;

    /**
     *  最后更新时间,所属表字段为t_sys_param.mtime
     */
    private Long mtime;

    /**
     *  状态 0:正常 1:删除,所属表字段为t_sys_param.rstatus
     */
    private Byte rstatus;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
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