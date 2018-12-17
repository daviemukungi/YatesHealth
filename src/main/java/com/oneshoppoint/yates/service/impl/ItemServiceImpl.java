package com.oneshoppoint.yates.service.impl;

import com.oneshoppoint.yates.model.Item;
import com.oneshoppoint.yates.repository.ItemDao;
import com.oneshoppoint.yates.service.ItemService;
import com.oneshoppoint.yates.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by robinson on 5/6/16.
 */

@Service
@Transactional
public class ItemServiceImpl implements  ItemService {
    @Autowired
    private ItemDao itemDao;
    @Autowired
    CurrentUser currentUser;

    public void saveCart (List<Item> items) {
        List<Item> cartItems = itemDao.getCart(currentUser.getUser().getCustomer().getId());
        if(cartItems != null) {
            for (Item item : cartItems) {
                itemDao.delete(item);
            }
        }

        cartItems = items;
        for (Item item : cartItems) {
            if(item.getMedical()) {
               continue;
            }
            item.setType("cart");
            item.setCustomer(currentUser.getUser().getCustomer());
            itemDao.save(item);
        }

    }

    public void saveWishlist (List<Item> items) {
        List<Item> wishlistItems = itemDao.getWishlist(currentUser.getUser().getCustomer().getId());
        if(wishlistItems != null) {
            for (Item item : wishlistItems) {
                itemDao.delete(item);
            }
        }

        wishlistItems = items;
        for (Item item : wishlistItems) {
            item.setType("wishlist");
            item.setCustomer(currentUser.getUser().getCustomer());
            itemDao.save(item);
        }

    }

    public void saveComparisonList (List<Item> items) {
        List<Item> comparisonItems = itemDao.getComparisonList(currentUser.getUser().getCustomer().getId());
        if(comparisonItems != null) {
            for (Item item : comparisonItems) {
                itemDao.delete(item);
            }
        }

        comparisonItems = items;
        for (Item item : comparisonItems) {
            item.setType("comparison");
            item.setCustomer(currentUser.getUser().getCustomer());
            itemDao.save(item);
        }

    }

    public List<Item> getCart () {
        return itemDao.getCart(currentUser.getUser().getCustomer().getId());
    }

    public List<Item> getWishlist () {
        return itemDao.getWishlist(currentUser.getUser().getCustomer().getId());
    }

    public List<Item> getComparisonList () {
        return itemDao.getComparisonList(currentUser.getUser().getCustomer().getId());
    }
}
