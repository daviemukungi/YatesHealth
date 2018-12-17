package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.Patient;
import com.oneshoppoint.yates.repository.PatientDao;
import com.oneshoppoint.yates.service.PatientService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.PatientForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

/**
 * Created by robinson on 4/8/16.
 */
@Service
@Transactional
public class PatientServiceImpl implements PatientService {
    @Autowired
    PatientDao patientDao;
    @Autowired
    CurrentUser currentUser;
    public void save (PatientForm patientForm) {
        Patient patient = new Patient();
        patient.setCreatedBy(currentUser.getUser().getId());
        patient.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        patient.setFirstname(patientForm.getFirstname());
        patient.setLastname(patientForm.getLastname());
        patient.setEmail(patientForm.getEmail());
        patient.setPhoneNumber(patientForm.getPhoneNumber());
        patient.setIdNumber(patientForm.getIdNumber());
        patient.setEnabled(true);

        patientDao.save(patient);
    }

    public void update (PatientForm patientForm) {
        Patient patient = patientDao.find(patientForm.getId());
        if(patient == null) {
            throw new RestException(Status.ERROR,"The patient specified does not exist");
        }

        patient.setUpdatedBy(currentUser.getUser().getId());
        patient.setFirstname(patientForm.getFirstname());
        patient.setLastname(patientForm.getLastname());
        patient.setEmail(patientForm.getEmail());
        patient.setPhoneNumber(patientForm.getPhoneNumber());
        patient.setIdNumber(patientForm.getIdNumber());
        patient.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        patientDao.update(patient);
    }

    public Patient getById (Integer id) {
        return patientDao.find(id);
    }

    public List<Patient> getAll () {
        return patientDao.getAll();
    }

    public List<Patient> getByCreator (Integer id) {
        return patientDao.getByCreator(id);
    }

    public void delete (Integer id) {
        Patient patient = patientDao.find(id);

        if(patient == null) {
            throw new RestException(Status.ERROR,"The patient does not exist");
        }

        patient.setDeletedBy(currentUser.getUser().getId());
        patient.setDeletedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        patientDao.delete(patient);
    }

    public List<Patient> search (String search) {
        return patientDao.search(search);
    }
}