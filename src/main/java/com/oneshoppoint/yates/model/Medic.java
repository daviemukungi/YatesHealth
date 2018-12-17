package com.oneshoppoint.yates.model;


import javax.persistence.*;

/**
 * Created by Davie on 4/6/16.
 */
@Entity
@Table(name="medics")
public class Medic extends Model {
    private String medicalId;
    private String nationalId;
    private MedicType medicType;
    private String status;

    public void setMedicalId (String medicalId) {
        this.medicalId = medicalId;
    }

    @Column(name="medical_id",nullable = false)
    public String getMedicalId () {
        return medicalId;
    }

    public void setStatus (String status) {
        this.status = status;
    }

    @Column(nullable = false)
    public String getStatus () {
        return status;
    }

    public void setNationalId (String nationalId) {
        this.nationalId = nationalId;
    }

    @Column(name="national_id",nullable = false)
    public String getNationalId () {
        return nationalId;
    }

    public void setMedicType (MedicType medicType) {
        this.medicType = medicType;
    }

    @ManyToOne
    @JoinColumn(name="medic_type_id")
    public MedicType getMedicType () {
        return medicType;
    }

}
