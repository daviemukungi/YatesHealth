package com.oneshoppoint.yates.repository.impl;

import com.oneshoppoint.yates.model.Retailer;
import com.oneshoppoint.yates.repository.RetailerDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by robinson on 4/12/16.
 */
@Repository
public class RetailerDaoImpl extends GenericDaoImpl<Retailer> implements RetailerDao {
    @PersistenceContext
    private EntityManager entityManager;

    public RetailerDaoImpl() {
        super(Retailer.class);
    }

    public List<Retailer> search (String pattern){
        //to be implemented later
        return null;
    }

    public List<Retailer> getByLocationId(Integer id) {
        TypedQuery<Retailer> query = entityManager.createQuery("SELECT r FROM Retailer r JOIN r.address a JOIN a.location l WHERE l.id = :id  AND  r.deletedBy IS NULL AND r.deletedOn IS NULL ",Retailer.class);

        query.setParameter("id",id);
        List<Retailer> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }
}
