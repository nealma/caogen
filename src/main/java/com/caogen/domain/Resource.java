package com.caogen.domain;

import com.caogen.core.domain.Page;

public class Resource extends Page {
    /**
     *  自增主键,所属表字段为t_sys_resource.id
     */
    private Long id;

    /**
     *  名称,所属表字段为t_sys_resource.name
     */
    private String name;

    /**
     *  图标,所属表字段为t_sys_resource.icon
     */
    private String icon;

    /**
     *  资源url,所属表字段为t_sys_resource.link
     */
    private String link;

    /**
     *  父资源id,所属表字段为t_sys_resource.pid
     */
    private Long pid;

    /**
     *  备注,所属表字段为t_sys_resource.remark
     */
    private String remark;

    /**
     *  创建时间,所属表字段为t_sys_resource.ctime
     */
    private Long ctime;

    /**
     *  最后更新时间,所属表字段为t_sys_resource.mtime
     */
    private Long mtime;

    /**
     *  状态 0:正常 1:删除,所属表字段为t_sys_resource.rstatus
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon == null ? null : icon.trim();
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
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