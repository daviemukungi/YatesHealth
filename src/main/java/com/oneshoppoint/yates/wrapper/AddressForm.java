package com.oneshoppoint.yates.wrapper;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by robinson on 4/6/16.
 */
public class AddressForm {
    @NotBlank
    private String phoneNumber;
    @Email(message = "Please enter a valid email")
    private String email;
    private String streetAddress;
    private String residentialName;
    @NotNull(message = "Please select your nearest location")
    private Integer locationId;

    public void setPhoneNumber (String phoneNumber) {
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

    public void setStreetAddress (String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getStreetAddress () {
        return  this.streetAddress;
    }

    public void setResidentialName (String residentialName) {
        this.residentialName = residentialName;
    }

    public String getResidentialName () {
        return residentialName;
    }

    public void setLocationId (Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getLocationId () {
        return locationId;
    }
}
