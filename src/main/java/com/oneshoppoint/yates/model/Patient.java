package com.oneshoppoint.yates.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Davie on 5/23/16.
 */
@Entity
@Table(name="patients")
public class Patient extends Model {
    private String firstname;
    private String lastname;
    private String email;
    private String phoneNumber;
    private String idNumber;

    public void setFirstname (String firstname) {
        this.firstname = firstname;
    }

    @Column(nullable = false)
    public String getFirstname () {
        return firstname;
    }

    public void setLastname (String lastname) {
        this.lastname = lastname;
    }

    @Column(nullable = false)
    public String getLastname () {
        return lastname;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    @Column(nullable = false)
    public String getEmail () {
        return email;
    }

    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(nullable = false)
    public String getPhoneNumber () {
        return phoneNumber;
    }

    public void setIdNumber (String idNumber) {
        this.idNumber = idNumber;
    }

    @Column(nullable = false)
    public String getIdNumber () {
        return this.idNumber;
    }
}
