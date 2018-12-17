package com.oneshoppoint.yates.wrapper;

import com.oneshoppoint.yates.model.Product;

/**
 * Created by robinson on 4/15/16.
 */
public class OrderItemSummary {
    private Product product;
    private Integer quantity;
    private Double  totalPrice;
    private String code;

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct () {
        return product;
    }

    public void setQuantity (Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantity () {
        return quantity;
    }

    public void calculateTotalPrice () {
        totalPrice = product.getPrice() * quantity;
    }

    public Double getTotalPrice () {
        return totalPrice;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode () {
        return code;
    }
}
