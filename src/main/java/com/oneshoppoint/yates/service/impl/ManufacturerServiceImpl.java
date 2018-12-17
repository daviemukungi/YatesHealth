package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.Address;
import com.oneshoppoint.yates.model.Location;
import com.oneshoppoint.yates.model.Manufacturer;
import com.oneshoppoint.yates.model.Image;
import com.oneshoppoint.yates.repository.GenericDao;
import com.oneshoppoint.yates.repository.GenericRecursiveDao;
import com.oneshoppoint.yates.service.ManufacturerService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.ManufacturerForm;
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
public class ManufacturerServiceImpl implements ManufacturerService {
    @Autowired
    GenericDao<Manufacturer> manufacturerDao;
    @Autowired
    GenericRecursiveDao<Location> locationDao;
    @Autowired
    CurrentUser currentUser;
    public void save (ManufacturerForm manufacturerForm) {
        if(manufacturerDao.getByName(manufacturerForm.getName()) != null) {
            throw new RestException(Status.ERROR,"The manufacturer "+manufacturerForm.getName()+" already exists");
        }

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCreatedBy(currentUser.getUser().getId());
        manufacturer.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        manufacturer.setName(manufacturerForm.getName());
        Address address = new Address();
        address.setEmail(manufacturerForm.getAddress().getEmail());
        address.setPhoneNumber(manufacturerForm.getAddress().getPhoneNumber());
        address.setStreetAddress(manufacturerForm.getAddress().getStreetAddress());
        address.setResidentialName(manufacturerForm.getAddress().getResidentialName());
        address.setLocation(locationDao.find(manufacturerForm.getAddress().getLocationId()));
        manufacturer.setAddress(address);
        Image img = manufacturerForm.getImage();
        manufacturer.setImage(img);
        manufacturer.setEnabled(manufacturerForm.getEnabled());

        manufacturerDao.save(manufacturer);
    }

    public void update (ManufacturerForm manufacturerForm) {
        Manufacturer manufacturer = manufacturerDao.find(manufacturerForm.getId());

        if(manufacturer == null) {
            throw new RestException(Status.ERROR,"The manufacturer does not exist");
        }

        manufacturer.setUpdatedBy(currentUser.getUser().getId());
        manufacturer.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        manufacturer.setName(manufacturerForm.getName());
        Address address = new Address();
        address.setEmail(manufacturerForm.getAddress().getEmail());
        address.setPhoneNumber(manufacturerForm.getAddress().getPhoneNumber());
        address.setStreetAddress(manufacturerForm.getAddress().getStreetAddress());
        address.setResidentialName(manufacturerForm.getAddress().getResidentialName());
        address.setLocation(locationDao.find(manufacturerForm.getAddress().getLocationId()));
        manufacturer.setAddress(address);
        Image img = manufacturerForm.getImage();
        manufacturer.setImage(img);
        manufacturer.setEnabled(manufacturerForm.getEnabled());
        manufacturerDao.update(manufacturer);
    }

    public List<Manufacturer> getAll () {
        return manufacturerDao.getAll();
    }

    public Manufacturer getById (Integer id) {
        return manufacturerDao.find(id);
    }

    public void delete (Integer id) {
        Manufacturer manufacturer = manufacturerDao.find(id);

        if(manufacturer == null) {
            throw new RestException(Status.ERROR,"The manufacturer does not exist");
        }
        manufacturer.setDeletedBy(currentUser.getUser().getId());
        manufacturer.setDeletedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        manufacturerDao.delete(manufacturer);
    }

    public List<Manufacturer> search (String search) {
        return manufacturerDao.getAll();
    }
}