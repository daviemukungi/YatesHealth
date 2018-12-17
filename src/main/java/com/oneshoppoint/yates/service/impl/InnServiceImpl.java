package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.DosageForm;
import com.oneshoppoint.yates.model.Inn;
import com.oneshoppoint.yates.repository.GenericDao;
import com.oneshoppoint.yates.service.InnService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.InnForm;
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
public class InnServiceImpl implements InnService {
    @Autowired
    private GenericDao<Inn> innDao;
    @Autowired
    private CurrentUser currentUser;
    @Autowired
    private GenericDao<DosageForm> dosageFormDao;

    public void save (InnForm innForm) {
       String name = innForm.getName().toLowerCase();
       Inn inn = innDao.getByName(name);
       if(inn != null) {
          throw new RestException(Status.FAILED,"The inn already exists");
       }

       inn = new Inn();
       inn.setName(name);
       List<Integer> ids  = innForm.getDosageFormIds();
       List<DosageForm> dosageForms = new ArrayList<DosageForm>();
       for(Integer id : ids) {
           DosageForm dosageForm = dosageFormDao.find(id);
           if(dosageForm != null) {
               dosageForms.add(dosageForm);
           }
       }
       inn.setDosageForms(dosageForms);
       inn.setEnabled(innForm.getEnabled());
       inn.setCreatedBy(currentUser.getUser().getId());
       inn.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
       innDao.save(inn);
    }

    public void update (InnForm innForm) {
        Inn inn = innDao.find(innForm.getId());
        if(inn == null) {
            throw new RestException(Status.FAILED,"The inn does not exists");
        }
        inn.setName(innForm.getName());
        List<Integer> ids  = innForm.getDosageFormIds();
        List<DosageForm> dosageForms = new ArrayList<DosageForm>();
        for(Integer id : ids) {
            DosageForm dosageForm = dosageFormDao.find(id);
            if(dosageForm != null) {
                dosageForms.add(dosageForm);
            }
        }
        inn.setDosageForms(dosageForms);
        inn.setEnabled(innForm.getEnabled());
        inn.setCreatedBy(currentUser.getUser().getId());
        inn.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        innDao.save(inn);
    }

    public List<Inn> getAll () {
        return innDao.getAll();
    }

    public Inn getById (Integer id) {
        return innDao.find(id);
    }

    public void delete (List<Integer> ids) {
        for(Integer id : ids) {
            Inn inn = innDao.find(id);
            if(inn == null) {
                continue;
            }
            inn.setDeletedBy(currentUser.getUser().getId());
            inn.setDeletedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            innDao.save(inn);
        }
    }
}
