package com.caogen.dao;

import com.caogen.domain.Role;
import java.util.List;

public interface RoleMapper {
    int insert(Role role);

    int deleteByPK(Long id);

    int updateByPK(Role role);

    Role selectByPK(Long id);

    long count(Role role);

    List<Role> select(Role role);
}