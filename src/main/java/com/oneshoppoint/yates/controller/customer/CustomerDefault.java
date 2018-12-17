package com.oneshoppoint.yates.controller.customer;

import com.oneshoppoint.yates.model.Item;
import com.oneshoppoint.yates.model.Product;
import com.oneshoppoint.yates.service.ItemService;
import com.oneshoppoint.yates.service.OrderService;
import com.oneshoppoint.yates.service.ProductService;
import com.oneshoppoint.yates.service.SettingsService;
import com.oneshoppoint.yates.util.CurrentUser;
import com.oneshoppoint.yates.util.PublicStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by robinson on 5/6/16.
 */
@Controller
@RequestMapping("/customer")
@Secured("ROLE_CUSTOMER")
public class CustomerDefault {
    @Autowired
    OrderService orderService;
    @Autowired
    PublicStorage publicStorage;
    @Autowired
    ProductService productService;
    @Autowired
    CurrentUser currentUser;
    @Autowired
    ItemService itemService;
    @Autowired
    SettingsService settingsService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView index () {
        ModelAndView mv = new ModelAndView();
        List<Item> cart = itemService.getCart();
        List<Item> wishlist = itemService.getWishlist();
        List<Item> comparisonList = itemService.getComparisonList();
        if(cart != null && !cart.isEmpty()) {
            publicStorage.setCart(cart);
        }

        if(wishlist != null && !wishlist.isEmpty()) {
            publicStorage.setWishlist(wishlist);
        }

        if(comparisonList != null && !comparisonList.isEmpty()) {
            publicStorage.setComparison(comparisonList);
        }

        mv.addObject("orders",orderService.getOrderSummaryByCustomer());
        mv.addObject("settings",settingsService.get());
        mv.setViewName("customer/index");
        return mv;
    }

    @RequestMapping(value="/order/{invoiceNo}/view",method = RequestMethod.GET)
    public ModelAndView invoice (@PathVariable String invoiceNo) {
        ModelAndView mv = new ModelAndView();
        mv.addObject("customer",true);
        mv.addObject("invoice",orderService.getInvoice(invoiceNo));
        mv.addObject("settings",settingsService.get());
        mv.setViewName("common/invoice");
        return mv;
    }

    @RequestMapping(value="/account",method = RequestMethod.GET)
    public ModelAndView account () {
        ModelAndView mv = new ModelAndView();
        mv.addObject("customer",true);
        mv.addObject("user",currentUser.getUser());
        mv.addObject("settings",settingsService.get());
        mv.setViewName("common/account");
        return mv;
    }

    @RequestMapping(value="/logout",method = RequestMethod.GET)
    public String logout (HttpServletRequest request) {
        itemService.saveCart(publicStorage.getCart());
        itemService.saveComparisonList(publicStorage.getComparison());
        itemService.saveWishlist(publicStorage.getWishlist());
        System.out.println(request.getRequestURL());
        return "redirect:/j_spring_security_logout";
    }

    @RequestMapping(value="/wishlist",method = RequestMethod.GET)
    public ModelAndView wishlist () {
        ModelAndView mv = new ModelAndView();
        List<Product> products = new ArrayList<Product>();
        List<Item> wishlist = publicStorage.getWishlist();
        for(Item item : wishlist) {
            products.add(productService.getByUUID(item.getUUID()));
        }
        mv.addObject("wishlist",products);
        mv.addObject("settings",settingsService.get());
        mv.setViewName("customer/wishlist");
        return mv;
    }
}
