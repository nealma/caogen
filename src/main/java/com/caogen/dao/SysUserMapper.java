package com.caogen.dao;

import com.caogen.domain.SysUser;
import java.util.List;

public interface SysUserMapper {
    int insert(SysUser sysUser);

    int deleteByPK(Long id);

    int updateByPK(SysUser sysUser);

    SysUser selectByPK(Long id);

    long count(SysUser sysUser);

    List<SysUser> select(SysUser sysUser);
}