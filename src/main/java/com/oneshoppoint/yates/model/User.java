package com.oneshoppoint.yates.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Davie on 4/6/16.
 */
@Entity
@Table(name="users")
public class User extends Model {
    private String firstname;
    private String lastname;
    @JsonIgnore
    private String password;
    private Address primaryAddress;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Medic medic;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Customer customer;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Affiliate affiliate;
    private Set<Permission> permissions;
    private Timestamp forgotPasswordRequest;

    public void setFirstname (String firstname) {
        this.firstname = firstname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getLastname () {
        return lastname;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public String getPassword () {
        return  password;
    }

    public void setAddress (Address address) {
        this.primaryAddress = address;
    }

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="primary_address_id")
    public Address getAddress () {
        return primaryAddress;
    }

    public void setMedic (Medic medic) {
        this.medic = medic;
    }

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="medic_id")
    public Medic getMedic () {
        return medic;
    }

    public void setCustomer (Customer customer) {
        this.customer = customer;
    }

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="customer_id")
    public Customer getCustomer () {
        return customer;
    }

    public void setAffiliate (Affiliate affiliate) {
        this.affiliate = affiliate;
    }

    @OneToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="affiliate_id")
    public Affiliate getAffiliate () {
        return affiliate;
    }

    public void setPermissions (Set<Permission> permissions) {
        this.permissions = new HashSet<Permission>();
        this.permissions.addAll(permissions);
    }

    @ManyToMany
    @JoinTable(name="user_permissions",joinColumns = @JoinColumn(name="user_id"),inverseJoinColumns = @JoinColumn(name="permission_id"))
    public Set<Permission> getPermissions () {
        return permissions;
    }

    public void setForgotPasswordRequest(Timestamp forgotPasswordRequest) {
        this.forgotPasswordRequest = forgotPasswordRequest;
    }

    public Timestamp getForgotPasswordRequest () {
        return forgotPasswordRequest;
    }
}
