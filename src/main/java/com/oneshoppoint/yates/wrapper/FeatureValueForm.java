package com.oneshoppoint.yates.wrapper;

import javax.validation.constraints.NotNull;

/**
 * Created by robinson on 5/5/16.
 */
public class FeatureValueForm {
    private Integer featureId;
    @NotNull
    private String val;

    public void setFeatureId(Integer featureId) {
        this.featureId = featureId;
    }

    public Integer getFeatureId () {
        return featureId;
    }

    public void setVal (String val) {
        this.val = val;
    }

    public String getVal () {
        return val;
    }
}
