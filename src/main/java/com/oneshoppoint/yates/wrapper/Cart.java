package com.oneshoppoint.yates.wrapper;

import com.oneshoppoint.yates.model.Item;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by robinson on 4/26/16.
 */
public class Cart {
    @NotNull
    private List<Item> cart;

    public void setCart (List<Item> cart) {
        this.cart = cart;
    }

    public List<Item> getCart () {
        return cart;
    }
}
