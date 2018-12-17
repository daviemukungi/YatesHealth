package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.MedicType;
import com.oneshoppoint.yates.wrapper.MedicTypeForm;

import java.util.List;

/**
 * Created by robinson on 4/9/16.
 */
public interface MedicTypeService {
    void save(MedicTypeForm medicTypeForm);
    List<MedicType> getAll();
    MedicType getById(Integer id);
    void update(MedicTypeForm medicTypeForm);
    void delete(Integer id);
    List<MedicType> search(String search);
}
