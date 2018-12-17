package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.Location;
import com.oneshoppoint.yates.wrapper.LocationForm;

import java.util.List;

/**
 * Created by robinson on 4/9/16.
 */
public interface LocationService {
    void save(LocationForm categoryForm);
    Location getTree();
    Location getById(Integer id);
    void update(LocationForm categoryForm);
    void delete(Integer id);
    List<Location> search(String search);
}
