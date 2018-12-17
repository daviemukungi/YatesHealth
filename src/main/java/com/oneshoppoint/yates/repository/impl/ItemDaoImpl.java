package com.oneshoppoint.yates.repository.impl;

import com.oneshoppoint.yates.model.Item;
import com.oneshoppoint.yates.repository.ItemDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by robinson on 5/6/16.
 */
@Repository
public class ItemDaoImpl implements  ItemDao {
    @PersistenceContext
    EntityManager entityManager;

    public void save (Item item) {
        entityManager.merge(item);
        entityManager.flush();
    }

    public List<Item> getCart(Integer id) {
        TypedQuery<Item> query = entityManager.createQuery("SELECT i FROM Item i JOIN i.customer c WHERE c.id = :id AND i.type = 'cart' ",Item.class);
        query.setParameter("id",id);
        List<Item> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<Item> getWishlist(Integer id) {
        TypedQuery<Item> query = entityManager.createQuery("SELECT i FROM Item i JOIN i.customer c WHERE c.id = :id AND i.type = 'wishlist' ",Item.class);
        query.setParameter("id",id);
        List<Item> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<Item> getComparisonList(Integer id) {
        TypedQuery<Item> query = entityManager.createQuery("SELECT i FROM Item i JOIN i.customer c WHERE c.id = :id AND i.type = 'comparison' ",Item.class);
        query.setParameter("id",id);
        List<Item> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public void delete (Item item) {
        entityManager.remove(item);
        entityManager.flush();
    }
}
