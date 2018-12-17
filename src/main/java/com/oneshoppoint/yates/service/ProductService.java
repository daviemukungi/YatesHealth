package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.Image;
import com.oneshoppoint.yates.model.Product;
import com.oneshoppoint.yates.wrapper.ProductForm;
import com.oneshoppoint.yates.wrapper.ProductImages;

import java.util.List;

/**
 * Created by robinson on 4/9/16.
 */
public interface ProductService {
    void save(ProductForm productForm);
    List<Product> getAll();
    Product getById(Integer id);
    Product getByUUID(String uuid);
    List<Product> getByCategoryId(Integer id);
    List<Product> getByInnId(Integer id);
    List<Product> getByFeatureId(Integer id);
    List<Product> getByManufacturerId(Integer id);
    void update(ProductForm productForm);
    void addImage (Integer id,Image image);
    void delete(Integer id);
    List<Product> search(String pattern);
}
