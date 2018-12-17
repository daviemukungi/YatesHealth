package com.oneshoppoint.yates.service;


import com.oneshoppoint.yates.util.PublicStorage;
import com.oneshoppoint.yates.wrapper.CarrierCharge;
import com.oneshoppoint.yates.wrapper.Checkout;
import com.oneshoppoint.yates.wrapper.RetailerBasket;

import java.util.List;

/**
 * Created by robinson on 4/26/16.
 */
public interface CheckoutService {
    List<RetailerBasket> getBasketByLocationAndCart(Integer locationId,PublicStorage storage);
    RetailerBasket getBasketByRetailerId(Integer id,PublicStorage storage);
    List<CarrierCharge> calculateCarrierCharges (Integer retailerId,Integer locationId,PublicStorage publicStorage);
    void makeOrder(Checkout checkout,PublicStorage storage);
}
