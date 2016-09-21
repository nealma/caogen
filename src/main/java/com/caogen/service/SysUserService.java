package com.caogen.service;

import com.caogen.core.service.BaseService;
import com.caogen.dao.SysUserMapper;
import com.caogen.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by neal on 9/21/16.
 */
@Service
public class SysUserService implements BaseService<SysUser> {

    @Autowired private SysUserMapper sysUserMapper;
    @Override
    public SysUser insert(SysUser sysUser) {
        return null;
    }

    @Override
    public int delete(Long id) {
        return 0;
    }

    @Override
    public SysUser update(SysUser sysUser) {
        return null;
    }

    @Override
    public SysUser selectByPK(Long id) {
        return sysUserMapper.selectByPK(id);
    }

    @Override
    public List<SysUser> select(SysUser sysUser) {
        return sysUserMapper.select(sysUser);
    }
}
