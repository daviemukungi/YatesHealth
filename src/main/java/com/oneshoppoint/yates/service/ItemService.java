package com.oneshoppoint.yates.service;

import com.oneshoppoint.yates.model.Item;

import java.util.List;

/**
 * Created by robinson on 5/6/16.
 */
public interface ItemService {
    void saveCart (List<Item> items);
    void saveComparisonList (List<Item> items);
    void saveWishlist (List<Item> items);
    List<Item> getCart ();
    List<Item> getWishlist ();
    List<Item> getComparisonList ();
}
