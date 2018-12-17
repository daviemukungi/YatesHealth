package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.*;
import com.oneshoppoint.yates.repository.*;
import com.oneshoppoint.yates.service.RetailerService;
import com.oneshoppoint.yates.util.*;
import com.oneshoppoint.yates.wrapper.RetailerBasket;
import com.oneshoppoint.yates.wrapper.RetailerForm;
import com.oneshoppoint.yates.wrapper.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by robinson on 4/8/16.
 */
@Service
@Transactional
public class RetailerServiceImpl implements RetailerService {
    @Autowired
    RetailerDao retailerDao;
    @Autowired
    UserDao userDao;
    @Autowired
    GenericRecursiveDao<Location> locationDao;
    @Autowired
    CurrentUser currentUser;
    @Autowired
    GenericDao<Permission> permissionDao;
    @Autowired
    ProductDao productDao;
    @Autowired
    PrescriptionDao prescriptionDao;
    @Autowired
    StockDao stockDao;
    @Autowired
    GenericDao<Manufacturer> manufacturerDao;


    public void save (RetailerForm retailerForm) {
        if(retailerDao.getByName(retailerForm.getName()) != null) {
            throw new RestException(Status.ERROR,"The retailer "+retailerForm.getName()+" already exists");
        }

        Retailer retailer = new Retailer();
        retailer.setCreatedBy(currentUser.getUser().getId());
        retailer.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        retailer.setName(retailerForm.getName());
        retailer.setDescription(retailerForm.getDescription());
        retailer.setImage(retailerForm.getImage());
        Address address = new Address();
        address.setEmail(retailerForm.getAddress().getEmail());
        address.setPhoneNumber(retailerForm.getAddress().getPhoneNumber());
        address.setStreetAddress(retailerForm.getAddress().getStreetAddress());
        address.setResidentialName(retailerForm.getAddress().getResidentialName());
        address.setLocation(locationDao.find(retailerForm.getAddress().getLocationId()));
        retailer.setAddress(address);
        retailer.setPayBillNo(retailerForm.getPayBillNo());
        retailer.setEnabled(retailerForm.getEnabled());
        if(currentUser.getUser().getAffiliate() != null && currentUser.getUser().getAffiliate().getRetailer() != null) {
            Retailer outlet = retailer;
            retailer = currentUser.getUser().getAffiliate().getRetailer();
            Set<Retailer> outlets = retailer.getOutlets();
            outlets.add(outlet);
            retailer.setOutlets(outlets);
            retailerDao.update(retailer);
        } else {
            List<Integer> ids = retailerForm.getManufacturerIds();
            if(ids.isEmpty()) {
                throw new RestException(Status.FAILED,"Please specify the allowed manufacturers for this retailer");
            }
            List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
            for(Integer id : ids) {
                Manufacturer manufacturer = manufacturerDao.find(id);
                if(manufacturer != null){
                    manufacturers.add(manufacturer);
                }
            }

            if(manufacturers.isEmpty()){
                throw new RestException(Status.FAILED,"None of the manufacturers specified exist");
            }
            retailer.setManufacturers(manufacturers);
            retailerDao.save(retailer);
        }
    }

    public void update (RetailerForm retailerForm) {
        Retailer retailer = retailerDao.find(retailerForm.getId());

        if(retailer == null) {
            throw new RestException(Status.ERROR,"The retailer does not exist");
        }

        retailer.setUpdatedBy(currentUser.getUser().getId());
        retailer.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        retailer.setName(retailerForm.getName());
        retailer.setDescription(retailerForm.getDescription());
        if(retailerForm.getImage() != null) {
            retailer.setImage(retailerForm.getImage());
        }
        Address address = new Address();
        address.setEmail(retailerForm.getAddress().getEmail());
        address.setPhoneNumber(retailerForm.getAddress().getPhoneNumber());
        address.setStreetAddress(retailerForm.getAddress().getStreetAddress());
        address.setResidentialName(retailerForm.getAddress().getResidentialName());
        address.setLocation(locationDao.find(retailerForm.getAddress().getLocationId()));
        retailer.setAddress(address);
        retailer.setPayBillNo(retailerForm.getPayBillNo());
        retailer.setEnabled(retailerForm.getEnabled());
        Set<Permission> permissions = currentUser.getUser().getPermissions();
        for(Permission permission : permissions) {
            if(permission.getName().equals("ROLE_ADMIN")) {
                List<Integer> ids = retailerForm.getManufacturerIds();
                if(ids.isEmpty()) {
                    throw new RestException(Status.FAILED,"Please specify the allowed manufacturers for this retailer");
                }
                List<Manufacturer> manufacturers = new ArrayList<Manufacturer>();
                for(Integer id : ids) {
                    Manufacturer manufacturer = manufacturerDao.find(id);
                    if(manufacturer != null){
                        manufacturers.add(manufacturer);
                    }
                }

                if(manufacturers.isEmpty()){
                    throw new RestException(Status.FAILED,"None of the manufacturers specified exist");
                }
                retailer.setManufacturers(manufacturers);
                break;
            }
        }
        retailerDao.update(retailer);
    }

    public List<Retailer> getAll () {
        return retailerDao.getAll();
    }

    public Retailer getById (Integer id) {
        return retailerDao.find(id);
    }

    public void delete (Integer id) {
        Retailer retailer = retailerDao.find(id);

        if(retailer == null) {
            throw new RestException(Status.ERROR,"The retailer does not exist");
        }
        retailer.setDeletedBy(currentUser.getUser().getId());
        retailer.setDeletedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        retailerDao.delete(retailer);
    }

    public List<Retailer> search (String search) {
        return retailerDao.getAll();
    }
}