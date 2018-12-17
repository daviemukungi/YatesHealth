package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.*;
import com.oneshoppoint.yates.repository.GenericDao;
import com.oneshoppoint.yates.repository.GenericRecursiveDao;
import com.oneshoppoint.yates.repository.MedicTypeDao;
import com.oneshoppoint.yates.service.MedicTypeService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.MedicTypeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by robinson on 4/8/16.
 */
@Service
@Transactional
public class MedicTypeServiceImpl implements MedicTypeService {
    @Autowired
    MedicTypeDao medicTypeDao;
    @Autowired
    GenericRecursiveDao<Location> locationDao;
    @Autowired
    CurrentUser currentUser;
    @Autowired
    GenericDao<Inn> innDao;

    public void save (MedicTypeForm medicTypeForm) {
        if(medicTypeDao.getByName(medicTypeForm.getName()) != null) {
            throw new RestException(Status.ERROR,"The medicType "+medicTypeForm.getName()+" already exists");
        }

        MedicType medicType = new MedicType();
        medicType.setCreatedBy(currentUser.getUser().getId());
        medicType.setCreatedBy(0);
        medicType.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        medicType.setName(medicTypeForm.getName());
        Address address = new Address();
        address.setEmail(medicTypeForm.getAddress().getEmail());
        address.setPhoneNumber(medicTypeForm.getAddress().getPhoneNumber());
        address.setStreetAddress(medicTypeForm.getAddress().getStreetAddress());
        address.setResidentialName(medicTypeForm.getAddress().getResidentialName());
        address.setLocation(locationDao.find(medicTypeForm.getAddress().getLocationId()));
        medicType.setAddress(address);
        medicType.setEnabled(medicTypeForm.getEnabled());
        List<Integer> allowedINNs = medicTypeForm.getAllowedINNs();
        Set<Inn> allowedINNSet = new HashSet<Inn>();

        for(Integer alowedINN : allowedINNs) {
            allowedINNSet.add(innDao.find(alowedINN));
        }
        medicType.setAllowedINNs(allowedINNSet);

        medicTypeDao.save(medicType);
    }

    public void update (MedicTypeForm medicTypeForm) {
        MedicType medicType = medicTypeDao.find(medicTypeForm.getId());

        if(medicType == null) {
            throw new RestException(Status.ERROR,"The medicType does not exist");
        }

        medicType.setUpdatedBy(currentUser.getUser().getId());
        medicType.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        medicType.setName(medicTypeForm.getName());
        Address address = new Address();
        address.setEmail(medicTypeForm.getAddress().getEmail());
        address.setPhoneNumber(medicTypeForm.getAddress().getPhoneNumber());
        address.setStreetAddress(medicTypeForm.getAddress().getStreetAddress());
        address.setResidentialName(medicTypeForm.getAddress().getResidentialName());
        address.setLocation(locationDao.find(medicTypeForm.getAddress().getLocationId()));
        medicType.setAddress(address);
        medicType.setEnabled(medicTypeForm.getEnabled());
        List<Integer> allowedINNs = medicTypeForm.getAllowedINNs();
        Set<Inn> allowedINNSet = new HashSet<Inn>();

        for(Integer alowedINN : allowedINNs) {
            allowedINNSet.add(innDao.find(alowedINN));
        }
        medicType.setAllowedINNs(allowedINNSet);
        medicTypeDao.update(medicType);
    }

    public List<MedicType> getAll () {
        return medicTypeDao.getAll();
    }

    public MedicType getById (Integer id) {
        return medicTypeDao.find(id);
    }

    public void delete (Integer id) {
        MedicType medicType = medicTypeDao.find(id);

        if(medicType == null) {
            throw new RestException(Status.ERROR,"The medicType does not exist");
        }
        medicType.setDeletedBy(currentUser.getUser().getId());
        medicType.setDeletedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        medicTypeDao.delete(medicType);
    }

    public List<MedicType> search (String search) {
        return medicTypeDao.getAll();
    }
}