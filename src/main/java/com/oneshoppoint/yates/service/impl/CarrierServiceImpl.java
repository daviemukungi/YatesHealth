package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.Address;
import com.oneshoppoint.yates.model.Carrier;
import com.oneshoppoint.yates.model.Location;
import com.oneshoppoint.yates.repository.GenericDao;
import com.oneshoppoint.yates.repository.GenericRecursiveDao;
import com.oneshoppoint.yates.service.CarrierService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.CarrierForm;
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
public class CarrierServiceImpl implements CarrierService {
    @Autowired
    GenericDao<Carrier> carrierDao;
    @Autowired
    GenericRecursiveDao<Location> locationDao;
    @Autowired
    CurrentUser currentUser;

    public void save (CarrierForm carrierForm) {
        if(carrierDao.getByName(carrierForm.getName()) != null) {
            throw new RestException(Status.ERROR,"The carrier "+carrierForm.getName()+" already exists");
        }

        Carrier carrier = new Carrier();
        carrier.setCreatedBy(currentUser.getUser().getId());
        carrier.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        carrier.setName(carrierForm.getName());
        carrier.setImage(carrierForm.getImage());
        Address address = new Address();
        address.setEmail(carrierForm.getAddress().getEmail());
        address.setPhoneNumber(carrierForm.getAddress().getPhoneNumber());
        address.setStreetAddress(carrierForm.getAddress().getStreetAddress());
        address.setResidentialName(carrierForm.getAddress().getResidentialName());
        address.setLocation(locationDao.find(carrierForm.getAddress().getLocationId()));
        carrier.setAddress(address);
        carrier.setPayBillNo(carrierForm.getPayBillNo());
        carrier.setEnabled(carrierForm.getEnabled());

        carrierDao.save(carrier);
    }

    public void update (CarrierForm carrierForm) {
        Carrier carrier = carrierDao.find(carrierForm.getId());

        if(carrier == null) {
            throw new RestException(Status.ERROR,"The carrier does not exist");
        }

        carrier.setUpdatedBy(currentUser.getUser().getId());
        carrier.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        carrier.setName(carrierForm.getName());
        carrier.setImage(carrierForm.getImage());
        Address address = new Address();
        address.setEmail(carrierForm.getAddress().getEmail());
        address.setPhoneNumber(carrierForm.getAddress().getPhoneNumber());
        address.setStreetAddress(carrierForm.getAddress().getStreetAddress());
        address.setResidentialName(carrierForm.getAddress().getResidentialName());
        address.setLocation(locationDao.find(carrierForm.getAddress().getLocationId()));
        carrier.setAddress(address);
        carrier.setPayBillNo(carrierForm.getPayBillNo());
        carrier.setEnabled(carrierForm.getEnabled());
        carrierDao.update(carrier);
    }

    public List<Carrier> getAll () {
        return carrierDao.getAll();
    }

    public Carrier getById (Integer id) {
        return carrierDao.find(id);
    }

    public void delete (Integer id) {
        Carrier carrier = carrierDao.find(id);

        if(carrier == null) {
            throw new RestException(Status.ERROR,"The carrier does not exist");
        }
        carrier.setDeletedBy(currentUser.getUser().getId());
        carrier.setDeletedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        carrierDao.delete(carrier);
    }

    public List<Carrier> search (String search) {
        return carrierDao.getAll();
    }
}