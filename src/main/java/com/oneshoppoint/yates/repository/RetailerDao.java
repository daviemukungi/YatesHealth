package com.oneshoppoint.yates.repository;

import com.oneshoppoint.yates.model.Retailer;

import java.util.List;

/**
 * Created by robinson on 4/12/16.
 */
public interface RetailerDao extends GenericDao<Retailer> {
    List<Retailer> search(String pattern);
    List<Retailer> getByLocationId(Integer id);
}
