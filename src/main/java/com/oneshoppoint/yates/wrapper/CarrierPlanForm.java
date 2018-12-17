package com.oneshoppoint.yates.wrapper;


import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robinson on 5/1/16.
 */
public class CarrierPlanForm {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Double baseWeight;
    @NotNull
    private Double maximumWeight;
    @NotNull
    private Double maximumPackSize;
    @NotNull
    private Double exceedWeight;
    @NotNull
    private Double exceedCharge;
    @NotNull
    private Double shippingFactor;
    @NotNull
    private List<CarrierRateForm> carrierRates;

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getId () {
        return id;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

    public void setBaseWeight (Double baseWeight) {
        this.baseWeight = baseWeight;
    }

    public Double getBaseWeight () {
        return baseWeight;
    }

    public void setMaximumWeight (Double maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    public Double getMaximumWeight () {
        return maximumWeight;
    }

    public void setMaximumPackSize (Double maximumPackSize) {
        this.maximumPackSize = maximumPackSize;
    }

    public Double getMaximumPackSize () {
        return maximumPackSize;
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

    public void setShippingFactor (Double shippingFactor) {
        this.shippingFactor = shippingFactor;
    }

    public Double getShippingFactor () {
        return shippingFactor;
    }

    public void setCarrierRates (List<CarrierRateForm> carrierRates) {
        this.carrierRates = new ArrayList<CarrierRateForm>();
        this.carrierRates.addAll(carrierRates);
    }

    public List<CarrierRateForm> getCarrierRates () {
        return carrierRates;
    }
}
