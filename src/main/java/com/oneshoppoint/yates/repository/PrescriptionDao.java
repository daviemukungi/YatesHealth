package com.oneshoppoint.yates.repository;

import com.oneshoppoint.yates.model.Prescription;

import java.util.List;

/**
 * Created by robinson on 4/8/16.
 */
public interface PrescriptionDao extends GenericDao<Prescription> {
    Prescription getByCode(String code);
    List<Prescription> getByPatientId(Integer id);
}
