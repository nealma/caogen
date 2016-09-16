package com.caogen.dao;

import com.caogen.domain.RememberMe;

public interface RememberMeMapper {
    int insert(RememberMe param);

    int deleteByPK(String series);

    int updateByPK(RememberMe param);

    RememberMe selectByPK(String series);
}