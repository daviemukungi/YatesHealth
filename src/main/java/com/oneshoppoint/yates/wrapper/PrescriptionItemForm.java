package com.oneshoppoint.yates.wrapper;

import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;

/**
 * Created by robinson on 4/20/16.
 */
public class PrescriptionItemForm {
    private Integer innId;
    private Integer productId;
    @NotBlank
    private String dosageForm;
    @NotNull
    private Integer frequencyQuantity;
    @NotNull
    private Integer frequencyPerDay;
    @NotNull
    private Integer duration;
    @NotBlank
    private String unit;
    private String note;
    private String type;

    public void setInnId (Integer innId) {
        this.innId = innId;
    }

    public Integer getInnId () {
        return innId;
    }

    public void setProductId (Integer productId) {
        this.productId = productId;
    }

    public Integer getProductId () {
        return productId;
    }

    public void setDosageForm (String dosageForm) {
        this.dosageForm = dosageForm;
    }

    public String getDosageForm () {
        return dosageForm;
    }

    public void setFrequencyQuantity (Integer frequencyQuantity) {
        this.frequencyQuantity = frequencyQuantity;
    }

    public Integer getFrequencyQuantity () {
        return frequencyQuantity;
    }

    public void setFrequencyPerDay (Integer frequencyPerDay) {
        this.frequencyPerDay = frequencyPerDay;
    }

    public Integer getFrequencyPerDay () {
        return frequencyPerDay;
    }

    public void setDuration (Integer duration) {
        this.duration = duration;
    }

    @Column(nullable = false)
    public Integer getDuration () {
        return duration;
    }

    public void setUnit (String unit) {
        this.unit = unit;
    }

    @Column(nullable = false)
    public String getUnit () {
        return unit;
    }

    public void setType (String type) {
        this.type = type;
    }

    @Column(nullable = false)
    public String getType () {
        return type;
    }

    public void setNote (String note) {
        this.note = note;
    }

    public String getNote () {
        return note;
    }
}
