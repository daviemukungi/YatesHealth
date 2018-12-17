package com.oneshoppoint.yates.wrapper;

import com.oneshoppoint.yates.model.Item;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by robinson on 4/26/16.
 */
public class CartAndLocation {
    @NotNull
    private List<Item> cart;
    @NotNull
    private Integer locationId;

    public void setCart (List<Item> cart) {
        this.cart = cart;
    }

    public List<Item> getCart () {
        return cart;
    }

    public void setLocationId (Integer locationId) {
        this.locationId = locationId;
    }

    public Integer getLocationId () {
        return locationId;
    }
}
