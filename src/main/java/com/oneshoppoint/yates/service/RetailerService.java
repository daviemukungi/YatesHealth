package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.Retailer;
import com.oneshoppoint.yates.util.PublicStorage;
import com.oneshoppoint.yates.wrapper.RetailerBasket;
import com.oneshoppoint.yates.wrapper.RetailerForm;

import java.util.List;

/**
 * Created by robinson on 4/9/16.
 */
public interface RetailerService {
    void save(RetailerForm retailerForm);
    List<Retailer> getAll();
    Retailer getById(Integer id);
    void update(RetailerForm retailerForm);
    void delete(Integer id);
    List<Retailer> search(String search);
}
