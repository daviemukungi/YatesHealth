package com.oneshoppoint.yates.wrapper;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Created by robinson on 4/9/16.
 */
public class MedicTypeForm {
    private Integer id;
    @Size(min=3,max=100)
    private String name;
    @NotNull
    private AddressForm address;
    @NotNull
    private List<Integer> allowedINNs;
    @NotNull
    private Boolean enabled;

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

    public void setAllowedINNs (List<Integer> allowedINNs) {
        this.allowedINNs = allowedINNs;
    }

    public List<Integer> getAllowedINNs () {
        return allowedINNs;
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
