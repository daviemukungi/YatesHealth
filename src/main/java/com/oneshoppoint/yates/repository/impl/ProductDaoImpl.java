package com.oneshoppoint.yates.repository.impl;

import com.oneshoppoint.yates.model.Product;
import com.oneshoppoint.yates.repository.ProductDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by robinson on 4/12/16.
 */
@Repository
public class ProductDaoImpl extends GenericDaoImpl<Product> implements ProductDao {
    @PersistenceContext
    private EntityManager entityManager;

    public ProductDaoImpl () {
        super(Product.class);
    }

    public List<Product> search (String pattern){
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.name like :pattern  AND  p.deletedBy IS NULL AND p.deletedOn IS NULL ",Product.class);

        query.setParameter("pattern","%"+pattern+"%");
        List<Product> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<Product> search (String pattern,Integer categoryId,Integer manufacturerId) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p JOIN p.category c JOIN p.manufacturer m WHERE p.name like :pattern  AND m.id = :manufacturerId AND c.id = :categoryId AND  p.deletedBy IS NULL AND p.deletedOn IS NULL ",Product.class);

        query.setParameter("pattern","%"+pattern+"%");
        query.setParameter("categoryId",categoryId);
        query.setParameter("manufacturerId",manufacturerId);
        List<Product> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<Product> searchByCategory (String pattern,Integer categoryId) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p JOIN p.category c WHERE p.name like :pattern  AND c.id = :categoryId AND  p.deletedBy IS NULL AND p.deletedOn IS NULL ",Product.class);

        query.setParameter("pattern","%"+pattern+"%");
        query.setParameter("categoryId",categoryId);
        List<Product> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<Product> searchByManufacturer (String pattern,Integer manufacturerId) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p JOIN p.manufacturer m WHERE p.name like :pattern  AND m.id = :manufacturerId AND  p.deletedBy IS NULL AND p.deletedOn IS NULL ",Product.class);

        query.setParameter("pattern","%"+pattern+"%");
        query.setParameter("manufacturerId",manufacturerId);
        List<Product> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<Product> getByCategoryId (Integer id) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p JOIN p.category c WHERE c.id = :id  AND  p.deletedBy IS NULL AND p.deletedOn IS NULL ",Product.class);

        query.setParameter("id",id);
        List<Product> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<Product> getByManufacturerId (Integer id) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p JOIN p.manufacturer m WHERE m.id = :id  AND  p.deletedBy IS NULL AND p.deletedOn IS NULL ",Product.class);

        query.setParameter("id",id);
        List<Product> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<Product> getByFeatureId (Integer id) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p JOIN p.featureValues fv JOIN fv.feature f WHERE f.id = :id  AND  p.deletedBy IS NULL AND p.deletedOn IS NULL ",Product.class);

        query.setParameter("id",id);
        List<Product> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public List<Product> getByInnId (Integer id) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p JOIN p.inn i WHERE i.id = :id  AND  p.deletedBy IS NULL AND p.deletedOn IS NULL ",Product.class);

        query.setParameter("id",id);
        List<Product> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result;
    }

    public Product getByUUID (String uuid) {
        TypedQuery<Product> query = entityManager.createQuery("SELECT p FROM Product p WHERE p.UUID = :uuid  AND  p.deletedBy IS NULL AND p.deletedOn IS NULL ",Product.class);
        query.setParameter("uuid",uuid);
        List<Product> result = query.getResultList();
        if(result.isEmpty()) {
            return null;
        }

        return result.get(0);
    }
}
