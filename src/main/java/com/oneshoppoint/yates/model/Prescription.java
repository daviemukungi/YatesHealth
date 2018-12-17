package com.oneshoppoint.yates.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Davie on 4/20/16.
 */
@Entity
@Table(name="prescriptions")
public class Prescription extends Model {
    private Medic medic;
    private List<PrescriptionItem> prescriptionItems;
    private String code;
    private Boolean dispensed;
    private Boolean sent;
    private Patient patient;

    public void setMedic (Medic medic) {
        this.medic = medic;
    }

    @ManyToOne
    @JoinColumn(name="medic_id")
    public Medic getMedic () {
        return medic;
    }

    public void setPrescriptionItems (List<PrescriptionItem> prescriptionItems) {
        this.prescriptionItems = new ArrayList<PrescriptionItem>();
        this.prescriptionItems.addAll(prescriptionItems);
    }

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="prescription_id")
    public List<PrescriptionItem> getPrescriptionItems () {
        return prescriptionItems;
    }

    public void setCode (String code) {
        this.code = code;
    }

    @Column(nullable = false)
    public String getCode () {
        return this.code;
    }

    public void setDispensed (Boolean dispensed) {
        this.dispensed = dispensed;
    }

    @Column(nullable = false)
    public Boolean getDispensed () {
        return this.dispensed;
    }

    public void setSent (Boolean sent) {
        this.sent = sent;
    }

    @Column(nullable = false)
    public Boolean getSent () {
        return this.sent;
    }

    public void setPatient (Patient patient) {
        this.patient = patient;
    }

    @OneToOne
    @JoinColumn(name = "patient_id")
    public Patient getPatient () {
        return this.patient;
    }
}
