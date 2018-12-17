package com.oneshoppoint.yates.wrapper;

import com.oneshoppoint.yates.model.Image;

/**
 * Created by robinson on 4/27/16.
 */
public class InvoiceItem {
    private String productUUID;
    private String productName;
    private Double productPrice;
    private Integer productQuantity;
    private Double productTotalPrice;
    private Image productImage;

    public void setProductUUID (String productUUID) {
        this.productUUID = productUUID;
    }

    public String getProductUUID() {
        return productUUID;
    }

    public void setProductName (String productName) {
        this.productName  = productName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductPrice (Double productPrice) {
        this.productPrice  = productPrice;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductQuantity (Integer productQuantity) {
        this.productQuantity  = productQuantity;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductTotalPrice (Double productTotalPrice) {
        this.productTotalPrice  = productTotalPrice;
    }

    public Double getProductTotalPrice() {
        return productTotalPrice;
    }

    public void setProductImage (Image productImage) {
        this.productImage = productImage;
    }

    public Image getProductImage() {
        return productImage;
    }
}
