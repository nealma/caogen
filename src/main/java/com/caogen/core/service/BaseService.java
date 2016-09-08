package com.caogen.core.service;

import java.util.List;

/**
 * Created by neal on 9/7/16.
 */
public interface BaseService<T> {
    T insert(T t);

    int delete(Long id);

    T update(T t);

    T selectByPK(Long id);

    List<T> select(T t);
}
