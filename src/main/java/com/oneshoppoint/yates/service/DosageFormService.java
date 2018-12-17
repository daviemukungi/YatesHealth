package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.DosageForm;
import com.oneshoppoint.yates.wrapper.DosageList;

import java.util.List;

/**
 * Created by robinson on 4/11/16.
 */
public interface DosageFormService {
    void save(DosageList innList);
    List<DosageForm> getAll();
    DosageForm getById(Integer id);
    List<DosageForm> getByInn(Integer id);
    void update(DosageList innList);
    void delete(List<Integer> ids);
}
