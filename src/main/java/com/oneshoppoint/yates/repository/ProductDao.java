package com.oneshoppoint.yates.repository;

import com.oneshoppoint.yates.model.Product;

import java.util.List;

/**
 * Created by robinson on 4/12/16.
 */
public interface ProductDao extends GenericDao<Product> {
    List<Product> search (String pattern);
    List<Product> searchByCategory (String pattern,Integer categoryId);
    List<Product> searchByManufacturer (String pattern,Integer manufacturerId);
    List<Product> search (String pattern,Integer categoryId,Integer manufacturerId);
    List<Product> getByCategoryId(Integer id);
    List<Product> getByManufacturerId(Integer id);
    List<Product> getByFeatureId(Integer id);
    List<Product> getByInnId(Integer id);
    Product getByUUID (String uuid);
}
