package com.oneshoppoint.yates.model;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by robinson on 4/6/16.
 */
@Entity
@Table(name="locations")
public class Location extends Model implements Comparable<Location> {
    private String name;
    private String label;
    private Set<Location> children;
    private Boolean root = false;

    public void setName (String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public String getName () {
        return name;
    }

    public void setLabel (String label) {
        this.label = label;
    }

    @Column(nullable = false)
    public String getLabel () {
        return label;
    }

    public void setChildren (Set<Location> children) {
        if(this.children == null || children == null) {
            this.children = new HashSet<Location>();
        }

        if(children != null) {
            this.children.addAll(children);
        }
    }

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="parent_id")
    public Set<Location> getChildren () {
        return children;
    }

    public void setRoot (Boolean root) {
        this.root = root;
    }

    @Column(nullable = false)
    public Boolean getRoot () {
        return root;
    }

    public int compareTo (@Nonnull Location other) {
        final int BEFORE = -1;
        final int EQUAL = 0;
        final int AFTER = 1;

        if(this.name.compareToIgnoreCase(other.getName()) < EQUAL) {
            return BEFORE;
        } else if(this.label.compareToIgnoreCase(other.getLabel()) == EQUAL) {
            return this.label.compareToIgnoreCase(other.getLabel());
        } else {
            return AFTER;
        }
    }

    public boolean equals (Object obj) {
        if(obj instanceof Location) {
            Location other = (Location) obj;
            if(this.name.equalsIgnoreCase(other.getName()) && this.label.equalsIgnoreCase(other.getLabel())) {
                return true;
            }

        }
        return false;
    }
}
