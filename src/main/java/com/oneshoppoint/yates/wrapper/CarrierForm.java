package com.oneshoppoint.yates.wrapper;

import com.oneshoppoint.yates.model.Image;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by robinson on 4/9/16.
 */
public class CarrierForm {
    private Integer id;
    @Size(min=3,max=100)
    private String name;
    private String payBillNo;
    @NotNull
    private AddressForm address;
    @NotNull
    private Image image;
    @NotNull
    private Boolean enabled;

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getId () {
        return id;
    }

    public void setImage (Image image) {
        this.image = image;
    }

    public Image getImage () {
        return image;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

    public void setPayBillNo (String payBillNo) {
        this.payBillNo = payBillNo;
    }

    public String getPayBillNo () {
        return payBillNo;
    }

    public void setEnabled (Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled () {
        return enabled;
    }

    public void setAddress(AddressForm address) {
        this.address = address;
    }

    public AddressForm getAddress () {
        return address;
    }

}
