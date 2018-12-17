package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.*;
import com.oneshoppoint.yates.repository.*;
import com.oneshoppoint.yates.service.UserService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.util.Email;
import com.oneshoppoint.yates.util.RestException;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.UserForm;
import com.oneshoppoint.yates.wrapper.UserPassword;
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
public class UserServiceImpl implements UserService {
    private @Autowired
    UserDao userDao;
    private @Autowired
    GenericRecursiveDao<Location> locationDao;
    private @Autowired
    GenericDao<Permission> permissionDao;
    private @Autowired
    CurrentUser currentUser;
    private @Autowired
    MedicTypeDao medicTypeDao;
    private @Autowired
    RetailerDao retailerDao;
    private @Autowired
    GenericDao<Carrier> carrierDao;

    public void save (UserForm userForm) {
        if(userDao.getByEmail(userForm.getAddress().getEmail()) != null) {
            throw new RestException(Status.ERROR,"The email : "+userForm.getAddress().getEmail()+" already exists");
        }

        User user = new User();
        user.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        user.setFirstname(userForm.getFirstname());
        user.setLastname(userForm.getLastname());
        String password;
        Address address = new Address();
        if(!userForm.getRegistration()) {
            password = randomString();
            user.setCreatedBy(currentUser.getUser().getId());
        } else {
            user.setCreatedBy(0);
            password = userForm.getPassword();
            if(password == null || password.trim().equals("") || password.trim().length() < 6) {
                throw new RestException(Status.ERROR,"Invalid password :- either password is not set or it is less than six characters");
            }
        }
        user.setPassword(new BCryptPasswordEncoder().encode(password));
        address.setEmail(userForm.getAddress().getEmail());
        address.setPhoneNumber(userForm.getAddress().getPhoneNumber());
        address.setStreetAddress(userForm.getAddress().getStreetAddress());
        address.setResidentialName(userForm.getAddress().getResidentialName());
        address.setLocation(locationDao.find(userForm.getAddress().getLocationId()));
        user.setAddress(address);
        user.setEnabled(userForm.getEnabled());
        Set<Permission> permissions = new HashSet<Permission>();
        if(userForm.getCustomer()) {
            Customer customer = new Customer();
            if(!userForm.getRegistration()) {
                customer.setCreatedBy(currentUser.getUser().getId());
            } else {
                customer.setCreatedBy(0);
            }
            customer.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            customer.setEnabled(true);
            user.setCustomer(customer);

            permissions.add(permissionDao.getByName("ROLE_CUSTOMER"));
        } else if (userForm.getMedicalId() != null) {//doctors
            Medic medic = new Medic();
            MedicType medicType ;
            medic.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            medic.setCreatedBy(0);
            medic.setStatus("pending");

            if(userForm.getMedicalId() == null) {
                throw new RestException(Status.FAILED,"Please enter your medical Id");
            } else {
                medic.setMedicalId(userForm.getMedicalId());
            }

            if(userForm.getNationalId() == null) {
                throw new RestException(Status.FAILED,"Please enter your national Id");
            } else {
                medic.setNationalId(userForm.getNationalId());
            }

            if(userForm.getMedicTypeId() == null) {
                throw new RestException(Status.FAILED,"Please select a medic type");
            } else {
                medicType = medicTypeDao.find(userForm.getMedicTypeId());
                if(medicType != null) {
                    medic.setMedicType(medicType);
                } else {
                    throw new RestException(Status.ERROR, "Supplied medic type does not exist");
                }

            }

            medic.setEnabled(true);
            user.setMedic(medic);
            user.setEnabled(false);//The medic must be approved before being allowed to sign in
            permissions.add(permissionDao.getByName("ROLE_MEDIC"));
            //send email
            String message = "Greetings ! \n";
            message+="We would like you to verify the details of this medical practitioner shown below\n";
            message+="NAME :-" + userForm.getFirstname()+" "+userForm.getLastname()+"\n";
            message+="NATIONAL ID"+userForm.getNationalId()+"\n";
            message+="MEDICAL ID"+userForm.getMedicalId()+"\n";
            message+="ADDRESS/CONTACT\n";
            message+="PHONE NUMBER :- "+userForm.getAddress().getPhoneNumber()+"\n";
            message+="EMAIL :- "+userForm.getAddress().getEmail()+"\n";
            message+="STREET ADDRESS :- "+userForm.getAddress().getStreetAddress()+"\n";
            message+="RESIDENTIAL NAME :- "+userForm.getAddress().getResidentialName()+"\n";
            message+="LOCATION :- "+locationDao.find(userForm.getAddress().getLocationId()).getName()+"\n";
            message+="PASSWORD :- "+password+"\n";
            try {
                Email.send("REGISTRATION TO ONE SHOP POINT",medicType.getAddress().getEmail(), message);
            } catch (Exception e) {
                throw new RestException(Status.ERROR,e.getMessage());
            }

        } else if (userForm.getAffiliate()) {//retailer,carrier and pharmacy employees
            Affiliate affiliate = new Affiliate();
            affiliate.setCreatedBy(currentUser.getUser().getId());
            affiliate.setCreatedBy(0);
            affiliate.setCreatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
            affiliate.setEnabled(true);
            affiliate.setImage(userForm.getImage());
            if(userForm.getCarrier()) {
                affiliate.setCarrier(carrierDao.find(userForm.getEmployerId()));
                affiliate.setPharmacist(false);
                permissions.add(permissionDao.getByName("ROLE_CARRIER"));
            } else {
                affiliate.setRetailer(retailerDao.find(userForm.getEmployerId()));
                if(userForm.getPharmacist()) {
                    affiliate.setPharmacist(userForm.getPharmacist());
                } else {
                    affiliate.setPharmacist(false);
                }
                permissions.add(permissionDao.getByName("ROLE_RETAILER"));
            }

            user.setAffiliate(affiliate);

        } else {
            Set<Integer> permissionIds = userForm.getPermissionIds();
            for(Integer permissionId : permissionIds) {
                permissions.add(permissionDao.find(permissionId));
            }
        }

        //send email
        String message = "Dear "+userForm.getLastname()+" \n";
        message+="You have been registered to use one shop point with the following credentials \n";
        message+="EMAIL :- "+userForm.getAddress().getEmail();
        message+="PASSWORD :- "+password;
        try {
            Email.send("REGISTRATION TO ONE SHOP POINT", userForm.getAddress().getEmail(), message);
        } catch (Exception e) {
            throw new RestException(Status.ERROR,e.getMessage());
        }

        user.setPermissions(permissions);
        userDao.save(user);
    }

