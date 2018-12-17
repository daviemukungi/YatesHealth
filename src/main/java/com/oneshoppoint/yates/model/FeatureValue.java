package com.oneshoppoint.yates.model;

import javax.annotation.Nonnull;
import javax.persistence.*;

/**
 * Created by robinson on 4/6/16.
 */
@Entity
@Table(name="feature_values")
public class FeatureValue implements Comparable<FeatureValue> {
    private Integer id;
    private Feature feature;
    private String val;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Integer getId () {
        return id;
    }

    public void setFeature (Feature feature) {
        this.feature = feature;
    }

    @ManyToOne
    @JoinColumn(name="feature_id")
    public Feature getFeature() {
        return feature;
    }

    public void setVal (String val) {
        this.val = val;
    }

    @Column(nullable = false)
    public String getVal () {
        return val;
    }

    public int compareTo (@Nonnull FeatureValue other) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if(this.feature.getName().compareToIgnoreCase(other.getFeature().getName()) < EQUAL) {
            return BEFORE;
        } else if(this.feature.getName().compareToIgnoreCase(other.getFeature().getName()) == EQUAL){
            return this.val.compareToIgnoreCase(other.getVal());
        } else {
            return AFTER;
        }
    }

    @Override
    public boolean equals (Object obj) {
        if(obj instanceof FeatureValue) {
            FeatureValue other = (FeatureValue) obj;
            if(this.feature.equals(other.getFeature()) && this.val.equalsIgnoreCase(other.getVal())) {
                return true;
            }
        }

        return false;
    }
}
