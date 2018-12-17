package com.oneshoppoint.yates.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by robinson on 4/6/16.
 */
@Entity
@Table(name="retailers")
public class Retailer extends Model {
    private Image image;
    private String name;
    private String description;
    private Address address;
    private String payBillNo;
    private List<Manufacturer> manufacturers;
    private Set<Retailer> outlets;
    private Boolean pharmacist;

    public void setImage (Image image) {
        this.image = image;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="image_id")
    public Image getImage () {
        return image;
    }

    public void setName (String name) {
       this.name = name;
    }

    @Column(nullable = false)
    public String getName () {
        return name;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    @Lob
    @Column(nullable=false,length = 2000)
    public String getDescription () {
        return description;
    }

    public void setAddress (Address address) {
        this.address = address;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="address_id")
    public Address getAddress () {
        return address;
    }

    public void setPayBillNo (String payBillNo) {
        this.payBillNo = payBillNo;
    }

    @Column(name="pay_bill_no",nullable = false)
    public String getPayBillNo () {
        return payBillNo;
    }

    public void setOutlets (Set<Retailer> outlets) {
        this.outlets = new HashSet<Retailer>();
        if(outlets != null) {
            this.outlets.addAll(outlets);
        }
    }

    public void setManufacturers (List<Manufacturer> manufacturers) {
        this.manufacturers = new ArrayList<Manufacturer>();
        if(manufacturers != null) {
            this.manufacturers.addAll(manufacturers);
        }
    }

    @ManyToMany
    @JoinTable(name="allowed_manufacturers",joinColumns = @JoinColumn(name="retailer_id"),inverseJoinColumns = @JoinColumn(name="manufacturer_id"))
    public List<Manufacturer> getManufacturers () {
        return manufacturers;
    }


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="retailer_id")
    public Set<Retailer> getOutlets () {
        return outlets;
    }

    public void setPharmacist(Boolean pharmacist) {
        this.pharmacist = pharmacist;
    }

    public Boolean getPharmacist () {
        return pharmacist;
    }

    @Override
    public boolean equals(Object obj) {
       if(obj instanceof Retailer) {
          Retailer other = (Retailer) obj;
          if(this.name.equalsIgnoreCase(other.getName()) && this.address.equals(other.getAddress()) && this.payBillNo.equals(other.getPayBillNo())) {
              return true;
          }
       }

       return false;
    }
}
