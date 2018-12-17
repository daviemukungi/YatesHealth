package com.oneshoppoint.yates.model;

import javax.persistence.*;

/**
 * Created by Davie on 4/20/16.
 */
@Entity
@Table(name="prescription_items")
public class PrescriptionItem {
    private Integer id;
    private Inn inn;
    private Product product;
    private String dosageForm;
    private Integer frequencyQuantity;
    private Integer frequencyPerDay;
    private Integer duration;
    private String unit;
    private String type;
    private String note;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Integer getId () {
        return id;
    }

    public void setInn (Inn inn) {
        this.inn = inn;
    }

    @ManyToOne
    @JoinColumn(name="inn_id")
    public Inn getInn () {
        return inn;
    }

    @ManyToOne
    @JoinColumn(name="product_id")
    public Product getProduct () {
        return product;
    }

    public void setProduct (Product product) {
        this.product = product;
    }


    public void setDosageForm (String dosageForm) {
        this.dosageForm = dosageForm;
    }

    @Column(nullable = false)
    public String getDosageForm () {
        return dosageForm;
    }

    @Column(name="frequency_quantity",nullable = false)
    public void setFrequencyQuantity (Integer frequencyQuantity) {
        this.frequencyQuantity = frequencyQuantity;
    }

    public Integer getFrequencyQuantity () {
        return frequencyQuantity;
    }

    public void setFrequencyPerDay (Integer frequencyPerDay) {
        this.frequencyPerDay = frequencyPerDay;
    }

    @Column(name="frequency_per_day",nullable = false)
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
