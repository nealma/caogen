package com.caogen.service;

import com.caogen.dao.ResourceDao;
import com.caogen.domain.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by neal on 9/3/16.
 */
@Service
public class ResourceService {

    @Autowired
    private ResourceDao resourceDao;

    public Resource insert(Resource resource){
        resourceDao.insert(resource);
        return resource;
    }

    public Resource update(Resource resource){
        resourceDao.update(resource);
        return resource;
    }

    public int delete(Long id){

        return resourceDao.delete(id);
    }

    public Resource findOne(Resource resource){
        resourceDao.findOne(resource.getId());
        return resource;
    }

    public List<Resource> findList(){
        return resourceDao.findList();
    }
}
