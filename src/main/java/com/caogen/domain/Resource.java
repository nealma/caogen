package com.caogen.domain;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by neal on 9/2/16.
 */
public class Resource {

    @NotNull
    @Min(0)
    private Long id;
    @NotNull(message = "name不能为空")
    @Size(min=2, max=50, message = "name长度在2到50之间")
    private String name;
    private String icon;
    @NotNull
    @Size(min = 1, max = 50)
    private String link;
    @NotNull
    @Min(0)
    private Long pId;
    private String remark;
    private Long ctime;
    private Long mtime;
    private Integer rstatus;

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
        this.name = name;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getRstatus() {
        return rstatus;
    }

    public void setRstatus(Integer rstatus) {
        this.rstatus = rstatus;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", url='" + link + '\'' +
                ", pid=" + pId +
                ", remark='" + remark + '\'' +
                ", ctime=" + ctime +
                ", mtime=" + mtime +
                ", rstatus=" + rstatus +
                '}';
    }
}
