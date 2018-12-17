package com.oneshoppoint.yates.wrapper;

import com.oneshoppoint.yates.model.Item;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by robinson on 4/26/16.
 */
public class Checkout {
    @Size(min = 3,max=20)
    private String firstname;
    @Size(min = 3,max=20)
    private String lastname;
    @Size(min = 3,max=20)
    private String phoneNumber;
    @Email
    private String email;
    @NotBlank
    private String streetAddress;
    @NotBlank
    private String residentialName;
    @NotNull
    private Integer locationId;
    @NotNull
    private Integer retailerId;
    @NotNull
    private String retailerTransactionCode;
    private Integer carrierId;
    private String carrierTransactionCode;
    private String carrierPlan;
    @NotNull
    private List<Item> cart;

    public void setCart (List<Item> cart) {
        this.cart = cart;
    }

    public List<Item> getCart () {
        return cart;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname () {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname () {
        return lastname;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber () {
        return phoneNumber;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getEmail () {
        return email;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void getResidentialName (String residentialName) {
        this.residentialName = residentialName;
    }

    public String getResidentialName () {
        return residentialName;
    }

    public void setLocationId (Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getLocationId() {
        return locationId;
    }

    public void setRetailerId (Integer retailerId) {
        this.retailerId = retailerId;
    }

    public Integer getRetailerId() {
        return retailerId;
    }

    public void setRetailerTransactionCode (String retailerTransactionCode) {
        this.retailerTransactionCode  = retailerTransactionCode;
    }

    public String getRetailerTransactionCode(){
        return retailerTransactionCode;
    }

    public void setCarrierId (Integer carrierId) {
        this.carrierId = carrierId;
    }

    public Integer getCarrierId() {
        return carrierId;
    }

    public void setCarrierTransactionCode (String carrierTransactionCode) {
        this.carrierTransactionCode  = carrierTransactionCode;
    }

    public String getCarrierTransactionCode(){
        return carrierTransactionCode;
    }

    public void setCarrierPlan (String carrierPlan) {
        this.carrierPlan  = carrierPlan;
    }

    public String getCarrierPlan(){
        return carrierPlan;
    }
}
