package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.DosageForm;
import com.oneshoppoint.yates.model.Inn;
import com.oneshoppoint.yates.repository.GenericDao;
import com.oneshoppoint.yates.service.DosageFormService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.wrapper.Dosage;
import com.oneshoppoint.yates.wrapper.DosageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by robinson on 4/11/16.
 */
@Service
@Transactional
public class DosageFormServiceImpl implements DosageFormService {
    @Autowired
    private GenericDao<DosageForm> dosageFormDao;
    @Autowired
    private GenericDao<Inn> innDao;
    @Autowired
    private CurrentUser currentUser;

    public void save (DosageList dosageList) {
        List<Dosage> dosageForms = dosageList.getDosageFormList();
        for(Dosage dosageForm : dosageForms) {
            String name = dosageForm.getName().toLowerCase();
            DosageForm dosage = dosageFormDao.getByName(name);
            if(dosage != null) {
                continue;
            }

            dosage = new DosageForm();
            dosage.setName(name);
            dosage.setEnabled(dosageForm.getEnabled());
            dosage.setCreatedBy(currentUser.getUser().getId());
            dosage.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            dosageFormDao.save(dosage);
        }
    }

    public void update (DosageList dosageList) {
        List<Dosage> dosageForms = dosageList.getDosageFormList();
        for(Dosage dosageForm : dosageForms) {
            DosageForm dosage = dosageFormDao.find(dosageForm.getId());
            if(dosage == null) {
                continue;
            }

            dosage.setName(dosageForm.getName().toLowerCase());
            dosage.setEnabled(dosageForm.getEnabled());
            dosage.setUpdatedBy(currentUser.getUser().getId());
            dosage.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            dosageFormDao.save(dosage);
        }
    }

    public List<DosageForm> getAll () {
        return dosageFormDao.getAll();
    }

    public DosageForm getById (Integer id) {
        return dosageFormDao.find(id);
    }

    public List<DosageForm> getByInn (Integer id) {
        Inn inn = innDao.find(id);
        if (inn != null) {
            return inn.getDosageForms();
        }

        return new ArrayList<DosageForm>();
    }

    public void delete (List<Integer> ids) {
        for(Integer id : ids) {
            DosageForm dosage = dosageFormDao.find(id);
            if(dosage == null) {
                continue;
            }
            dosage.setDeletedBy(currentUser.getUser().getId());
            dosage.setDeletedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            dosageFormDao.save(dosage);
        }
    }
}
