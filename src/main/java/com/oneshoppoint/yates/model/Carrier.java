package com.oneshoppoint.yates.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robinson on 4/6/16.
 */

@Entity
@Table(name="carriers")
public class Carrier extends Model {
    private Image image;
    private String name;
    private Address address;
    private String payBillNo;
    private List<CarrierPlan> carrierPlans;

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

    public void setCarrierPlans (List<CarrierPlan> carrierPlans) {
        this.carrierPlans = new ArrayList<CarrierPlan>();
        this.carrierPlans.addAll(carrierPlans);
    }

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="carrier_id")
    public List<CarrierPlan> getCarrierPlans () {
        return carrierPlans;
    }
}
