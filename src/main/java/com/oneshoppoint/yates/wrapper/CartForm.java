package com.oneshoppoint.yates.wrapper;

import java.util.HashMap;

/**
 * Created by robinson on 4/15/16.
 */
public class CartForm {
    private HashMap<String,Integer> items;

    public void setItems (HashMap<String,Integer> items) {
        this.items = items;
    }

    public HashMap<String,Integer> getItems() {
        return items;
    }
}
