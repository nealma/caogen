package test.model;

public class Role {
    /**
     *  自增主键,所属表字段为t_sys_role.id
     */
    private Long id;

    /**
     *  名称,所属表字段为t_sys_role.name
     */
    private String name;

    /**
     *  父角色id,所属表字段为t_sys_role.pid
     */
    private Long pid;

    /**
     *  备注,所属表字段为t_sys_role.remark
     */
    private String remark;

    /**
     *  创建时间,所属表字段为t_sys_role.ctime
     */
    private Long ctime;

    /**
     *  最后更新时间,所属表字段为t_sys_role.mtime
     */
    private Long mtime;

    /**
     *  状态 0:正常 1:删除,所属表字段为t_sys_role.rstatus
     */
    private String rstatus;

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

    public String getRstatus() {
        return rstatus;
    }

    public void setRstatus(String rstatus) {
        this.rstatus = rstatus == null ? null : rstatus.trim();
    }
}