package com.oneshoppoint.yates.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Created by robinson on 6/4/16.
 */
@Entity
@Table(name="dosage_forms")
public class DosageForm extends Model {
    private String name;

    public void setName (String name) {
        this.name = name;
    }

    @Column(nullable = false)
    @NotNull
    public String getName () {
        return name;
    }

    @Override
    public boolean equals (Object obj) {
        if(obj instanceof DosageForm) {
            DosageForm other = (DosageForm) obj;
            if(this.name.equalsIgnoreCase(other.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode () {
        int hash = 13;
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
}
