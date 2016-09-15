package com.caogen.dao;

import com.caogen.domain.Resource;

import java.util.List;

public interface ResourceMapper {
    int insert(Resource resource);

    int deleteByPK(Long id);

    int updateByPK(Resource resource);

    Resource selectByPK(Long id);

    long count(Resource resource);

    List<Resource> select(Resource resource);

    List<Resource> selectBatch(List<Long> resourceIds);
}