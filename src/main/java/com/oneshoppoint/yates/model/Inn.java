package com.oneshoppoint.yates.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by robinson on 4/11/16.
 */
@Entity
@Table(name="inns")
public class Inn extends Model {
    private String name;
    private List<DosageForm> dosageForms;

    public void setName (String name) {
        this.name = name;
    }

    @Column(nullable = false)
    @NotNull
    public String getName () {
        return name;
    }

    public void setDosageForms (List<DosageForm> dosageForms) {
        this.dosageForms = new ArrayList<DosageForm>();
        if(dosageForms != null && !dosageForms.isEmpty()) {
            this.dosageForms.addAll(dosageForms);
        }
    }

    @ManyToMany
    @JoinTable(name = "inn_to_dosage_form",joinColumns = @JoinColumn(name="inn_id"),inverseJoinColumns = @JoinColumn(name="dosage_form_id"))
    public List<DosageForm> getDosageForms () {
        return dosageForms;
    }

    @Override
    public boolean equals (Object obj) {
        if(obj instanceof Inn) {
            Inn other = (Inn) obj;
            if(this.name.equalsIgnoreCase(other.getName())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode () {
        int hash = 7;
        hash = 79 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
}
