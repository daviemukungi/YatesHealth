package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.Manufacturer;
import com.oneshoppoint.yates.wrapper.ManufacturerForm;

import java.util.List;

/**
 * Created by robinson on 4/9/16.
 */
public interface ManufacturerService {
    void save(ManufacturerForm manufacturerForm);
    List<Manufacturer> getAll();
    Manufacturer getById(Integer id);
    void update(ManufacturerForm manufacturerForm);
    void delete(Integer id);
    List<Manufacturer> search(String search);
}
