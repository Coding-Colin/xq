package com.community.system.mapper;

import java.util.List;

/**
 * CRUD接口
 * @param <T>
 */
public interface  BaseMapper<T> {

    List<T> getAll();

    T getById(int id);

    void add(T t);

    void update(T t);

    void delete(int id);

}
