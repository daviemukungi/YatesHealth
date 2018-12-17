package com.oneshoppoint.yates.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by robinson on 4/6/16.
 */

@Entity
@Table(name="stocks")
public class Stock extends Model {
    private Double price;
    private Product product;
    private Inn inn;
    private Retailer retailer;
    private Integer quantity;

    public void setPrice (Double price) {
        this.price = price;
    }

    public Double getPrice () {
        return price;
    }

    public void setProduct (Product product) {
        this.product = product;
    }

    @OneToOne
    @JoinColumn(name="product_id")
    public Product getProduct () {
        return product;
    }

    public void setInn (Inn inn) {
        this.inn = inn;
    }

    @OneToOne
    @JoinColumn(name="inn_id")
    public Inn getInn () {
        return inn;
    }

    public void setRetailer (Retailer retailer) {
        this.retailer = retailer;
    }

    @OneToOne
    @JoinColumn(name="retailer_id")
    public Retailer getRetailer () {
        return retailer;
    }

    public void setQuantity (Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity () {
        return quantity;
    }

    @Override
    public boolean equals (Object obj) {
        if(obj instanceof Stock ) {
            Stock other = (Stock) obj;
            if(this.product.equals(other.getProduct()) && this.retailer.equals(other.getRetailer())) {
                return true;
            }
        }

        return false;
    }
}
