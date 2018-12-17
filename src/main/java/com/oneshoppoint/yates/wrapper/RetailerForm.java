package com.oneshoppoint.yates.wrapper;

import com.oneshoppoint.yates.model.Image;
import com.oneshoppoint.yates.model.Manufacturer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by robinson on 4/9/16.
 */
public class RetailerForm {
    private Integer id;
    @Size(min=3,max=100)
    private String name;
    private String description;
    private String payBillNo;
    @NotNull
    private AddressForm address;
    @NotNull
    private Boolean enabled;
    private Image image;
    private Boolean pharmacist;
    private List<Integer> manufacturerIds;

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

    public void setDescription (String description) {
        this.description = description;
    }

    public String getDescription () {
        return description;
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

    public void setImage (Image image) {
        this.image = image;
    }

    public Image getImage () {
        return image;
    }

    public void setPharmacist (Boolean pharmacist) {
        this.pharmacist = pharmacist;
    }

    public Boolean getPharmacist () {
        return pharmacist;
    }

    public void setManufacturerIds (List<Integer> manufacturerIds) {
        this.manufacturerIds = manufacturerIds;
    }

    public List<Integer> getManufacturerIds () {
        return manufacturerIds;
    }

}
