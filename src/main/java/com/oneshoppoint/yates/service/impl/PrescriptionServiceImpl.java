package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.*;
import com.oneshoppoint.yates.repository.GenericDao;
import com.oneshoppoint.yates.repository.PatientDao;
import com.oneshoppoint.yates.repository.PrescriptionDao;
import com.oneshoppoint.yates.repository.ProductDao;
import com.oneshoppoint.yates.service.PrescriptionService;
import com.oneshoppoint.yates.util.*;
import com.oneshoppoint.yates.wrapper.PrescriptionForm;
import com.oneshoppoint.yates.wrapper.PrescriptionItemForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by robinson on 4/8/16.
 */
@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {
    @Autowired
    private PrescriptionDao prescriptionDao;
    @Autowired
    private GenericDao<Inn> innDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private PatientDao patientDao;

    private static final Logger logger = LoggerFactory.getLogger(Email.class);

    public void save (PrescriptionForm prescriptionForm) {
        Prescription prescription = new Prescription();
        prescription.setMedic(currentUser.getUser().getMedic());
        prescription.setEnabled(true);
        prescription.setDispensed(false);
        prescription.setCreatedBy(currentUser.getUser().getId());

        Random rand = new Random();
        prescription.setCode("PRES-"+Calendar.getInstance().getTimeInMillis()+"-"+rand.nextInt(1000) + 1);
        prescription.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        List<PrescriptionItem> prescriptionItems = new ArrayList<PrescriptionItem>();
        List<PrescriptionItemForm> itemForms = prescriptionForm.getPrescriptionItems();
        for (PrescriptionItemForm item : itemForms){
            PrescriptionItem prescriptionItem = new PrescriptionItem();
            if(item.getInnId() != null) {
                prescriptionItem.setInn(innDao.find(item.getInnId()));
            } else if (item.getProductId() != null) {
                prescriptionItem.setProduct(productDao.find(item.getProductId()));
            } else {
                throw new RestException(Status.FAILED,"No INN or product was specified");
            }

            prescriptionItem.setDosageForm(item.getDosageForm());
            prescriptionItem.setFrequencyQuantity(item.getFrequencyQuantity());
            prescriptionItem.setFrequencyPerDay(item.getFrequencyPerDay());
            prescriptionItem.setDuration(item.getDuration());
            prescriptionItem.setUnit(item.getUnit());
            prescriptionItem.setType(item.getType());
            prescriptionItem.setNote(item.getNote());
            prescriptionItems.add(prescriptionItem);
        }

        prescription.setPrescriptionItems(prescriptionItems);
        prescription.setSent(false);
        Patient patient = patientDao.find(prescriptionForm.getPatientId());
        if(patient == null) {
            throw new RestException(Status.FAILED,"The patient does not exist");
        }
        prescription.setPatient(patient);
        prescriptionDao.save(prescription);
    }

    public void send (Integer id) {
        Prescription prescription = prescriptionDao.find(id);
        //send email
        String message = "Dear "+prescription.getPatient().getLastname()+" \n";
        message+="You have been prescribed medicine by the Medical Practitioner described below \n";
        message+="Doctor's Name :"+currentUser.getUser().getFirstname()+" "+currentUser.getUser().getLastname()+"\n";
        message+="Doctor's Email : "+currentUser.getUser().getAddress().getEmail()+"\n";
        message+="Doctor's Phone number : "+currentUser.getUser().getAddress().getPhoneNumber()+"\n";
        message+="==================================================================================\n";
        message+="Use this prescription code '"+prescription.getCode()+"'  to access your prescription within the next 7 days";
        try {
            Email.send("PRESCRIPTION FROM ONE SHOP POINT BY "+currentUser.getUser().getFirstname(), prescription.getPatient().getEmail(), message);
        } catch (Exception e) {
            logger.error("Email error :- ",e);
            throw new RestException(Status.ERROR,e.getMessage());
        }

        prescription.setSent(true);
        prescription.setUpdatedBy(currentUser.getUser().getId());
        prescription.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        prescriptionDao.update(prescription);
    }

    public void update (PrescriptionForm prescriptionForm) {
        Prescription prescription = prescriptionDao.find(prescriptionForm.getId());
        if(prescription == null) {
            throw new RestException(Status.ERROR,"The prescription specified does not exist");
        }

        if(prescription.getDispensed()) {
            throw new RestException(Status.ERROR,"The prescription specified has already been dispensed to the patient and cannot be modified");
        }

        prescription.setMedic(currentUser.getUser().getMedic());
        prescription.setCreatedBy(currentUser.getUser().getId());
        byte[] encodedBytes = Base64.encode(UUID.randomUUID().toString().getBytes());
        prescription.setCode(new String(encodedBytes, Charset.forName("UTF-8")));
        prescription.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        List<PrescriptionItem> prescriptionItems = new ArrayList<PrescriptionItem>();
        List<PrescriptionItemForm> itemForms = prescriptionForm.getPrescriptionItems();
        for (PrescriptionItemForm item : itemForms){
            PrescriptionItem prescriptionItem = new PrescriptionItem();
            if(item.getInnId() != null) {
                prescriptionItem.setInn(innDao.find(item.getInnId()));
                prescriptionItem.setProduct(null);
            } else if (item.getProductId() != null) {
                prescriptionItem.setProduct(productDao.find(item.getProductId()));
                prescriptionItem.setInn(null);
            } else {
                throw new RestException(Status.FAILED,"No INN or product was specified");
            }
            prescriptionItem.setDosageForm(item.getDosageForm());
            prescriptionItem.setFrequencyQuantity(item.getFrequencyQuantity());
            prescriptionItem.setFrequencyPerDay(item.getFrequencyPerDay());
            prescriptionItem.setDuration(item.getDuration());
            prescriptionItem.setUnit(item.getUnit());
            prescriptionItem.setType(item.getType());
            prescriptionItem.setNote(item.getNote());
            prescriptionItems.add(prescriptionItem);
        }

        prescription.setPrescriptionItems(prescriptionItems);
        Patient patient = patientDao.find(prescriptionForm.getPatientId());
        if(patient == null) {
            throw new RestException(Status.FAILED,"The patient does not exist");
        }
        prescription.setPatient(patient);
        prescriptionDao.update(prescription);
    }

    public void dispense (Integer id) {
        Prescription prescription = prescriptionDao.find(id);
        if(prescription == null) {
            throw new RestException(Status.ERROR,"The prescription specified does not exist");
        }

        if(prescription.getDispensed()) {
            throw new RestException(Status.ERROR,"The prescription specified has already been dispensed to the patient and cannot be modified");
        }

        prescription.setDispensed(true);
        prescription.setUpdatedBy(currentUser.getUser().getId());
        prescription.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        prescriptionDao.update(prescription);
    }

    public List<Prescription> getAll () {
        return prescriptionDao.getAll();
    }

    public List<Prescription> getByPatient (Integer id) {
        return prescriptionDao.getByPatientId(id);
    }

    public Prescription getById (Integer id) {
        return prescriptionDao.find(id);
    }

    public Prescription getByCode (String code) {
        Prescription prescription = prescriptionDao.getByCode(code);
        if(prescription != null) {
            long timeElapsed = (Calendar.getInstance().getTimeInMillis() - prescription.getCreatedOn().getTime())/(1000*60*60*24);
            if(timeElapsed > 7) {
                return null;
            }

            if(prescription.getDispensed()) {
                return null;
            }
        }

        return prescription;
    }

    public void delete (Integer id) {
        Prescription prescription = prescriptionDao.find(id);

        if(prescription == null) {
            throw new RestException(Status.ERROR,"The prescription does not exist");
        }

        if(prescription.getDispensed()) {
            throw new RestException(Status.ERROR,"The prescription specified has already been dispensed to the patient and cannot be deleted");
        }

        prescription.setDeletedBy(currentUser.getUser().getId());
        prescription.setDeletedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        prescriptionDao.delete(prescription);
    }

    public List<Prescription> search (String search) {
        return prescriptionDao.getAll();
    }
}