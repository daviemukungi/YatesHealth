package com.oneshoppoint.yates.controller.api;

import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.*;
import com.oneshoppoint.yates.service.CheckoutService;
import com.oneshoppoint.yates.util.PublicStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The checkout api contains endpoints for getting retailerBasket values and carrierCharges and placing an order
 * @author robinson odhiambo
 * @version 1.0
 * @since 4/26/16.
 */

@RestController
@RequestMapping("/api/checkout")
@Secured("ROLE_APP")
public class CheckoutController {
    @Autowired
    CheckoutService checkoutService;

    /**
     * This endpoint allows access to retailer basket values based on their location and the cart supplied
     * @param cartAndLocation is an object that contains the list of items in the cart plus the location of the
     *                        desired retailer
     * @return The return is a list of retailers and their basket value
     * @see RetailerBasket
     */
    @RequestMapping(method = RequestMethod.POST)
    public RestMessage<List<RetailerBasket>> retailerBaskets (@Valid @RequestBody CartAndLocation cartAndLocation) {
        PublicStorage storage = new PublicStorage();
        storage.setCart(cartAndLocation.getCart());
        return new RestMessage<List<RetailerBasket>>(Status.OK,checkoutService.getBasketByLocationAndCart(cartAndLocation.getLocationId(),storage));
    }

    /**
     * This endpoint allows access to a retailer's basket value based on the cart supplied
     * @param cart is an object that contains the list of items in the cart
     * @param retailerId This is an integer specifying the desired retailer
     * @return The return is a retailer and their basket value
     * @see RetailerBasket
     */
    @RequestMapping(params = {"retailerId"},method = RequestMethod.POST)
    public RestMessage<RetailerBasket> retailerBasket (@RequestParam Integer retailerId,@Valid @RequestBody Cart cart) {
        PublicStorage storage = new PublicStorage();
        storage.setCart(cart.getCart());
        return new RestMessage<RetailerBasket>(Status.OK,checkoutService.getBasketByRetailerId(retailerId, storage));
    }

    /**
     * This endpoint allows access to carrier charges based on the destination location ,the selected retailer's id and the cart supplied
     * @param retailerId This is an integer specifying the desired retailer
     * @param cart is an object that contains the list of items in the cart plus the location id of the
     *                        destination of the order
     * @return The return is a list of carrier charges
     * @see RetailerBasket
     */
    @RequestMapping(value="/carrier",params = {"retailerId"},method = RequestMethod.POST)
    public RestMessage<List<CarrierCharge>> carrierCharge (@RequestParam Integer retailerId,@Valid @RequestBody CartAndLocation cart) {
        PublicStorage storage = new PublicStorage();
        storage.setCart(cart.getCart());
        return new RestMessage<List<CarrierCharge>>(Status.OK,checkoutService.calculateCarrierCharges(retailerId,cart.getLocationId(),storage));
    }

    /**
     * This endpoint is used to place the order for a product(s)
     * @param checkout Is an object that specifies the data structure that contains details of the transaction
     * @return The return is a message and a status of the operation
     * @see RetailerBasket
     */
    @RequestMapping(value="/order",method = RequestMethod.POST)
    public RestMessage checkout (@Valid @RequestBody Checkout checkout) {
        PublicStorage storage = new PublicStorage();
        storage.setCart(checkout.getCart());
        checkoutService.makeOrder(checkout,storage);
        return new RestMessage(Status.CREATED,"You have successfully placed an order");
    }
}
