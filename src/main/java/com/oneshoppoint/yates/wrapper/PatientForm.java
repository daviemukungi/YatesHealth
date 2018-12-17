package com.oneshoppoint.yates.wrapper;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by robinson on 4/20/16.
 */
public class PatientForm {
    private Integer id;
    @NotBlank
    private String firstname;
    @NotBlank
    private String lastname;
    @NotBlank
    private String email;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private String idNumber;

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

    public void setEmail (String email) {
        this.email = email;
    }

    public String getEmail () {
        return email;
    }

    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber () {
        return phoneNumber;
    }

    public void setIdNumber (String idNumber) {
        this.idNumber = idNumber;
    }

    public String getIdNumber () {
        return this.idNumber;
    }
}
