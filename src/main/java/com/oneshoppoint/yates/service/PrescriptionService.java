package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.Prescription;
import com.oneshoppoint.yates.model.PrescriptionItem;
import com.oneshoppoint.yates.wrapper.PrescriptionForm;

import java.util.List;

/**
 * Created by robinson on 4/9/16.
 */
public interface PrescriptionService {
    void save(PrescriptionForm prescriptionForm);
    void update (PrescriptionForm prescriptionForm);
    void send (Integer id);
    List<Prescription> getAll();
    Prescription getById(Integer id);
    List<Prescription> getByPatient(Integer id);
    void dispense (Integer id);
    void delete(Integer id);
    List<Prescription> search(String search);
    Prescription getByCode (String code);
}
