package test.dao;

import java.util.List;
import test.model.Role;

public interface RoleMapper {
    int insert(Role role);

    int deleteByPK(Long id);

    int updateByPK(Role role);

    Role selectByPK(Long id);

    List<Role> select(Role role);
}