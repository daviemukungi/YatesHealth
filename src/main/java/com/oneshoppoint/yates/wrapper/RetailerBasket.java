package com.oneshoppoint.yates.wrapper;


import com.oneshoppoint.yates.model.Retailer;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robinson on 4/25/16.
 */
public class RetailerBasket implements Comparable<RetailerBasket> {
    private Retailer retailer;
    private Double totalPrice;
    private List<ProductItem> products;

    public void setRetailer (Retailer retailer) {
        this.retailer = retailer;
    }

    public Retailer getRetailer () {
        return retailer;
    }

    public void setTotalPrice (Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getTotalPrice () {
        return totalPrice;
    }

    public void setProducts (List<ProductItem> products) {
        this.products = new ArrayList<ProductItem>();
        this.products.addAll(products);
    }

    public List<ProductItem> getProducts() {
        return products;
    }

    public int compareTo (@Nonnull RetailerBasket other) {
        return this.totalPrice.compareTo(other.getTotalPrice());
    }
}
