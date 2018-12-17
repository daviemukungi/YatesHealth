package com.oneshoppoint.yates.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by robinson on 4/30/16.
 */
@Entity
@Table(name="carrier_plans")
public class CarrierPlan {
    private Integer id;
    private String name;
    private Double baseWeight;
    private Double maximumWeight;
    private Double maximumPackSize;
    private Double exceedWeight;
    private Double exceedCharge;
    private Double shippingFactor;
    private List<CarrierRate> carrierRates;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Integer getId () {
        return id;
    }

    public void setName (String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getName () {
        return name;
    }

    public void setBaseWeight (Double baseWeight) {
        this.baseWeight = baseWeight;
    }

    @Column(nullable=false)
    public Double getBaseWeight () {
        return baseWeight;
    }

    public void setMaximumWeight (Double maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    @Column(nullable=false)
    public Double getMaximumWeight () {
        return maximumWeight;
    }

    public void setMaximumPackSize (Double maximumPackSize) {
        this.maximumPackSize = maximumPackSize;
    }

    @Column(nullable=false)
    public Double getMaximumPackSize () {
        return maximumPackSize;
    }

    public void setExceedWeight (Double exceedWeight) {
        this.exceedWeight = exceedWeight;
    }

    @Column(nullable = false)
    public Double getExceedWeight () {
        return exceedWeight;
    }

    public void setExceedCharge (Double exceedCharge) {
        this.exceedCharge = exceedCharge;
    }

    @Column(nullable = false)
    public Double getExceedCharge () {
        return exceedCharge;
    }

    public void setShippingFactor (Double shippingFactor) {
        this.shippingFactor = shippingFactor;
    }

    @Column(nullable = false)
    public Double getShippingFactor () {
        return shippingFactor;
    }

    public void setCarrierRates (Collection<CarrierRate> carrierRates) {
        this.carrierRates = new ArrayList<CarrierRate>();
        this.carrierRates.addAll(carrierRates);
    }

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="carrier_plan_id")
    public List<CarrierRate> getCarrierRates () {
        return carrierRates;
    }
}
