package com.oneshoppoint.yates.repository;

import com.oneshoppoint.yates.model.Item;

import java.util.List;

/**
 * Created by robinson on 4/8/16.
 */
public interface ItemDao {
    void save (Item item);
    List<Item> getCart(Integer id);
    List<Item> getWishlist(Integer id);
    List<Item> getComparisonList(Integer id);
    void delete (Item item);
}
