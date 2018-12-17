package com.oneshoppoint.yates.wrapper;

import javax.validation.constraints.NotNull;

/**
 * Created by robinson on 4/23/16.
 */
public class StockForm {
    private Integer id;
    @NotNull
    private Double price;
    private String productUUID;
    private Integer innId;
    @NotNull
    private Integer quantity;
    @NotNull
    private Integer retailerId;
    private Boolean enabled = true;

    public void setId (Integer id) {
        this.id = id;
    }

    public Integer getId () {
        return id;
    }

    public void setPrice (Double price) {
        this.price = price;
    }

    public Double getPrice () {
        return price;
    }

    public void setProductUUID(String productUUID) {
        this.productUUID = productUUID;
    }

    public String getProductUUID () {
        return this.productUUID;
    }

    public void setInnId(Integer innId) {
        this.innId = innId;
    }

    public Integer getInnId () {
        return this.innId;
    }

    public void setQuantity (Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity () {
        return this.quantity;
    }

    public void setRetailerId (Integer retailerId) {
        this.retailerId = retailerId;
    }

    public Integer getRetailerId () {
        return this.retailerId;
    }

    public void setEnabled (Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getEnabled () {
        return this.enabled;
    }
}
