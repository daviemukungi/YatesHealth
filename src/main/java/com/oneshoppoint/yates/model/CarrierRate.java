package com.oneshoppoint.yates.model;

import javax.persistence.*;


/**
 * Created by robinson on 4/29/16.
 */
@Entity
@Table(name="carrier_rates")
public class CarrierRate  {
    private Integer id;
    private Location origin;
    private Location destination;
    private Double price;
    private Double exceedWeight;
    private Double exceedCharge;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Integer getId () {
        return id;
    }

    public void setOrigin (Location origin) {
        this.origin = origin;
    }

    @OneToOne
    @JoinColumn(name = "origin_id")
    public Location getOrigin () {
        return origin;
    }

    public void setDestination (Location destination) {
        this.destination = destination;
    }

    @OneToOne
    @JoinColumn(name = "destination_id")
    public Location getDestination () {
        return destination;
    }

    public void setPrice (Double price) {
        this.price = price;
    }

    @Column(nullable = false)
    public Double getPrice () {
        return price;
    }

    public void setExceedWeight (Double exceedWeight) {
        this.exceedWeight = exceedWeight;
    }

    public Double getExceedWeight () {
        return exceedWeight;
    }

    public void setExceedCharge (Double exceedCharge) {
        this.exceedCharge = exceedCharge;
    }

    public Double getExceedCharge () {
        return exceedCharge;
    }

    @Override
    public boolean equals (Object obj) {
        if(obj instanceof CarrierRate) {
            CarrierRate other = (CarrierRate) obj;
            if(this.origin.equals(other.getOrigin()) && this.destination.equals(other.getDestination())) {
                return true;
            }
        }
        return false;
    }

}
