package com.oneshoppoint.yates.model;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by robinson on 4/6/16.
 */
@Entity
@Table(name="medic_types")
public class MedicType extends Model {
    private String name;
    private Address address;
    private Set<Inn> allowedINNs;
    private String token;
    private Timestamp tokenTime;

    public void setName (String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getName () {
        return name;
    }

    public void setToken (String token) {
        this.token = token;
    }

    public String getToken () {
        return token;
    }

    public void setTokenTime (Timestamp tokenTime) {
        this.tokenTime = tokenTime;
    }

    @Column(name="token_time")
    public Timestamp getTokenTime () {
        return tokenTime;
    }

    public void setAddress (Address address) {
        this.address = address;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="address_id")
    public Address getAddress () {
        return address;
    }

    public void setAllowedINNs (Set<Inn> allowedINNs) {
        this.allowedINNs = new HashSet<Inn>();
        this.allowedINNs.addAll(allowedINNs);
    }

    @ManyToMany
    @JoinTable(name = "allowed_inns",joinColumns = @JoinColumn(name="medic_type_id"),inverseJoinColumns = @JoinColumn(name="inn_id"))
    public Set<Inn> getAllowedINNs () {
        return allowedINNs;
    }

    @Override
    public boolean equals (Object obj) {
        if(obj instanceof MedicType) {
            MedicType other = (MedicType) obj;
            if(this.name.equalsIgnoreCase(other.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode () {
        int hash = 17;
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0) + (this.address != null ? this.address.hashCode() : 0);
        return hash;
    }
}
