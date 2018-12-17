package com.oneshoppoint.yates.wrapper;

import com.oneshoppoint.yates.model.Carrier;

/**
 * Created by robinson on 4/28/16.
 */
public class CarrierCharge {
    private Carrier carrier;
    private Double charge;
    private String planName;

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCharge(Double charge) {
        this.charge = charge;
    }

    public Double getCharge() {
        return charge;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanName() {
        return planName;
    }
}
