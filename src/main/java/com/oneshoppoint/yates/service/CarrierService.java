package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.Carrier;
import com.oneshoppoint.yates.wrapper.CarrierForm;

import java.util.List;

/**
 * Created by Davie on 4/9/16.
 */
public interface CarrierService {
    void save(CarrierForm carrierForm);
    List<Carrier> getAll();
    Carrier getById(Integer id);
    void update(CarrierForm carrierForm);
    void delete(Integer id);
    List<Carrier> search(String search);
}
