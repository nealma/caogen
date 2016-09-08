package com.caogen.service;

import com.caogen.core.service.BaseService;
import com.caogen.dao.ResourceMapper;
import com.caogen.domain.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by neal on 9/3/16.
 */
@Service
public class ResourceService implements BaseService<Resource>{

    @Autowired
    private ResourceMapper resourceDao;

    public Resource insert(Resource resource){
        resourceDao.insert(resource);
        return resource;
    }

    public Resource update(Resource resource){
        resourceDao.updateByPK(resource);
        return resource;
    }

    public int delete(Long id){

        return resourceDao.deleteByPK(id);
    }

    public Resource selectByPK(Long id) {
        return resourceDao.selectByPK(id);
    }

    public List<Resource> select(Resource resource){
        return resourceDao.select(resource);
    }
}
