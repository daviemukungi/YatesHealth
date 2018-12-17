package com.oneshoppoint.yates.wrapper;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by robinson on 4/20/16.
 */
public class PrescriptionForm {
    private Integer id;
    @NotNull
    private Integer patientId;
    @NotNull
    List<PrescriptionItemForm> prescriptionItems;

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getId () {
        return id;
    }

    public void setPrescriptionItems (List<PrescriptionItemForm> prescriptionItems) {
        this.prescriptionItems = prescriptionItems;
    }

    public List<PrescriptionItemForm> getPrescriptionItems () {
        return prescriptionItems;
    }

    public void setPatientId (Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getPatientId () {
        return patientId;
    }
}
