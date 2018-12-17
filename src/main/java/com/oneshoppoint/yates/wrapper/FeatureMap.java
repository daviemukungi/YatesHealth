package com.oneshoppoint.yates.wrapper;

import com.oneshoppoint.yates.model.Feature;
import com.oneshoppoint.yates.model.FeatureValue;

import java.util.Set;
import java.util.TreeSet;

/**
 * Created by robinson on 5/5/16.
 */
public class FeatureMap {
    private Feature feature;
    private Set<FeatureValue> featureValues;

    public FeatureMap () {
        featureValues = new TreeSet<FeatureValue>();
    }

    public void setFeature (Feature feature) {
        this.feature = feature;
    }

    public Feature getFeature () {
        return feature;
    }

    public void setFeatureValues (Set<FeatureValue> featureValues) {
        this.featureValues = featureValues;
    }

    public Set<FeatureValue> getFeatureValues () {
        return featureValues;
    }
}
