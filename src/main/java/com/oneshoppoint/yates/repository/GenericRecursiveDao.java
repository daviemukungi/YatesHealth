package com.oneshoppoint.yates.repository;

/**
 * Created by robinson on 4/11/16.
 */
public interface GenericRecursiveDao <T> extends GenericDao<T> {
    T getRoot ();
}
