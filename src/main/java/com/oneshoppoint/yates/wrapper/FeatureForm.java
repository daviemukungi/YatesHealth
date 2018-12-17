package com.oneshoppoint.yates.wrapper;


import javax.validation.constraints.NotNull;

/**
 * Created by robinson on 5/4/16.
 */
public class FeatureForm {
    private Integer id;
    @NotNull
    private String feature;
    @NotNull
    private Boolean enabled;

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getId () {
        return id;
    }

    public void setFeature (String feature) {
        this.feature = feature;
    }

    public String getFeature () {
        return feature;
    }

    public void setEnabled (Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled () {
        return enabled;
    }
}
