package com.caogen.dao;

import com.caogen.domain.UserRoleLink;
import java.util.List;

public interface UserRoleLinkMapper {
    int insert(UserRoleLink userRoleLink);

    int deleteByPK(Long id);

    int updateByPK(UserRoleLink userRoleLink);

    UserRoleLink selectByPK(Long id);

    long count(UserRoleLink userRoleLink);

    List<UserRoleLink> select(UserRoleLink userRoleLink);
}