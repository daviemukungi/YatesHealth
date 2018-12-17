package com.oneshoppoint.yates.repository;

import com.oneshoppoint.yates.model.Patient;

import java.util.List;

/**
 * Created by robinson on 4/8/16.
 */
public interface PatientDao extends GenericDao<Patient> {
    List<Patient> search (String pattern);
    List<Patient> getByCreator(Integer id);
}
