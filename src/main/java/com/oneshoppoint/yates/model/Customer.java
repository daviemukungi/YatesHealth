package com.oneshoppoint.yates.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Davie on 4/6/16.
 */
@Entity
@Table(name="customers")
public class Customer extends Model {
    private Set<Item> cart;
    private Set<Item> wishlist;
    private Set<Address> addresses;

    public void setCart (Set<Item> cart) {
        this.cart = new HashSet<Item>();
        this.cart.addAll(cart);
    }

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="user_id")
    public Set<Item> getCart () {
        return cart;
    }

    public void setWishlist (Set<Item> wishlist) {
        this.wishlist = new HashSet<Item>();
        this.wishlist.addAll(wishlist);
    }

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="user_id")
    public Set<Item> getWishlist() {
        return wishlist;
    }

    public void setAddresses (Set<Address> addresses) {
        this.addresses = new TreeSet<Address>();
        this.addresses.addAll(addresses);
    }

    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name="customer_id")
    public Set<Address> getAddresses () {
        return addresses;
    }
}
