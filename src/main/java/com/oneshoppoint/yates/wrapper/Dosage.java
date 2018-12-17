package com.oneshoppoint.yates.wrapper;


import javax.validation.constraints.NotNull;

/**
 * Created by robinson on 5/4/16.
 */
public class Dosage {
    private Integer id;
    @NotNull
    private String name;
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

    public void setEnabled (Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled () {
        return enabled;
    }
}
