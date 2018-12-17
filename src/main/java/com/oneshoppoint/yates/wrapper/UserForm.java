package com.oneshoppoint.yates.wrapper;


import com.oneshoppoint.yates.model.Image;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * Created by robinson on 4/9/16.
 */
public class UserForm {
    private Integer id;
    @Size(min=3,max=100)
    private String firstname;
    @Size(min=3,max=100)
    private String lastname;
    @NotNull
    private AddressForm address;
    @NotNull
    private Boolean enabled;
    private Set<Integer> permissionIds;
    @NotNull
    private Boolean customer = false;
    private Boolean affiliate  = false;
    private Boolean pharmacist = false;
    private Boolean carrier = false;
    private Boolean registration = false;
    private String password;
    private String medicalId;
    private String nationalId;
    private Image image;
    private Integer medicTypeId;
    private Integer employerId;

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getId () {
        return id;
    }

    public void setFirstname (String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname () {
        return firstname;
    }

    public void setLastname (String lastname) {
        this.lastname = lastname;
    }

    public String getLastname () {
        return lastname;
    }

    public void setEnabled (Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled () {
        return enabled;
    }

    public void setAddress(AddressForm address) {
        this.address = address;
    }

    public AddressForm getAddress () {
        return address;
    }

    public void setPermissionIds (Set<Integer> permissionIds) {
        this.permissionIds = permissionIds;
    }

    public Set<Integer> getPermissionIds () {
        return permissionIds;
    }

    public void setCustomer (Boolean customer) {
        this.customer = customer;
    }

    public Boolean getCustomer () {
        return customer;
    }

    public void setCarrier (Boolean carrier) {
        this.carrier = carrier;
    }

    public Boolean getCarrier () {
        return carrier;
    }

    public void setAffiliate (Boolean affiliate) {
        this.affiliate = affiliate;
    }

    public Boolean getAffiliate () {
        return affiliate;
    }

    public void setPharmacist (Boolean pharmacist) {
        this.pharmacist = pharmacist;
    }

    public Boolean getPharmacist () {
        return pharmacist;
    }

    public void setRegistration (Boolean registration) {
        this.registration = registration;
    }

    public Boolean getRegistration () {
        return registration;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getPassword () {
        return password;
    }

    public void setMedicalId (String medicalId) {
        this.medicalId = medicalId;
    }

    public String getMedicalId () {
        return medicalId;
    }

    public void setNationalId (String nationalId) {
        this.nationalId = nationalId;
    }

    public String getNationalId () {
        return nationalId;
    }

    public void setImage (Image image) {
        this.image = image;
    }

    public Image getImage () {
        return image;
    }

    public void setMedicTypeId (Integer medicTypeId) {
        this.medicTypeId = medicTypeId;
    }

    public Integer getMedicTypeId () {
        return medicTypeId;
    }

    public void getEmployerId (Integer employerId) {
        this.employerId = employerId;
    }

    public Integer getEmployerId () {
        return employerId;
    }

}
