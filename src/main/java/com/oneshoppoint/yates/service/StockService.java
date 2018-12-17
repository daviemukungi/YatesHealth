package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.Stock;
import com.oneshoppoint.yates.wrapper.StockForm;

import java.util.List;

/**
 * Created by robinson on 4/9/16.
 */
public interface StockService {
    void save(StockForm productForm);
    void importExcel (byte[] bytes,String filename);
    byte[] exportExcel (String name,Integer categoryId);
    byte[] exportInnExcel (String name,Integer InnId);
    List<Stock> getAll();
    List<Stock> getByRetailer(Integer id);
    Stock getById(Integer id);
    void update(StockForm productForm);
    void delete(Integer id);
    List<Stock> search(String search);
}
