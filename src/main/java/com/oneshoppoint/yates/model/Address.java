package com.oneshoppoint.yates.model;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Davie on 4/6/16.
 */
@Entity
@Table(name="addresses")
public class Address  {
    private Integer id;
    @NotBlank
    private String phoneNumber;
    @Email(message = "Please enter a valid email")
    private String email;
    private String streetAddress;
    private String residentialName;
    @NotNull(message = "Please select your nearest location")
    private Location location;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Integer getId () {
        return id;
    }

    public void setPhoneNumber (String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Column(nullable = false)
    public String getPhoneNumber () {
        return phoneNumber;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    @Column(nullable = false)
    public String getEmail () {
        return email;
    }

    public void setStreetAddress (String streetAddress) {
        this.streetAddress = streetAddress;
    }

    @Column(nullable = false)
    public String getStreetAddress () {
        return  this.streetAddress;
    }

    public void setResidentialName (String residentialName) {
        this.residentialName = residentialName;
    }

    @Column(nullable = false)
    public String getResidentialName () {
        return residentialName;
    }

    public void setLocation (Location location) {
        this.location = location;
    }


    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="location_id")
    public Location getLocation () {
        return location;
    }

    public boolean equals (Object obj) {
        if(obj instanceof Address) {
            Address other = (Address) obj;
            if(this.phoneNumber.equalsIgnoreCase(other.getPhoneNumber()) || this.email.equalsIgnoreCase(other.getEmail())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode () {
        int hash = 19;
        hash = 79 * hash + (this.phoneNumber != null ? this.phoneNumber.hashCode() : 0) + (this.email != null ? this.email.hashCode() : 0);
        return hash;
    }
}
