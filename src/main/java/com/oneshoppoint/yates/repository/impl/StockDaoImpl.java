package com.oneshoppoint.yates.repository.impl;

import com.oneshoppoint.yates.model.Product;
import com.oneshoppoint.yates.model.Retailer;
import com.oneshoppoint.yates.model.Stock;
import com.oneshoppoint.yates.repository.StockDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by robinson on 4/8/16.
 */
@Repository
public class StockDaoImpl extends  GenericDaoImpl<Stock> implements StockDao {
    @PersistenceContext
    EntityManager entityManager;

    public StockDaoImpl() {
        super(Stock.class);
    }

    public Stock getByProductAndRetailer(Product product,Retailer retailer) {
        TypedQuery<Stock> query = entityManager.createQuery("SELECT s FROM Stock s JOIN s.product p JOIN s.retailer r WHERE p.UUID = :uuid AND r.id = :rid AND s.deletedBy IS NULL AND s.deletedOn IS NULL ",Stock.class);
        query.setParameter("uuid",product.getUUID());
        query.setParameter("rid",retailer.getId());
        List<Stock> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }

    public List<Stock> getByRetailer(Integer id) {
        TypedQuery<Stock> query = entityManager.createQuery("SELECT s FROM Stock s JOIN s.retailer r WHERE r.id = :id AND s.deletedBy IS NULL AND s.deletedOn IS NULL ",Stock.class);
        query.setParameter("id",id);
        List<Stock> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }
}
