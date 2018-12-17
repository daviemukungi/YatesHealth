package com.oneshoppoint.yates.util;

import com.oneshoppoint.yates.model.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by robinson on 4/8/16.
 */
public class PublicStorage {
    private List<Item> cart;
    private List<Item> wishlist;
    private List<Item> comparison;

    public PublicStorage () {
        cart = new ArrayList<Item>();
        wishlist = new ArrayList<Item>();
        comparison = new ArrayList<Item>();
    }

    public void setCart (List<Item> cart) {
        this.cart.addAll(cart);
    }

    public void setWishlist (List<Item> wishlist) {
        this.wishlist.addAll(wishlist);
    }

    public void setComparison (List<Item> comparison) {
        this.comparison.addAll(comparison);
    }

    public void addToCart (String uuid) {
        Item item = new Item();
        item.setUUID(uuid);
        item.setMedical(false);
        if(cart.contains(item)) {
            int idx = cart.indexOf(item);
            Item itemOld = cart.get(idx);
            if(cart.remove(itemOld)) {
                item.setQuantity(itemOld.getQuantity()+1);
                cart.add(idx,item);
            }
        } else {
            item.setQuantity(1);
            cart.add(item);
        }
    }

    public void addToCartMedical (String code) {
        Item item = new Item();
        item.setUUID(code);
        item.setMedical(true);
        if(cart.contains(item)) {
            int idx = cart.indexOf(item);
            Item itemOld = cart.get(idx);
            if(cart.remove(itemOld)) {
                item.setQuantity(itemOld.getQuantity()+1);
                cart.add(idx,item);
            }
        } else {
            item.setQuantity(1);
            cart.add(item);
        }
    }

    public boolean updateCart(String uuid,Integer quantity) {
        Item item = new Item();
        item.setUUID(uuid);
        if(cart.contains(item)) {
            int idx = cart.indexOf(item);
            Item itemOld = cart.get(idx);
            if(cart.remove(itemOld)) {
                item.setQuantity(quantity);
                cart.add(idx,item);
            }
            return true;
        }
        return false;
    }

    public boolean removeFromCart(String uuid) {
        Item item = new Item();
        item.setUUID(uuid);
        return cart.contains(item) &&cart.remove(item);
    }

    public void addToWishlist (String uuid) {
        Item item = new Item();
        item.setUUID(uuid);
        if(!wishlist.contains(item)) {
            wishlist.add(item);
        }
    }

    public boolean removeFromWishlist(String uuid) {
        Item item = new Item();
        item.setUUID(uuid);
        return wishlist.contains(item) &&wishlist.remove(item);
    }

    public void addToComparison (String uuid) {
        Item item = new Item();
        item.setUUID(uuid);
        if(!comparison.contains(item)) {
            comparison.add(item);
        }
    }

    public List<Item> getComparison () {
        return comparison;
    }

    public boolean removeFromComparison(String uuid) {
        Item item = new Item();
        item.setUUID(uuid);
        return comparison.contains(item) &&comparison.remove(item);
    }

    public List<Item> getCart() {
        return cart;
    }

    public void clearCart() {
        cart = new ArrayList<Item>();
    }

    public List<Item> getWishlist() {
        return wishlist;
    }

    public void clearWishlist() {
        wishlist = new ArrayList<Item>();
    }

    public void clearComparison() {
        comparison = new ArrayList<Item>();
    }
}
