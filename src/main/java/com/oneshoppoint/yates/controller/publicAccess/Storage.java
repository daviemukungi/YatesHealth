package com.oneshoppoint.yates.controller.publicAccess;

import com.oneshoppoint.yates.model.Item;
import com.oneshoppoint.yates.model.Prescription;
import com.oneshoppoint.yates.model.Product;
import com.oneshoppoint.yates.service.InnService;
import com.oneshoppoint.yates.service.PrescriptionService;
import com.oneshoppoint.yates.service.ProductService;
import com.oneshoppoint.yates.service.UserService;
import com.oneshoppoint.yates.util.PublicStorage;
import com.oneshoppoint.yates.util.RestMessage;
import com.oneshoppoint.yates.util.Status;
import com.oneshoppoint.yates.wrapper.CartForm;
import com.oneshoppoint.yates.wrapper.OrderItemSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.*;

/**
 * Created by robinson on 4/15/16.
 */
@RestController
public class Storage {
    @Autowired
    PublicStorage publicStorage;
    @Autowired
    ProductService productService;
    @Autowired
    PrescriptionService prescriptionService;
    @Autowired
    UserService userService;
    @Autowired
    InnService innService;

    @RequestMapping(value="/cart",params = {"uuidStr"},method = RequestMethod.POST)
    public RestMessage addToCart (@RequestParam String uuidStr) {
        publicStorage.addToCart(uuidStr);
        return new RestMessage (Status.MODIFIED,"Added "+productService.getByUUID(uuidStr).getName()+" to your shopping cart");
    }

    @RequestMapping(value="/cart",method = RequestMethod.PUT)
    public RestMessage updateCart (@RequestBody CartForm cartForm) {
        HashMap<String,Integer> map = cartForm.getItems();

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            publicStorage.updateCart(entry.getKey(),entry.getValue());
        }
        return new RestMessage (Status.MODIFIED,"Your shopping cart has been updated");
    }

    @RequestMapping(value="/cart",method=RequestMethod.DELETE)
    public RestMessage clearCart () {
        publicStorage.clearCart();
        return new RestMessage (Status.MODIFIED,"Your cart has been cleared");
    }

    @RequestMapping(value="/cart",params = {"uuidStr","type"},method=RequestMethod.DELETE)
    public RestMessage removeFromCart (@RequestParam String uuidStr,@RequestParam String type) {
        publicStorage.removeFromCart(uuidStr);
        if(type.equalsIgnoreCase("medical")) {
            return new RestMessage (Status.MODIFIED,"The medical package has been removed from the cart");
        }
        return new RestMessage (Status.MODIFIED,"The product "+productService.getByUUID(uuidStr).getName()+" has been removed from the cart");
    }

    @RequestMapping(value="/wishlist",params = {"uuidStr"},method = RequestMethod.POST)
    public RestMessage addToWishlist (@RequestParam String uuidStr) {
        publicStorage.addToWishlist(uuidStr);
        return new RestMessage (Status.MODIFIED,"Added "+productService.getByUUID(uuidStr).getName()+" to your shopping wish list");
    }

    @RequestMapping(value="/wishlist",method=RequestMethod.DELETE)
    public RestMessage clearWishlist () {
        publicStorage.clearWishlist();
        return new RestMessage (Status.MODIFIED,"Your wish list has been cleared");
    }

    @RequestMapping(value="/wishlist",params = {"uuidStr"},method=RequestMethod.DELETE)
    public RestMessage removeFromWishlist (@RequestParam String uuidStr) {
        publicStorage.removeFromWishlist(uuidStr);
        return new RestMessage (Status.MODIFIED,"The product "+productService.getByUUID(uuidStr).getName()+" has been removed from your wish list");
    }

    @RequestMapping(value="/comparison",params = {"uuidStr"},method = RequestMethod.POST)
    public RestMessage addToComparison (@RequestParam String uuidStr) {
        if(publicStorage.getComparison().size() <= 4){
            publicStorage.addToComparison(uuidStr);
            return new RestMessage (Status.MODIFIED,"Added "+productService.getByUUID(uuidStr).getName()+" to your shopping comparison");
        } else {
            return new RestMessage (Status.FAILED,"Comparison list is full");
        }
    }

    @RequestMapping(value="/comparison",method=RequestMethod.DELETE)
    public RestMessage clearComparison () {
        publicStorage.clearComparison();
        return new RestMessage (Status.MODIFIED,"Your comparison list has been cleared");
    }

    @RequestMapping(value="/comparison",params = {"uuidStr"},method=RequestMethod.DELETE)
    public RestMessage removeFromComparison (@RequestParam String uuidStr) {
        publicStorage.removeFromComparison(uuidStr);
        return new RestMessage (Status.MODIFIED,"The product "+productService.getByUUID(uuidStr).getName()+" has been removed from your comparison list");
    }


    @RequestMapping(value="/cart",method = RequestMethod.GET)
    public List<OrderItemSummary> getCart () {
        List<Item> items = publicStorage.getCart();
        List<OrderItemSummary> orderItems = new ArrayList<OrderItemSummary>();
        for(Item item : items) {
            if(item.getMedical() != null && item.getMedical()) {
                OrderItemSummary orderItem = new OrderItemSummary();
                orderItem.setCode(item.getUUID());
                orderItems.add(orderItem);
            } else {
                Product product = productService.getByUUID(item.getUUID());
                if(product != null) {
                    OrderItemSummary orderItem = new OrderItemSummary();
                    orderItem.setProduct(product);
                    orderItem.setQuantity(item.getQuantity());
                    orderItem.calculateTotalPrice();
                    orderItems.add(orderItem);
                }
            }

        }

        return orderItems;
    }

    @RequestMapping(value="/prescription",params = {"code"},method = RequestMethod.POST)
    public RestMessage decodePrescription (@RequestParam String code) {
        Prescription prescription = prescriptionService.getByCode(code);
        if(prescription != null) {
            publicStorage.addToCartMedical(code);
            return new RestMessage (Status.MODIFIED,"A prescription from Medic :- "+userService.getByMedicId(prescription.getMedic().getId()).getLastname()+" has been added to your shopping cart");
        }
        return new RestMessage (Status.FAILED,"The prescription code you entered has expired or does not exist");
    }
}
