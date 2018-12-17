package com.oneshoppoint.yates.repository.impl;



import com.oneshoppoint.yates.repository.GenericRecursiveDao;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by robinson on 4/8/16.
 */

public class GenericRecursiveDaoImpl<T> extends GenericDaoImpl<T> implements GenericRecursiveDao<T> {
    @PersistenceContext
    EntityManager entityManager;
    private Class<T> type;

    public GenericRecursiveDaoImpl(Class<T> type) {
        super(type);
        this.type = type;
    }

    public T getRoot() {
        TypedQuery<T> query = entityManager.createQuery("SELECT t FROM "+type.getCanonicalName()+" t WHERE t.root = true AND t.deletedBy IS NULL AND t.deletedOn IS NULL",type);
        List<T> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public List<T> getAll() {
        TypedQuery<T> query = entityManager.createQuery("SELECT t FROM "+type.getCanonicalName()+" t WHERE t.root = true AND t.deletedBy IS NULL AND t.deletedOn IS NULL",type);
        List<T> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }
        return result;
    }
}
