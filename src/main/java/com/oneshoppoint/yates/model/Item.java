package com.oneshoppoint.yates.model;

import javax.persistence.*;

/**
 * Created by robinson on 4/6/16.
 */
@Entity
@Table(name="items")
public class Item {
    private Integer id;
    private String uuid;
    private Integer quantity;
    private Boolean medical;
    private String type;
    private Customer customer;

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    public Integer getId () {
        return id;
    }

    public void setUUID (String uuid) {
        this.uuid = uuid;
    }

    public String getUUID () {
        return uuid;
    }

    public void setQuantity (Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity () {
        return quantity;
    }

    public void setMedical (Boolean medical) {
        this.medical = medical;
    }

    public Boolean getMedical () {
        return medical;
    }

    public void setType (String type) {
        this.type = type;
    }

    public String getType () {
        return type;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @ManyToOne
    @JoinColumn(name="customer_id")
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public boolean equals (Object obj) {
        if(obj instanceof  Item) {
            Item other = (Item) obj;
            if(this.uuid.equals(other.getUUID())) {
                return true;
            }
        }

        return false;
    }
}
