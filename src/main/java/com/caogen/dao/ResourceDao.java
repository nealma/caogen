package com.caogen.dao;

import com.caogen.domain.Resource;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by neal on 9/3/16.
 */
@Component
public interface ResourceDao {

    int insert(Resource resource);

    int update(Resource resource);

    int delete(@Param("id") Long id);

    Resource findOne(Long id);

    List<Resource> findList();
}
