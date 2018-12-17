package com.oneshoppoint.yates.repository;

import com.oneshoppoint.yates.model.Product;
import com.oneshoppoint.yates.model.Retailer;
import com.oneshoppoint.yates.model.Stock;

import java.util.List;

/**
 * Created by robinson on 4/8/16.
 */
public interface StockDao extends GenericDao<Stock> {
    Stock getByProductAndRetailer(Product product,Retailer retailer);
    List<Stock> getByRetailer(Integer id);
}
