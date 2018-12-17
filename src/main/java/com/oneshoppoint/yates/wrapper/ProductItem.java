package com.oneshoppoint.yates.wrapper;

import com.oneshoppoint.yates.model.Image;

/**
 * Created by robinson on 4/28/16.
 */
public class ProductItem {
    private String uuid;
    private String name;
    private Double price;
    private Image image;
    private Integer quantity;


    public void setName (String name) {
        this.name = name;
    }

    public String getName () {
        return name;
    }

    public void setUUID (String uuid) {
        this.uuid = uuid;
    }

    public String getUUID() {
        return uuid;
    }

    public void setPrice (Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setImage (Image image) {
        this.image = image;
    }

    public Image getImage () {
        return image;
    }

    public void setQuantity (Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity () {
        return quantity;
    }
}
