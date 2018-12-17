package com.oneshoppoint.yates.model;

import javax.persistence.*;

/**
 * Created by Davie on 4/6/16.
 */

@Entity
@Table(name="affiliates")
public class Affiliate extends Model {
    private Carrier carrier;
    private Retailer retailer;
    private Image image;
    private Boolean pharmacist;

    public void setCarrier (Carrier carrier) {
        this.carrier = carrier;
    }

    @OneToOne
    @JoinColumn(name="carrier_id")
    public Carrier getCarrier () {
        return carrier;
    }

    public void setRetailer (Retailer retailer) {
        this.retailer = retailer;
    }

    @OneToOne
    @JoinColumn(name="retailer_id")
    public Retailer getRetailer () {
        return retailer;
    }

    public void setImage (Image image) {
        this.image = image;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name="image_id")
    public Image getImage () {
        return image;
    }

    public void setPharmacist (Boolean pharmacist) {
        this.pharmacist = pharmacist;
    }

    public Boolean getPharmacist () {
        return pharmacist;
    }
}
