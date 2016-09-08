package com.caogen.service;

import com.caogen.core.service.BaseService;
import com.caogen.dao.ParamMapper;
import com.caogen.domain.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by neal on 9/8/16.
 */
@Service
public class ParamService implements BaseService<Param>{

    @Autowired
    private ParamMapper paramMapper;

    @Override
    public Param insert(Param param) {
        paramMapper.insert(param);
        return param;
    }

    @Override
    public int delete(Long id) {
        return paramMapper.deleteByPK(id);
    }

    @Override
    public Param update(Param param) {
        paramMapper.updateByPK(param);
        return param;
    }

    @Override
    public Param selectByPK(Long id) {
        return paramMapper.selectByPK(id);
    }

    @Override
    public List<Param> select(Param param) {
        param.setTotal(paramMapper.count(param));
        param.setResult(paramMapper.select(param));
        return param.getResult();
    }
}
