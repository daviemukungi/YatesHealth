package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.Patient;
import com.oneshoppoint.yates.wrapper.PatientForm;

import java.util.List;

/**
 * Created by Davie on 4/9/16.
 */
public interface PatientService {
    void save(PatientForm patientForm);
    void update(PatientForm patientForm);
    List<Patient> getAll();
    List<Patient> search(String search);
    Patient getById(Integer id);
    List<Patient> getByCreator (Integer id);
    void delete(Integer id);
}
