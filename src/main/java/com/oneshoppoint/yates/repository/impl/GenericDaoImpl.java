package com.oneshoppoint.yates.repository.impl;



import com.oneshoppoint.yates.repository.GenericDao;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by robinson on 4/8/16.
 */

public class GenericDaoImpl<T> implements GenericDao<T> {
    @PersistenceContext
    EntityManager entityManager;
    private Class<T> type;

    public GenericDaoImpl (Class<T> type) {
        this.type = type;
    }

    public void save (T domain) {
        entityManager.persist(domain);
        entityManager.flush();
    }

    public T find(Integer id) {
        TypedQuery<T> query = entityManager.createQuery("SELECT t FROM "+type.getCanonicalName()+" t WHERE t.id = :id AND t.deletedBy IS NULL AND t.deletedOn IS NULL ",type);
        query.setParameter("id",id);
        List<T> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public void update (T domain) {
        entityManager.merge(domain);
        entityManager.flush();
    }

    public void delete (T domain) {
        entityManager.merge(domain);
        entityManager.flush();
    }

    public List<T> getAll() {
        TypedQuery<T> query = entityManager.createQuery("SELECT t FROM "+type.getCanonicalName()+" t WHERE  t.deletedBy IS NULL AND t.deletedOn IS NULL ",type);
        List<T> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public T getByName(String name) {
        TypedQuery<T> query = entityManager.createQuery("SELECT t FROM "+type.getCanonicalName()+" t WHERE  t.name = :name AND t.deletedBy IS NULL AND t.deletedOn IS NULL ",type);
        query.setParameter("name",name);
        List<T> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }
}
