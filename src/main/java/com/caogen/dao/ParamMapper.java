package com.caogen.dao;

import com.caogen.domain.Param;
import java.util.List;

public interface ParamMapper {
    int insert(Param param);

    int deleteByPK(Long id);

    int updateByPK(Param param);

    Param selectByPK(Long id);

    long count(Param param);

    List<Param> select(Param param);
}