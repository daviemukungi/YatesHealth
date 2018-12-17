package com.oneshoppoint.yates.model;

import javax.persistence.*;

/**
 * Created by Davie on 4/6/16.
 */
@Entity
@Table(name="manufacturers")
public class Manufacturer extends Model {
    private String name;
    private Image image;
    private Address address;

    public void setName (String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getName () {
        return name;
    }

    public void setImage (Image image) {
        this.image = image;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="image_id")
    public Image getImage () {
        return image;
    }

    public void setAddress (Address address) {
        this.address = address;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="address_id")
    public Address getAddress () {
        return address;
    }

    public boolean equals (Object obj) {
        if(obj instanceof Manufacturer) {
            Manufacturer other = (Manufacturer) obj;
            if(other.name.equalsIgnoreCase(other.getName())) {
                return true;
            }
        }

        return false;
    }
}
