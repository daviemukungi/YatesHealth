package com.oneshoppoint.yates.wrapper;



import javax.validation.constraints.NotNull;

/**
 * Created by robinson on 5/1/16.
 */
public class CarrierRateForm {
    @NotNull
    private Integer originId;
    @NotNull
    private Integer destinationId;
    @NotNull
    private Double price;
    private Double exceedWeight;
    private Double exceedCharge;

    public void setOriginId (Integer originId) {
        this.originId = originId;
    }

    public Integer getOriginId () {
        return originId;
    }

    public void setDestinationId (Integer destinationId) {
        this.destinationId = destinationId;
    }

    public Integer getDestinationId () {
        return destinationId;
    }

    public void setPrice (Double price) {
        this.price = price;
    }

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
}
