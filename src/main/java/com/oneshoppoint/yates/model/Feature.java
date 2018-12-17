package com.oneshoppoint.yates.model;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robinson on 5/4/16.
 */
@Entity
@Table(name="features")
public class Feature extends Model implements Comparable<Feature> {
    private String name;

    public void setName (String name) {
        this.name = name;
    }
    @Column(nullable = false)
    public String getName () {
        return name;
    }

    public int compareTo (@Nonnull Feature other) {
        return this.name.compareToIgnoreCase(other.getName());
    }

    @Override
    public boolean equals (Object obj) {
        if(obj instanceof Feature) {
            Feature other = (Feature) obj;
            if(this.name.equalsIgnoreCase(other.getName())) {
                return true;
            }
        }

        return false;
    }
}
