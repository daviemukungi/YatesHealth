package com.oneshoppoint.yates.repository;


import java.util.List;

/**
 * Created by robinson on 4/8/16.
 */
public interface GenericDao<T> {
    void save(T model);
    List<T> getAll();
    T find(Integer id);
    void update(T model);
    void delete(T model);
    T getByName(String name);
}