    public void update (UserForm userForm) {
        User user = userDao.find(userForm.getId());

        if(user == null) {
            throw new RestException(Status.ERROR,"The user does not exist");
        }

        user.setUpdatedBy(currentUser.getUser().getId());
        user.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        user.setFirstname(userForm.getFirstname());
        user.setLastname(userForm.getLastname());
        Address address = new Address();
        address.setEmail(userForm.getAddress().getEmail());
        address.setPhoneNumber(userForm.getAddress().getPhoneNumber());
        address.setStreetAddress(userForm.getAddress().getStreetAddress());
        address.setResidentialName(userForm.getAddress().getResidentialName());
        address.setLocation(locationDao.find(userForm.getAddress().getLocationId()));
        user.setAddress(address);
        user.setEnabled(userForm.getEnabled());
        Set<Permission> permissions = new HashSet<Permission>();
        if(userForm.getAffiliate()) {
           Affiliate affiliate = user.getAffiliate();
           if(affiliate != null) {
               affiliate.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
               affiliate.setUpdatedBy(currentUser.getUser().getId());


               affiliate.setImage(userForm.getImage());
               if(userForm.getCarrier()) {
                   affiliate.setCarrier(carrierDao.find(userForm.getEmployerId()));
                   permissions.add(permissionDao.getByName("ROLE_CARRIER"));
               } else if (userForm.getEmployerId() != null) {
                   affiliate.setRetailer(retailerDao.find(userForm.getEmployerId()));
                   permissions.add(permissionDao.getByName("ROLE_RETAILER"));
                   if(userForm.getPharmacist()) {
                       affiliate.setPharmacist(userForm.getPharmacist());
                   }
               } else {
                   throw new RestException(Status.FAILED,"Please choose an employer");
               }
           }
            user.setPermissions(permissions);
        } else if(!userForm.getCustomer()) {
            Set<Integer> permissionIds = userForm.getPermissionIds();
            for(Integer permissionId : permissionIds) {
                permissions.add(permissionDao.find(permissionId));
            }
            permissions.add(permissionDao.getByName("ROLE_ADMIN"));
            user.setPermissions(permissions);
        } else {
            permissions.add(permissionDao.getByName("ROLE_CUSTOMER"));
            user.setPermissions(permissions);
        }

        userDao.update(user);
    }

    public List<User> getAll () {
        return userDao.getAll();
    }

    public User getById (Integer id) {
        return userDao.find(id);
    }

    public void delete (Integer id) {
        User user = userDao.find(id);

        if(user == null) {
            throw new RestException(Status.ERROR,"The user does not exist");
        }
        user.setDeletedBy(currentUser.getUser().getId());
        user.setDeletedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));

        userDao.delete(user);
    }

    public User getByMedicId (Integer id) {
        return userDao.getByMedicId(id);
    }

    public List<User> getByRegulatorToken (String token) {
        return userDao.getByRegulatorToken(token);
    }

    public List<User> search (String search) {
        return userDao.getAll();
    }

    public void updatePassword(UserPassword userPassword) {
        User user = currentUser.getUser();
        if(user == null) {
            throw new RestException(Status.ERROR,"Failed to determine user");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(!encoder.matches(userPassword.getOldPassword(),user.getPassword())) {
            throw new RestException(Status.FAILED,"You are not authorized to change this password");
        }

        user.setPassword(encoder.encode(userPassword.getNewPassword()));
        user.setUpdatedBy(user.getId());
        user.setUpdatedOn(new Timestamp(Calendar.getInstance().getTimeInMillis()));
        userDao.update(user);

    }

    public List<User> getPendingMedic() {
        return userDao.getPendingMedic();
    }

    public void confirm (Integer userId,String status) {
        User user = userDao.find(userId);

        if(user == null) {
            throw new RestException(Status.ERROR,"The user does not exist");
        }

        if(status.equalsIgnoreCase("approved")) {
            user.setEnabled(true);
            user.getMedic().setStatus(status);
        } else if (status.equalsIgnoreCase("rejected")) {
            user.getMedic().setStatus(status);
        }
    }

    private String randomString () {
        String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int len = 10;
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ ) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}