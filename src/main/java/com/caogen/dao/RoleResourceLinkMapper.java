package com.caogen.dao;

import com.caogen.domain.RoleResourceLink;
import java.util.List;

public interface RoleResourceLinkMapper {
    int insert(RoleResourceLink roleResourceLink);

    int deleteByPK(Long id);

    int updateByPK(RoleResourceLink roleResourceLink);

    RoleResourceLink selectByPK(Long id);

    long count(RoleResourceLink roleResourceLink);

    List<RoleResourceLink> select(RoleResourceLink roleResourceLink);

    int deleteByRoleId(Long roleId);
}